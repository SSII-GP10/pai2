package persistence;

import java.sql.SQLException;

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
    public static void createDB() throws SQLException {
        if (PATH == null) {
            PATH = "jdbc:sqlite:db.db";
            try {
                Class.forName("org.sqlite.JDBC");
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
