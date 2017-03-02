package persistence;

import java.sql.SQLException;

public class DBConnection {

    public static String PATH = null;

    public DBConnection() {
    }

    public static void createDB() throws SQLException {
        if (PATH == null) {
            PATH = "jdbc:sqlite:./db.db";
            try {
                Class.forName("org.sqlite.JDBC");
                ClientRepository.createClientTable();
                MessageRepository.createMessageTable();
                KPIRepository.createKPITable();
            } catch (ClassNotFoundException | SQLException e) {
                throw new SQLException("Error: Cant connect to DB.");
            }
        }
    }

    public static void checkPath() throws SQLException {
        if (PATH == null) {
            throw new SQLException("Error: The DB not exist.");
        }
    }

}
