package sistemaqr;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VentanaHistorialConfig extends javax.swing.JFrame {
    private final DAO dao = new DAO();
    private List<CambioConfiguracion> cambios = List.of();

    public VentanaHistorialConfig() {
        initComponents();
        btnBuscar.addActionListener(e -> buscarPorId());
        btnTodos.addActionListener(e -> verTodos());
        txtId.addActionListener(e -> buscarPorId());
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDetalle();
            }
        });
        mostrarCambios(0);
    }

    private void buscarPorId() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            if (id <= 0) {
                throw new NumberFormatException();
            }
            mostrarCambios(id);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Escriba un ID de equipo mayor que cero");
        }
    }

    private void verTodos() {
        txtId.setText("");
        mostrarCambios(0);
    }

    private void mostrarCambios(int idEquipo) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        tabla.clearSelection();
        modelo.setRowCount(0);
        cambios = dao.selectHistorialDAO(idEquipo);
        for (CambioConfiguracion cambio : cambios) {
            modelo.addRow(new Object[] {
                cambio.idHistorial(), cambio.fechaCambio(), cambio.idEquipo(), cambio.nombreEquipo(),
                cambio.direccionIp(), cambio.direccionMac(), cambio.sistemaOperativo()
            });
        }
        lblTotal.setText("Cambios: " + cambios.size());
        detalle.setText("");
    }

    private void mostrarDetalle() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            detalle.setText("");
            return;
        }
        CambioConfiguracion cambio = cambios.get(tabla.convertRowIndexToModel(fila));
        detalle.setText("Equipo: " + cambio.nombreEquipo() + " (ID " + cambio.idEquipo() + ")\n"
                + "Fecha del cambio: " + cambio.fechaCambio() + "\n"
                + "IP anterior: " + java.util.Objects.toString(cambio.direccionIp(), "") + "\n"
                + "MAC anterior: " + java.util.Objects.toString(cambio.direccionMac(), "") + "\n"
                + "Sistema operativo anterior: " + java.util.Objects.toString(cambio.sistemaOperativo(), "")
                + "\n\nConfiguración anterior\n" + java.util.Objects.toString(cambio.configuracion(), ""));
        detalle.setCaretPosition(0);
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

        tabla.setModel(new DefaultTableModel(new String[] {
            "N.º", "Fecha", "ID equipo", "Equipo", "IP anterior", "MAC anterior", "SO anterior"
        }, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        });
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
