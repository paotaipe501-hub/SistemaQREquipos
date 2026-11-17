package sistemaqr;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VentanaHistorialConfig extends javax.swing.JFrame {
    private final DAO dao = new DAO();
    private List<CambioConfiguracion> cambios = List.of();

    public VentanaHistorialConfig() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        filtros = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel("ID del equipo");
        txtId = new javax.swing.JTextField(8);
        btnBuscar = new javax.swing.JButton("Buscar");
        btnTodos = new javax.swing.JButton("Ver todos");
        lblTotal = new javax.swing.JLabel("Cambios: 0");
        contenido = new javax.swing.JPanel(new java.awt.GridLayout(2, 1));
        tablaScroll = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        detalleScroll = new javax.swing.JScrollPane();
        detalle = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Historial de configuración");
        filtros.add(lblId);
        filtros.add(txtId);
        filtros.add(btnBuscar);
        filtros.add(btnTodos);
        filtros.add(lblTotal);
        getContentPane().add(filtros, java.awt.BorderLayout.NORTH);

        tablaScroll.setViewportView(tabla);
        contenido.add(tablaScroll);
        detalle.setEditable(false);
        detalle.setRows(7);
        detalle.setLineWrap(true);
        detalle.setWrapStyleWord(true);
        detalleScroll.setViewportView(detalle);
        contenido.add(detalleScroll);
        getContentPane().add(contenido, java.awt.BorderLayout.CENTER);
        pack();
    }//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnTodos;
    private javax.swing.JPanel contenido;
    private javax.swing.JTextArea detalle;
    private javax.swing.JScrollPane detalleScroll;
    private javax.swing.JPanel filtros;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tabla;
    private javax.swing.JScrollPane tablaScroll;
    private javax.swing.JTextField txtId;
    // End of variables declaration//GEN-END:variables
}
