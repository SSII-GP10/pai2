package business;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistence.DBConnection;

public class Test {
    public static void test(){
        try {
            System.out.println("Hola");
            DBConnection.createDB();
        } catch (SQLException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
