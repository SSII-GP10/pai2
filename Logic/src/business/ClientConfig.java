package business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class ClientConfig {

    private static ClientConfig instance;

    private String ipServer;
    private int portServer;
    private String numberAccount;
    private String key;
    private String algorithm;

    private ClientConfig() {
    }

    public static ClientConfig getInstance() {
        if (instance == null) {
            instance = new ClientConfig();
        }
        return instance;
    }

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    public int getPortServer() {
        return portServer;
    }

    public void setPortServer(int portServer) {
        this.portServer = portServer;
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void readConfig() throws FileNotFoundException, IOException {
        try {
            InputStream input = new FileInputStream("config.properties");
            Properties prop = new Properties();
            prop.load(input);
            String ipServer = prop.getProperty("ip").trim();
            String portServer = prop.getProperty("port").trim();
            String numberAccount = prop.getProperty("number").trim();
            String key = prop.getProperty("key").trim();
            String algorithm = prop.getProperty("algorithm").trim();
            if (ipServer == null || portServer == null || numberAccount == null
                    || key == null || algorithm == null) {
                throw new IOException("Error in configuration file.");
            } else {
                this.ipServer = ipServer;
                this.portServer = Integer.parseInt(portServer);
                this.numberAccount = numberAccount;
                this.key = key;
                this.algorithm = algorithm;
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Configuration file not exists.");
        }
    }
}
