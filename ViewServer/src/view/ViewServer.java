package view;

import business.KPIRecolect;
import business.KPIReport;
import business.LogReport;
import business.ServerConfig;
import java.io.IOException;
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
        ScheduledExecutorService timer = Executors.newScheduledThreadPool(3);
        timer.scheduleAtFixedRate(new LogReport(), 0, 1, TimeUnit.MINUTES);
        timer.scheduleAtFixedRate(new KPIRecolect(), 0, 1, TimeUnit.MINUTES);
        timer.scheduleAtFixedRate(new KPIReport(), 0, 5, TimeUnit.MINUTES);
    }

    public void readConfig() {
        try {
            serverConfig.readConfig();
        } catch (IOException | SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Exiting...");
            System.exit(0);
        }
    }
}
