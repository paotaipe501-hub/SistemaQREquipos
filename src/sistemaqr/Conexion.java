package sistemaqr;

import javax.swing.JOptionPane;
import java.sql.*;

// Conexión a MySQL
public class Conexion {

    public Connection conectar() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/bd_equipos", "root", "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return con;
    }
}