package view;

import business.ServerConfig;
import connections.ServerConnection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewServer {
    
    private ServerConfig serverConfig;
    
    public ViewServer(){
        serverConfig = ServerConfig.getInstance();
        readConfig();
    }

    public void start() {
        System.out.println("Starting server...");
        ServerConnection connection = new ServerConnection(serverConfig.getPort());
        try {
            connection.openServer();
            while(true){
                connection.openConnections();
                // lanzar hilo
            }
        } catch (IOException ex) {
            Logger.getLogger(ViewServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void readConfig(){
        try {
            serverConfig.readConfig();
        } catch (IOException | SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Exiting...");
            System.exit(0);
        }
    }
}
