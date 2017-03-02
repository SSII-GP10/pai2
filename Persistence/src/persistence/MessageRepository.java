package persistence;

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

import domain.Client;
import domain.Message;

public class MessageRepository {

    public static void createMessageTable() throws SQLException {
        try {
            DBConnection.checkPath();
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DBConnection.PATH);
            Statement stm = con.createStatement();
            String drop = "DROP TABLE IF EXISTS MESSAGE;";
            stm.executeUpdate(drop);
            String query = "CREATE TABLE IF NOT EXISTS MESSAGE"
                    + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + "ClientOrigin INTEGER NOT NULL,"
                    + "ClientDestination INTEGER NOT NULL,"
                    + "Money DOUBLE NOT NULL,"
                    + "Mac TEXT NOT NULL,"
                    + "Integrity BOOLEAN NOT NULL,"
                    + "Date TEXT NOT NULL,"
                    + " FOREIGN KEY(ClientOrigin) REFERENCES CLIENT(ID),"
                    + " FOREIGN KEY(ClientDestination) REFERENCES CLIENT(ID)"
                    + ")";
            stm.executeUpdate(query);
            stm.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static void insertMessage(Message message) throws SQLException {
        insertMessage(message.getClientOrigin(), message.getClientDestination(), message.getMoney(), message.getMac(), message.isIntegrity(), message.getDate());
    }

    public static void insertMessage(Client clientOrigin, Client clientDestination, Double money, String mac, Boolean integrity, Date date) throws SQLException {
        try {
            DBConnection.checkPath();
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DBConnection.PATH);
            con.setAutoCommit(false);
            Statement stm = con.createStatement();
            String formatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(date);
            String sql = "INSERT INTO MESSAGE (ClientOrigin,ClientDestination,Money,Mac,Integrity,Date)"
                    + "VALUES ('"
                    + clientOrigin.getId()
                    + "','"
                    + clientDestination.getId()
                    + "','"
                    + money
                    + "','"
                    + mac
                    + "','"
                    + integrity
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

    public static Collection<Message> getMessage(Integer id) throws SQLException {
        String sql = "SELECT * FROM MESSAGE WHERE ID = '" + id + "';";
        return runQuery(sql);
    }

    public static Collection<Message> getSentMessages(Date date) throws SQLException {
        String formatted = new SimpleDateFormat("yyyy-MM-dd")
                .format(date);
        String sql = "SELECT * FROM MESSAGE WHERE Date BETWEEN '" + formatted + " 00:00:00.000' AND '" + formatted + " 23:59:59.997';";
        return runQuery(sql);
    }

    public static Collection<Message> getMessagesWithNoIntegrity(Date date) throws SQLException {
        String formatted = new SimpleDateFormat("yyyy-MM-dd")
                .format(date);
        String sql = "SELECT * FROM MESSAGE WHERE (Date BETWEEN '" + formatted + " 00:00:00.000' AND '" + formatted + " 23:59:59.997') AND Integrity =='false';";
        return runQuery(sql);
    }

    public static Collection<Message> getMessagesWithIntegrity(Date date) throws SQLException {
        String formatted = new SimpleDateFormat("yyyy-MM-dd")
                .format(date);
        String sql = "SELECT * FROM MESSAGE WHERE (Date BETWEEN '" + formatted + " 00:00:00.000' AND '" + formatted + " 23:59:59.997') AND Integrity =='true';";
        return runQuery(sql);
    }

    public static Collection<Message> getAllMessages() throws SQLException {
        String sql = "SELECT * FROM MESSAGE;";
        return runQuery(sql);
    }
    
    public static Collection<Message> getAllMessages(Date date) throws SQLException {
        String formatted = new SimpleDateFormat("yyyy-MM-dd")
                .format(date);
        String sql = "SELECT * FROM MESSAGE WHERE Date BETWEEN '" + formatted + " 00:00:00.000' AND '" + formatted + " 23:59:59.997';";
        return runQuery(sql);
    }

    public static Collection<Message> runQuery(String sql) throws SQLException {
        try {
            DBConnection.checkPath();
            Collection<Message> result = new ArrayList<Message>();
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection(DBConnection.PATH);
            con.setAutoCommit(false);
            Statement stm = con.createStatement();
            ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                Integer id = res.getInt("ID");
                Client clientOrigin = ClientRepository.getClient(res.getInt("ClientOrigin"));
                Client clientDestination = ClientRepository.getClient(res.getInt("ClientDestination"));
                Double money = res.getDouble("Money");
                String mac = res.getString("Mac");
                Boolean integrity = new Boolean(res.getString("Integrity"));
                SimpleDateFormat formatted = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                Date date = formatted.parse(res.getString("Date"));
                Message newMessage = new Message(id, clientOrigin, clientDestination, money, mac, integrity, date);
                result.add(newMessage);
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
