package communications;

import business.ClientConfig;
import helpers.ReaderPlus;
import helpers.Utilities;
import helpers.WriterPlus;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ClientCommunication {

    private ClientConfig clientConfig;
    private ClientConnection connection;
    
    public ClientCommunication(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
        connection = new ClientConnection(clientConfig.getIpServer(), clientConfig.getPortServer());
    }

    public String sendMessage(String message) throws IOException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        connection.openConnection();
        OutputStream outStream = connection.getOutputStream();
        OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream);
        WriterPlus writer = new WriterPlus(outStreamWriter, 3000);
        writer.writeLine(message);
        writer.writeLine(calculateMac(message));
        writer.flush();
        InputStream inStream = connection.getInputStream();
        InputStreamReader inStreamReader = new InputStreamReader(inStream);
        ReaderPlus reader = new ReaderPlus(inStreamReader, 3000);
        String messageServer = reader.nextLine();
        String macServer = reader.nextLine();
        if(!checkIntegrity(messageServer, macServer)){
            throw new IOException("Error in communication (Mac incorrect)");
        }
        writer.close();
        reader.close();
        connection.closeConnection();
        return messageServer;
    }
    
    private String calculateMac(String message) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException{
        return Utilities.calculateMac(message, clientConfig.getKey(), clientConfig.getAlgorithm());
    }
    
    private boolean checkIntegrity(String message, String expectMac) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException{
        String mac = calculateMac(message);
        return mac.equals(expectMac);
    }
}
