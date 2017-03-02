package business;

import domain.KPI;
import java.sql.SQLException;
import java.util.Collection;
import persistence.KPIRepository;

public class KPIManager {
    public static KPIManager instance;

    public static KPIManager getInstance() {
        if (instance == null) {
            instance = new KPIManager();
        }
        return instance;
    }
    
    private KPIManager(){
        
    }

    public void addKPI(KPI kpi) throws SQLException {
        KPIRepository.insertKPI(kpi);
    }

    public Collection<KPI> getAllKPI() throws SQLException {
        return KPIRepository.getAllKPI();
    }
    
    public Collection<KPI> getKPIOfCurrentMonth() throws SQLException{
        return KPIRepository.getKPIOfCurrentMonth();
    }
    
}
