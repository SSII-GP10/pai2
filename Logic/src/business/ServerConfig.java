package business;

import domain.Client;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import persistence.DBConnection;

public class ServerConfig {

    private static ServerConfig instance;
    private ClientManager clientManager;

    private String algorithm;
    private int port;
    private File kpiFile;
    private File logFile;

    private ServerConfig() {
        this.clientManager = ClientManager.getInstance();
    }

    public static ServerConfig getInstance() {
        if (instance == null) {
            instance = new ServerConfig();
        }
        return instance;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
   
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public File getKpiFile() {
        return kpiFile;
    }

    public void setKpiFile(File kpiFile) {
        this.kpiFile = kpiFile;
    }

    public File getLogFile() {
        return logFile;
    }

    public void setLogFile(File logFile) {
        this.logFile = logFile;
    }

    public void readConfig() throws FileNotFoundException, IOException, SQLException {
        try {
            DBConnection.createDB();
            InputStream input = new FileInputStream("config.properties");
            Properties prop = new Properties();
            prop.load(input);
            String algorithm = prop.getProperty("algorithm").trim();
            String port = prop.getProperty("port").trim();
            String kpiPath = prop.getProperty("kpiPath").trim();
            String logPath = prop.getProperty("logPath").trim();
            String clients = prop.getProperty("keys").trim();
            if (algorithm == null || port == null || kpiPath == null || logPath == null
                    || clients == null) {
                throw new IOException("Error in configuration file.");
            } else {
                this.algorithm = algorithm;
                this.port = Integer.parseInt(port);
                this.kpiFile = new File(kpiPath);
                this.logFile = new File(logPath);
                String[] clientsAr = clients.split(";");
                readClients(clientsAr);
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Configuration file not exists.");
        }
    }

    private void readClients(String[] clients) throws SQLException {
        for (String clientPos : clients) {
            String[] clientAr = clientPos.split(":");
            Client client = new Client(0, clientAr[0], clientAr[1]);
            clientManager.addClient(client);
        }
    }

}
