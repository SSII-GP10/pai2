package view;

import business.ClientConfig;
import business.ExchangeClient;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ViewTest {

    private ClientConfig clientConfig;
    private ExchangeClient exchange;

    public ViewTest() {
        clientConfig = ClientConfig.getInstance();
        readConfig();
        exchange = new ExchangeClient(clientConfig);
    }

    public void start() {
        try {
            String accountOrigin = clientConfig.getNumberAccount();
            String accountDestinate = "C2";
            float amount = 20;
            String message = exchange.transfer(accountOrigin, accountDestinate, amount);
            System.out.println(message);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void readConfig() {
        try {
            clientConfig.readConfig();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
}
