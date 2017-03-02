package persistence;

import domain.Client;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class ClientRepository {

    public static void createClientTable() throws SQLException {
        try {
            DBConnection.checkPath();
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DBConnection.PATH);
            Statement stm = con.createStatement();
            String drop = "DROP TABLE IF EXISTS CLIENT;";
            stm.executeUpdate(drop);
            String query = "CREATE TABLE IF NOT EXISTS CLIENT"
                    + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "NumberAccount TEXT NOT NULL," + "Key TEXT NOT NULL" + ")";
            stm.executeUpdate(query);
            stm.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static void insertClient(Client client) throws SQLException {
        insertClient(client.getNumberAccount(), client.getKey());
    }

    public static void insertClient(String numberAccount, String key)
            throws SQLException {
        try {
            DBConnection.checkPath();
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DBConnection.PATH);
            con.setAutoCommit(false);
            Statement stm = con.createStatement();
            String sql = "INSERT INTO CLIENT (NumberAccount,Key)"
                    + "VALUES ('" + numberAccount + "','" + key + "');";
            stm.executeUpdate(sql);
            stm.close();
            con.commit();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static Client getClient(String numberAccount)
            throws SQLException {
        String sql = "SELECT * FROM CLIENT WHERE NumberAccount = '" + numberAccount + "';";
        Collection<Client> result = runQuery(sql);
        Client client = result.iterator().next();
        return client;
    }

    public static Collection<Client> getAllClients() throws SQLException {
        String sql = "SELECT * FROM CLIENT;";
        return runQuery(sql);
    }

    public static Collection<Client> runQuery(String sql) throws SQLException {
        try {
            DBConnection.checkPath();
            Collection<Client> result = new ArrayList<Client>();
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DBConnection.PATH);
            con.setAutoCommit(false);
            Statement stm = con.createStatement();
            ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                int id = res.getInt("ID");
                String numberAccount = res.getString("NumberAccount");
                String key = res.getString("Key");
                Client newClient = new Client(id, numberAccount, key);
                result.add(newClient);
            }
            res.close();
            stm.close();
            con.close();
            return result;
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }
}
