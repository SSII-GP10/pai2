package persistence;

import domain.KPI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class KPIRepository {

    public static void createKPITable() throws SQLException {
        try {
            DBConnection.checkPath();
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DBConnection.PATH);
            Statement stm = con.createStatement();
            String drop = "DROP TABLE IF EXISTS KPI;";
            stm.executeUpdate(drop);
            String query = "CREATE TABLE IF NOT EXISTS KPI"
                    + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "Positives NUMERIC NOT NULL,"
                    + "Total NUMERIC NOT NULL,"
                    + "ReportDate DATE NOT NULL" + ")";
            stm.executeUpdate(query);
            stm.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static void insertKPI(KPI kpi) throws SQLException {
        insertKPI(kpi.getPositives(), kpi.getTotal(), kpi.getReportDate());
    }

    public static void insertKPI(Integer positives,
            Integer total, Date date) throws SQLException {
        try {
            DBConnection.checkPath();
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DBConnection.PATH);
            con.setAutoCommit(false);
            Statement stm = con.createStatement();
            String formatted = new SimpleDateFormat("yyyy-MM-dd")
                    .format(date);
            String sql = "INSERT INTO KPI (Positives,Total,ReportDate)"
                    + "VALUES ('"
                    + positives
                    + "','"
                    + total
                    + "','"
                    + formatted + "');";
            stm.executeUpdate(sql);
            stm.close();
            con.commit();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static Collection<KPI> getKPI(Integer id) throws SQLException {
        String sql = "SELECT * FROM KPI WHERE ID = '" + id + "';";
        return runQuery(sql);
    }

    public static Collection<KPI> getAllKPI() throws SQLException {
        String sql = "SELECT * FROM KPI;";
        return runQuery(sql);
    }
    
    public static Collection<KPI> getKPIOfCurrentMonth(Date date) throws SQLException {
        String month = new SimpleDateFormat("MM").format(date);
        String year = new SimpleDateFormat("yyyy").format(date);
        String sql = "SELECT * FROM KPI WHERE strftime('%m', ReportDate) = '" 
                + month + "' AND strftime('%Y', ReportDate) = '" + year + "';";
        return runQuery(sql);
    }

    public static Collection<KPI> runQuery(String sql) throws SQLException {
        try {
            DBConnection.checkPath();
            Collection<KPI> result = new ArrayList<KPI>();
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DBConnection.PATH);
            con.setAutoCommit(false);
            Statement stm = con.createStatement();
            ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                Integer id = res.getInt("ID");
                Integer positives = res.getInt("Positives");
                Integer total = res.getInt("Total");
                SimpleDateFormat formatted = new SimpleDateFormat(
                        "yyyy-MM-dd");
                Date date = formatted.parse(res.getString("ReportDate"));
                KPI newkpi = new KPI(id, positives, total, date);
                result.add(newkpi);
            }
            res.close();
            stm.close();
            con.close();
            return result;
        } catch (SQLException | ClassNotFoundException | ParseException e) {
            throw new SQLException(e.getMessage());
        }
    }

}
