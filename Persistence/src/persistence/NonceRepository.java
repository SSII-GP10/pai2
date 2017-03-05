package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import domain.Client;
import domain.Nonce;

public class NonceRepository {

	public static void main(String[] args) {
		
		try {
			DBConnection.createDB();
			Client newC = new Client(1,"sdfsdf","asdf5asdf");
			ClientRepository.insertClient(newC);
			Nonce newn = new Nonce(1,newC,5,6);
			insertNonce(newn);
			
			
			Collection<Nonce> res =runQuery("SELECT * FROM NONCE;");
			System.out.println(res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
            Map<Nonce,Integer> temporaryMap = new HashMap<Nonce,Integer>();
            //Creo un mapa temporal donde guardaré los Nonce SIN client y el id de client
            while (res.next()) {
                Integer id = res.getInt("ID");
                
                Client client = null;
                Integer nonceClient = res.getInt("ClientNonce");
                Integer nonceServer = res.getInt("ServerNonce");
                Nonce newNonce = new Nonce(id, client, nonceClient, nonceServer);
                temporaryMap.put(newNonce, res.getInt("Client"));
            }
            //ahora que la primera query ha terminado podemos recorrer el mapa e ir pidiendo los clientes
            // para terminar de armas los Nonce
            for(Entry<Nonce,Integer> tem:temporaryMap.entrySet()){
            	
            	String getClient = "SELECT * FROM CLIENT WHERE ID = '" + tem.getValue() + "';";
            	ResultSet clients = stm.executeQuery(getClient);
            	Client client = new Client(clients.getInt("ID"),clients.getString("NumberAccount"),clients.getString("Key"));
            	
            	Nonce n = tem.getKey();
            	n.setClient(client);
            	result.add(n);
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
