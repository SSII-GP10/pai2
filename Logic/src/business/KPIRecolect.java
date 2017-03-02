package business;

import java.sql.SQLException;
import java.util.Date;
import java.util.TimerTask;
import domain.KPI;

/**
 * Clase que se encarga de recolectar la informacion de los KPI
 *
 * @author Matias Crizul
 */
public class KPIRecolect extends TimerTask {

    private static KPIManager kpiManager;
    private static MessageManager messageManager;

    public KPIRecolect() {
        this.kpiManager = KPIManager.getInstance();
        this.messageManager = MessageManager.getInstance();
    }

    @Override
    public void run() {
        try {
            Date today = new Date();
            int integrity = messageManager.getMessagesWithIntegrity(today).size();
            int total = messageManager.getAllMessages(today).size();
            KPI kpi = new KPI(0, integrity, total, today);
            kpiManager.addKPI(kpi);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
