package business;

import domain.KPI;
import domain.Message;
import helpers.WriterPlus;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.TimerTask;

public class LogReport extends TimerTask {
    
    private static ServerConfig serverConfig;
    private static MessageManager messageManager;

    public LogReport() {
        this.serverConfig = ServerConfig.getInstance();
        this.messageManager = MessageManager.getInstance();
    }

    @Override
    public void run() {
        try {
            System.out.println("Generating Log Report ...");
            FileWriter fileWriter = new FileWriter(serverConfig.getLogFile());
            WriterPlus writer = new WriterPlus(fileWriter, 3000);
            Date today = new Date();
            int totalMessageToday = messageManager.getAllMessages(today).size();
            writer.writeLine("Message total: " + totalMessageToday);
            Collection<Message> messageNoIntegrity = messageManager.getMessagesWithNoIntegrity(today);
            Iterator<Message> it = messageNoIntegrity.iterator();
            while (it.hasNext()) {
                Message message = it.next();
                writer.writeLine(message.toString());
            }
            writer.flush();
            writer.close();
        } catch (IOException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
