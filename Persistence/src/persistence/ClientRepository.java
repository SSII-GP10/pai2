package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import domain.Client;

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
                    + "Account TEXT NOT NULL,"
                    + "Key TEXT NOT NULL" + ")";
            stm.executeUpdate(query);
            stm.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static void insertClient(Client client) throws SQLException {
        insertClient(client.getNumberAccount(), client.getKey());
    }

    public static void insertClient(String account,String key) throws SQLException {
        try {
            DBConnection.checkPath();
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DBConnection.PATH);
            con.setAutoCommit(false);
            Statement stm = con.createStatement();
       
            String sql = "INSERT INTO CLIENT (Account,Key)"
                    + "VALUES ('"
                    + account
                    + "','"
                    + key+ "');";
            stm.executeUpdate(sql);
            stm.close();
            con.commit();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static Collection<Client> getClient(Integer id) throws SQLException {
        String sql = "SELECT * FROM CLIENT WHERE ID = '" + id + "';";
        return runQuery(sql);
    }

    public static Collection<Client> getAllClient() throws SQLException {
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
                Integer id = res.getInt("ID");
                String account = res.getString("Account");
                String key = res.getString("Key");
               
                Client newclient = new Client();
                newclient.setId(id);
                newclient.setNumberAccount(account);
                newclient.setKey(key);
                result.add(newclient);
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
