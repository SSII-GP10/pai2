package persistence;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import domain.Client;
import domain.Message;

/**
 * Clase que sirve como punto de entrada a la base de datos
 *
 * @author Matias Crizul
 */
public class DBConnection {

    public static String PATH = null;

    public DBConnection() {
    }

    /**
     * Create Database if doesn't exists;
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void main(String[] args) {
		try {
			createDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static void createDB() throws SQLException {
        if (PATH == null) {
            PATH = "jdbc:sqlite:./db.db";
            try {
                Class.forName("org.sqlite.JDBC");
                KPIRepository.createKPITable();
                ClientRepository.createClientTable();
                MessageRepository.createMessageTable();
                
                
               littleTest();
                
            } catch (ClassNotFoundException | SQLException e) {
                throw new SQLException();
            }
        }
    }
    public static void littleTest() throws SQLException{
    	Client c = new Client();
        c.setId(1);
        c.setKey("asdfasdfasdf");
        c.setNumberAccount("156234987");
        ClientRepository.insertClient(c);
       Date now = new Date(System.currentTimeMillis());
        
        Message m = new Message(2, c, c, 30., "5asdfsa5df6as", true, now);
        Message m2 = new Message(3, c, c, 70., "I have no integrity", false, now);
        MessageRepository.insertMessage(m);
        MessageRepository.insertMessage(m2);
        Collection<Message> me = MessageRepository.getSentMessages(now);
        System.out.println(me);
       System.out.println("________________________________________");
         me = MessageRepository.getMessagesWithNoIntegrity(now);
        System.out.println(me);
    }
    public static void checkPath() throws SQLException {
        if (PATH == null) {
            throw new SQLException("Error: The DB not exist.");
        }
    }

}
