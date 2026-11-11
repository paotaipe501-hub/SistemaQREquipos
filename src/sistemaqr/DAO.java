package sistemaqr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Acceso a datos del sistema: CRUD de equipos e historial de lecturas QR.
 *
 * @author Diana Tituaña
 */
public class DAO {

    // ------------------------------------------------------------ INGRESAR Y LISTAR
    /** Registra el equipo y devuelve el ID que le dio la base de datos (0 si no se pudo). */
    public int insertEquipoDAO(Equipo equipo) {
        int idNuevo = 0;
        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            String sql = "INSERT INTO equipo (nombre, tipo, marca, modelo, numero_serie, ubicacion, "
                    + "responsable, direccion_ip, direccion_mac, sistema_operativo, configuracion) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement psd = cc.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            psd.setString(1, equipo.getNombre());
            psd.setString(2, equipo.getTipo());
            psd.setString(3, equipo.getMarca());
            psd.setString(4, equipo.getModelo());
            psd.setString(5, equipo.getNumeroSerie());
            psd.setString(6, equipo.getUbicacion());
            psd.setString(7, equipo.getResponsable());
            psd.setString(8, equipo.getDireccionIp());
            psd.setString(9, equipo.getDireccionMac());
            psd.setString(10, equipo.getSistemaOperativo());
            psd.setString(11, equipo.getConfiguracion());
            int respuesta = psd.executeUpdate();
            if (respuesta > 0) {
                ResultSet rs = psd.getGeneratedKeys();
                if (rs.next()) {
                    idNuevo = rs.getInt(1);
                }
                JOptionPane.showMessageDialog(null, "Se registro el equipo con ID " + idNuevo);
            }
            cc.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo registrar el equipo: " + ex.getMessage());
        }
        return idNuevo;
    }

    public List<Equipo> selectEquiposDAO(boolean incluirBajas) {
        List<Equipo> lista = new ArrayList<>();
        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            String sql = "SELECT * FROM equipo";
            if (!incluirBajas) {
                sql = sql + " WHERE estado = 'ACTIVO'";
            }
            sql = sql + " ORDER BY id_equipo";
            PreparedStatement psd = cc.prepareStatement(sql);
            ResultSet rs = psd.executeQuery();
            while (rs.next()) {
                lista.add(leerEquipo(rs));
            }
            cc.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo consultar los equipos: " + ex.getMessage());
        }
        return lista;
    }

    private Equipo leerEquipo(ResultSet rs) throws Exception {
        Equipo e = new Equipo(
                rs.getString("nombre"),
                rs.getString("tipo"),
                rs.getString("marca"),
                rs.getString("modelo"),
                rs.getString("numero_serie"),
                rs.getString("ubicacion"),
                rs.getString("responsable"),
                rs.getString("direccion_ip"),
                rs.getString("direccion_mac"),
                rs.getString("sistema_operativo"),
                rs.getString("configuracion"));
        e.setIdEquipo(rs.getInt("id_equipo"));
        e.setEstado(rs.getString("estado"));
        e.setFechaRegistro(rs.getString("fecha_registro"));
        e.setFechaBaja(rs.getString("fecha_baja"));
        e.setMotivoBaja(rs.getString("motivo_baja"));
        return e;
    }
        // ------------------------------------------------------------ BUSCAR POR ID
    /** Busca el equipo por su ID (el numero que va dentro del QR). Devuelve null si no existe. */
    public Equipo buscarEquipoDAO(int idEquipo) {
        Equipo equipo = null;
        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            String sql = "SELECT * FROM equipo WHERE id_equipo = ?";
            PreparedStatement psd = cc.prepareStatement(sql);
            psd.setInt(1, idEquipo);
            ResultSet rs = psd.executeQuery();
            if (rs.next()) {
                equipo = leerEquipo(rs);
            }
            cc.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo buscar el equipo: " + ex.getMessage());
        }
        return equipo;
    }
}