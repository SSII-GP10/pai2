package view;

import business.KPIRecolect;
import business.KPIReport;
import business.LogReport;
import business.ServerConfig;
import business.ExchangeServer;
import communications.ServerConnection;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ViewServer {

    private ServerConfig serverConfig;

    public ViewServer() {
        serverConfig = ServerConfig.getInstance();
        readConfig();
    }

    public void start() {
        System.out.println("Starting server...");
        startProcesses();
        ServerConnection server = new ServerConnection(serverConfig.getPort());
        try {
            server.openServer();
            while(true){
                Socket socket = server.openConnection();
                new Thread(new ExchangeServer(serverConfig, socket)).start();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void startProcesses(){
        ScheduledExecutorService timer = Executors.newScheduledThreadPool(3);
        timer.scheduleAtFixedRate(new LogReport(), 3, 3, TimeUnit.MINUTES);
        timer.scheduleAtFixedRate(new KPIRecolect(), 1, 1, TimeUnit.MINUTES);
        timer.scheduleAtFixedRate(new KPIReport(), 3, 3, TimeUnit.MINUTES);
    }
    
    private void readConfig() {
        try {
            serverConfig.readConfig();
        } catch (IOException | SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Exiting...");
            System.exit(0);
        }
    }
}
