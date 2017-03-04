package business;

import communications.ClientCommunication;
import helpers.Utilities;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ExchangeClient {

    private ClientConfig clientConfig;
    private ClientCommunication communication;

    private int clientNonce;
    private int serverNonce;

    public ExchangeClient(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
        this.communication = new ClientCommunication(clientConfig);
    }

    public String transfer(String accountOrigin, String accountDestinate, float amount) throws IOException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        exchangeNonces(accountOrigin);
        return transferAndCheck(accountOrigin, accountDestinate, amount);
    }

    private void exchangeNonces(String accountClient) throws IOException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        clientNonce = Utilities.getOneNonce();
        String clientNonceStr = accountClient + "-" + clientNonce;
        String serverNonceStr = communication.sendMessage(clientNonceStr);
        serverNonce = Integer.parseInt(serverNonceStr);
    }

    private String transferAndCheck(String accountOrigin, String accountDestinate, float amount) throws IOException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String message = accountOrigin + "-" + accountDestinate + "-" + amount + "-" + clientNonce;
        String serverMessage = communication.sendMessage(message);
        String[] partsMessage = serverMessage.split("-");
        int nonceServer = Integer.parseInt(partsMessage[1]);
        if (!checkNonce(nonceServer, serverNonce)) {
            throw new IOException("Error in communication (Nonce incorrect)");
        }
        return partsMessage[0];
    }

    private boolean checkNonce(int nonce, int expectNonce) {
        return nonce == expectNonce;
    }

}
