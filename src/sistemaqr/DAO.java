package sistemaqr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.sql.SQLException;
import javax.swing.JOptionPane;

// DAO
public class DAO {

    // CRUD: Crear
    public int insertEquipoDAO(Equipo equipo) {
        int idNuevo = 0;
        try {
            // Instancia de la conexión
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

    // CRUD: Leer
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

    // Instancia de Equipo
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
    // CRUD: Buscar por ID
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

    // CRUD: Actualizar
    public boolean updateEquipoDAO(Equipo equipo) {
        int respuesta;
        try (Connection cc = new Conexion().conectar()) {
            if (cc == null) {
                return false;
            }
            cc.setAutoCommit(false);
            try {
                Equipo anterior;
                String consulta = "SELECT * FROM equipo WHERE id_equipo = ? FOR UPDATE";
                try (PreparedStatement ps = cc.prepareStatement(consulta)) {
                    ps.setInt(1, equipo.getIdEquipo());
                    try (ResultSet rs = ps.executeQuery()) {
                        anterior = rs.next() ? leerEquipo(rs) : null;
                    }
                }
                if (anterior == null) {
                    cc.rollback();
                    JOptionPane.showMessageDialog(null, "No se encontró el equipo");
                    return false;
                }
                String sql = "UPDATE equipo SET nombre=?, tipo=?, marca=?, modelo=?, numero_serie=?, "
                        + "ubicacion=?, responsable=?, direccion_ip=?, direccion_mac=?, "
                        + "sistema_operativo=?, configuracion=? WHERE id_equipo=?";
                try (PreparedStatement psd = cc.prepareStatement(sql)) {
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
                    psd.setInt(12, equipo.getIdEquipo());
                    respuesta = psd.executeUpdate();
                }
                String campos = camposCambiados(anterior, equipo);
                if (respuesta > 0 && !campos.isEmpty()) {
                    insertHistorialDAO(cc, anterior, campos);
                }
                cc.commit();
            } catch (Exception ex) {
                try {
                    cc.rollback();
                } catch (SQLException errorRollback) {
                    ex.addSuppressed(errorRollback);
                }
                throw ex;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar el equipo: " + ex.getMessage());
            return false;
        }
        if (respuesta > 0) {
            JOptionPane.showMessageDialog(null, "Se actualizó el equipo con ID " + equipo.getIdEquipo());
        }
        return respuesta > 0;
    }

    private String camposCambiados(Equipo antes, Equipo ahora) {
        List<String> campos = new ArrayList<>();
        agregarSiCambio(campos, "Nombre", antes.getNombre(), ahora.getNombre());
        agregarSiCambio(campos, "Tipo", antes.getTipo(), ahora.getTipo());
        agregarSiCambio(campos, "Marca", antes.getMarca(), ahora.getMarca());
        agregarSiCambio(campos, "Modelo", antes.getModelo(), ahora.getModelo());
        agregarSiCambio(campos, "N.º de serie", antes.getNumeroSerie(), ahora.getNumeroSerie());
        agregarSiCambio(campos, "Ubicación", antes.getUbicacion(), ahora.getUbicacion());
        agregarSiCambio(campos, "Responsable", antes.getResponsable(), ahora.getResponsable());
        agregarSiCambio(campos, "IP", antes.getDireccionIp(), ahora.getDireccionIp());
        agregarSiCambio(campos, "MAC", antes.getDireccionMac(), ahora.getDireccionMac());
        agregarSiCambio(campos, "Sistema operativo", antes.getSistemaOperativo(), ahora.getSistemaOperativo());
        agregarSiCambio(campos, "Configuración", antes.getConfiguracion(), ahora.getConfiguracion());
        return String.join(", ", campos);
    }

    private void agregarSiCambio(List<String> campos, String nombre, String antes, String ahora) {
        if (!Objects.equals(Objects.toString(antes, ""), Objects.toString(ahora, ""))) {
            campos.add(nombre);
        }
    }

    private void insertHistorialDAO(Connection cc, Equipo anterior, String campos) throws SQLException {
        String sql = "INSERT INTO historial_configuracion (id_equipo, campos_cambiados, nombre, tipo, marca, modelo, "
                + "numero_serie, ubicacion, responsable, direccion_ip, direccion_mac, sistema_operativo, configuracion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cc.prepareStatement(sql)) {
            ps.setInt(1, anterior.getIdEquipo());
            ps.setString(2, campos);
            ps.setString(3, anterior.getNombre());
            ps.setString(4, anterior.getTipo());
            ps.setString(5, anterior.getMarca());
            ps.setString(6, anterior.getModelo());
            ps.setString(7, anterior.getNumeroSerie());
            ps.setString(8, anterior.getUbicacion());
            ps.setString(9, anterior.getResponsable());
            ps.setString(10, anterior.getDireccionIp());
            ps.setString(11, anterior.getDireccionMac());
            ps.setString(12, anterior.getSistemaOperativo());
            ps.setString(13, anterior.getConfiguracion());
            ps.executeUpdate();
        }
    }

    public List<CambioConfiguracion> selectHistorialDAO(int idEquipo) {
        List<CambioConfiguracion> lista = new ArrayList<>();
        String sql = "SELECT h.*, e.nombre AS nombre_actual FROM historial_configuracion h "
                + "JOIN equipo e ON e.id_equipo = h.id_equipo"
                + (idEquipo > 0 ? " WHERE h.id_equipo = ?" : "")
                + " ORDER BY h.fecha_cambio DESC, h.id_historial DESC";
        try (Connection cc = new Conexion().conectar()) {
            if (cc == null) {
                return lista;
            }
            try (PreparedStatement ps = cc.prepareStatement(sql)) {
                if (idEquipo > 0) {
                    ps.setInt(1, idEquipo);
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        lista.add(new CambioConfiguracion(
                                rs.getInt("id_historial"), rs.getInt("id_equipo"),
                                rs.getString("nombre_actual"), rs.getString("fecha_cambio"),
                                rs.getString("campos_cambiados"), rs.getString("nombre"),
                                rs.getString("tipo"), rs.getString("marca"), rs.getString("modelo"),
                                rs.getString("numero_serie"), rs.getString("ubicacion"),
                                rs.getString("responsable"), rs.getString("direccion_ip"),
                                rs.getString("direccion_mac"), rs.getString("sistema_operativo"),
                                rs.getString("configuracion")));
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo consultar el historial: " + ex.getMessage());
        }
        return lista;
    }

    // CRUD: Eliminar (dar de baja)
    public boolean bajaEquipoDAO(int idEquipo, String motivo) {
        boolean ok = false;
        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            String sql = "UPDATE equipo SET estado='DADO_DE_BAJA', fecha_baja=NOW(), motivo_baja=? "
                    + "WHERE id_equipo=?";
            PreparedStatement psd = cc.prepareStatement(sql);
            psd.setString(1, motivo);
            psd.setInt(2, idEquipo);
            int respuesta = psd.executeUpdate();
            cc.close();
            if (respuesta > 0) {
                ok = true;
                JOptionPane.showMessageDialog(null, "El equipo con ID " + idEquipo + " fue dado de baja");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo dar de baja: " + ex.getMessage());
        }
        return ok;
    }

    public boolean reactivarEquipoDAO(int idEquipo) {
        boolean ok = false;
        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            String sql = "UPDATE equipo SET estado='ACTIVO', fecha_baja=NULL, motivo_baja=NULL "
                    + "WHERE id_equipo=?";
            PreparedStatement psd = cc.prepareStatement(sql);
            psd.setInt(1, idEquipo);
            int respuesta = psd.executeUpdate();
            cc.close();
            if (respuesta > 0) {
                ok = true;
                JOptionPane.showMessageDialog(null, "El equipo con ID " + idEquipo + " fue reactivado");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo reactivar: " + ex.getMessage());
        }
        return ok;
    }
    public void insertLecturaDAO(int idEquipo) {
        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            String sql = "INSERT INTO lectura_qr (id_equipo) VALUES(?)";
            PreparedStatement psd = cc.prepareStatement(sql);
            psd.setInt(1, idEquipo);
            psd.executeUpdate();
            cc.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo registrar la lectura: " + ex.getMessage());
        }
    }

    public List<Lectura> selectLecturasDAO() {
        List<Lectura> lista = new ArrayList<>();
        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            String sql = "SELECT l.id_lectura, l.fecha_lectura, e.id_equipo, e.nombre, e.ubicacion "
                    + "FROM lectura_qr l INNER JOIN equipo e ON l.id_equipo = e.id_equipo "
                    + "ORDER BY l.fecha_lectura DESC";
            PreparedStatement psd = cc.prepareStatement(sql);
            ResultSet rs = psd.executeQuery();
            while (rs.next()) {
                lista.add(new Lectura(
                        rs.getInt("id_lectura"),
                        rs.getString("fecha_lectura"),
                        rs.getInt("id_equipo"),
                        rs.getString("nombre"),
                        rs.getString("ubicacion")));
            }
            cc.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo consultar las lecturas: " + ex.getMessage());
        }
        return lista;
    }

    public int contarLecturasDAO(int idEquipo) {
        int cantidad = 0;
        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            String sql = "SELECT COUNT(*) AS cantidad FROM lectura_qr WHERE id_equipo = ?";
            PreparedStatement psd = cc.prepareStatement(sql);
            psd.setInt(1, idEquipo);
            ResultSet rs = psd.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt("cantidad");
            }
            cc.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return cantidad;
    }
}