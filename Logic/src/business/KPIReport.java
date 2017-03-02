package business;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.TimerTask;
import domain.KPI;
import helpers.WriterPlus;

/**
 * Clase que se encarga de generar el reporte de los KPI
 *
 * @author Matias Crizul
 */
public class KPIReport extends TimerTask {

    private static ServerConfig serverConfig;
    private static KPIManager kpiManager;

    public KPIReport() {
        this.serverConfig = ServerConfig.getInstance();
        this.kpiManager = KPIManager.getInstance();
    }

    @Override
    public void run() {
        try {
            System.out.println("Generating KPI Report ...");
            FileWriter fileWriter = new FileWriter(serverConfig.getKpiFile());
            WriterPlus writer = new WriterPlus(fileWriter, 3000);
            Collection<KPI> kpis = kpiManager.getKPIOfCurrentMonth();
            Iterator<KPI> it = kpis.iterator();
            while (it.hasNext()) {
                KPI kpi = it.next();
                writer.writeLine(kpi.toString());
            }
            writer.flush();
            writer.close();
        } catch (IOException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
