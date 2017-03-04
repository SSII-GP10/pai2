package business;

import communications.ServerCommunication;
import communications.ServerSocket;
import domain.Client;
import domain.Message;
import domain.Nonce;
import helpers.ReaderPlus;
import helpers.Utilities;
import helpers.WriterPlus;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

public class ExchangeServer implements Runnable {

    private ServerConfig serverConfig;
    private ServerCommunication connection;
    
    private ClientManager clientManager;
    private NonceManager nonceManager;
    private MessageManager messageManager;

    public ExchangeServer(ServerConfig serverConfig, Socket socket) {
        this.serverConfig = serverConfig;
        this.clientManager = ClientManager.getInstance();
        this.nonceManager = NonceManager.getInstance();
        this.messageManager = MessageManager.getInstance();
        try {
            this.connection = new ServerCommunication(socket);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String[] recive = connection.readMessage();
            String message = recive[0];
            String mac = recive[1];
            if(message == null || mac == null){
                System.out.println("Message incorrect format");
            }else{
                String[] partsMessage = message.split("-");
                int lengthParts = partsMessage.length;
                if(lengthParts == 2){
                    checkIntegrity(partsMessage[0], message, mac);
                    exchangeNonces(partsMessage);
                }else if(lengthParts == 4){
                    checkIntegrity(partsMessage[0], message, mac);
                    checkNonce(partsMessage[0], message, mac, Integer.parseInt(partsMessage[3]));
                    transfer(partsMessage, message, mac);
                }else{
                    System.out.println("Message incorrect format");
                }
            }
            connection.closeConnection();
        } catch (IOException | SQLException | NoSuchAlgorithmException | InvalidKeyException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void exchangeNonces(String[] partsMessage) throws IOException, SQLException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String clientAccount = partsMessage[0];
        int nonceClient = Integer.parseInt(partsMessage[1]);
        Client client = clientManager.getClient(clientAccount);
        int nonceServer = Utilities.getOneNonce();
        Nonce nonce = new Nonce(0, client, nonceClient, nonceServer);
        nonceManager.addNonce(nonce);
        String message = nonceServer + "";
        String mac = Utilities.calculateMac(message, client.getKey(), serverConfig.getAlgorithm());
        connection.sendMessage(message, mac);
    }

    private void transfer(String[] partsMessage, String message, String mac) throws IOException, SQLException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String clientAccount = partsMessage[0];
        Client client = clientManager.getClient(clientAccount);
        Nonce nonceDb = nonceManager.getNonce(client);
        int nonceServer = nonceDb.getServerNonce();
        nonceManager.deleteNonce(nonceDb);
        Message messageReport = new Message(0, client, message, mac, true, new Date());
        messageManager.addMessage(messageReport);
        String messageServer = "Transfer OK-" + nonceServer;
        String macServer = Utilities.calculateMac(messageServer, client.getKey(), serverConfig.getAlgorithm());
        connection.sendMessage(messageServer, macServer);
    }

    private void checkIntegrity(String clientAccount, String message, String expectMac) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Client client = clientManager.getClient(clientAccount);
        String mac = Utilities.calculateMac(message, client.getKey(), serverConfig.getAlgorithm());
        if(!mac.equals(expectMac)){
            Message messageReport = new Message(0, client, message, expectMac, false, new Date());
            messageManager.addMessage(messageReport);
            throw new IOException("Error in communication (Mac incorrect)");
        }
    }

    private void checkNonce(String clientAccount, String message, String mac, int nonce) throws SQLException, IOException {
        Client client = clientManager.getClient(clientAccount);
        Nonce nonceDb = nonceManager.getNonce(client);
        if(nonce != nonceDb.getClientNonce()){
            Message messageReport = new Message(0, client, message, mac, false, new Date());
            messageManager.addMessage(messageReport);
            throw new IOException("Error in communication (Nonce incorrect)");
        }
    }
    
}
