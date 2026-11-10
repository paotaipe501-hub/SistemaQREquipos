package sistemaqr;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 * Conexion a la base de datos bd_equipos (MySQL de XAMPP).
 *
 * @author Diana Tituaña
 */
public class Conexion {

    public Connection conectar() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bd_equipos"
                    + "?useSSL=false"
                    + "&serverTimezone=America/Guayaquil"
                    + "&allowPublicKeyRetrieval=true",
                    "root",
                    "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error de conexion: " + ex.getMessage());
        }
        return con;
    }
}
