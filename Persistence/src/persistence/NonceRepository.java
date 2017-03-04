package persistence;

import domain.Client;
import domain.Nonce;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class NonceRepository {

    public static void createNonceTable() throws SQLException {
        try {
            DBConnection.checkPath();
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DBConnection.PATH);
            Statement stm = con.createStatement();
            String drop = "DROP TABLE IF EXISTS NONCE;";
            stm.executeUpdate(drop);
            String query = "CREATE TABLE IF NOT EXISTS NONCE"
                    + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "Client INTEGER NOT NULL,"
                    + "ClientNonce INTEGER NOT NULL,"
                    + "ServerNonce INTEGER NOT NULL,"
                    + " FOREIGN KEY(Client) REFERENCES CLIENT(ID)"
                    + ")";
            stm.executeUpdate(query);
            stm.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static void insertNonce(Nonce nonce) throws SQLException {
        insertNonce(nonce.getClient(), nonce.getClientNonce(), nonce.getServerNonce());
    }

    public static void insertNonce(Client client, Integer clientNonce, Integer serverNonce) throws SQLException {
        try {
            DBConnection.checkPath();
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DBConnection.PATH);
            con.setAutoCommit(false);
            Statement stm = con.createStatement();
            String sql = "INSERT INTO NONCE (Client,ClientNonce,ServerNonce)"
                    + "VALUES ('"
                    + client.getId()
                    + "','"
                    + clientNonce
                    + "','"
                    + serverNonce + "');";
            stm.executeUpdate(sql);
            stm.close();
            con.commit();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static void deleteNonce(Nonce nonce) throws SQLException {
        try {
            DBConnection.checkPath();
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DBConnection.PATH);
            con.setAutoCommit(false);
            Statement stm = con.createStatement();
            String sql = "DELETE FROM NONCE WHERE Id = " + nonce.getId() + ";";
            stm.executeUpdate(sql);
            stm.close();
            con.commit();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static Nonce getNonce(Client client) throws SQLException {
        int idClient = client.getId();
        String sql = "SELECT * FROM NONCE WHERE Client = '" + idClient + "';";
        return runQuery(sql).iterator().next();
    }

    public static Collection<Nonce> runQuery(String sql) throws SQLException {
        try {
            DBConnection.checkPath();
            Collection<Nonce> result = new ArrayList<Nonce>();
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DBConnection.PATH);
            con.setAutoCommit(false);
            Statement stm = con.createStatement();
            ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                Integer id = res.getInt("ID");
                Client client = null;//ClientRepository.getClient(res.getInt("Client"));
                Integer nonceClient = res.getInt("ClientNonce");
                Integer nonceServer = res.getInt("ServerNonce");
                Nonce newNonce = new Nonce(id, client, nonceClient, nonceServer);
                result.add(newNonce);
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
