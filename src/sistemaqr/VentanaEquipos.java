/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistemaqr;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class VentanaEquipos extends javax.swing.JFrame {

    DAO dao = new DAO();
    List<Equipo> listaEquipos = new ArrayList<>();

    boolean editando = false;

    /**
     * Creates new form VentanaEquipos
     */
    public VentanaEquipos() {
        initComponents();
        CargarTipos();
        CargarTablaEquipos();
        MostrarEquipos();
        BotonesInicio();
        InformacionInicio();
        CargarDatosEditar();
    }

    public void CargarTipos() {
        String vectorTipos[] = {"Seleccione..", "Computador de escritorio", "Laptop", "Impresora",
            "Switch", "Router", "Access Point", "Servidor", "Proyector", "Otro"};
        DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>(vectorTipos);
        jcbxTipo.setModel(modelo);
    }

    public void CargarTablaEquipos() {
        String vectorTitulos[] = {"ID", "Nombre", "Tipo", "Marca", "Modelo",
            "Ubicación", "Responsable", "IP", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(null, vectorTitulos) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        jtblEquipos.setModel(modelo);
    }

    public void MostrarEquipos() {
        DefaultTableModel modelo = (DefaultTableModel) jtblEquipos.getModel();
        modelo.setRowCount(0);
        listaEquipos = new ArrayList<>();
        String filtro = jtxtBuscar.getText().trim().toLowerCase();

        for (Equipo e : dao.selectEquiposDAO(jchkBajas.isSelected())) {
            String texto = (e.getIdEquipo() + " " + e.getNombre() + " " + e.getTipo() + " "
                    + e.getUbicacion() + " " + e.getResponsable()).toLowerCase();
            if (texto.contains(filtro)) {
                listaEquipos.add(e);
                String dato[] = new String[9];
                dato[0] = String.valueOf(e.getIdEquipo());
                dato[1] = e.getNombre();
                dato[2] = e.getTipo();
                dato[3] = e.getMarca();
                dato[4] = e.getModelo();
                dato[5] = e.getUbicacion();
                dato[6] = e.getResponsable();
                dato[7] = e.getDireccionIp();
                dato[8] = e.isActivo() ? "Activo" : "Dado de baja";
                modelo.addRow(dato);
            }
        }
    }

    public void BotonesInicio() {
        jbtnNuevo.setEnabled(true);
        jbtnGuardar.setEnabled(false);
        jbtnEditar.setEnabled(false);
        jbtnEliminar.setEnabled(false);
        jbtnCancelar.setEnabled(false);
        jbtnReactivar.setEnabled(false);
    }

    public void InformacionInicio() {
        jtxtNombre.setEnabled(false);
        jcbxTipo.setEnabled(false);
        jtxtMarca.setEnabled(false);
        jtxtModelo.setEnabled(false);
        jtxtSerie.setEnabled(false);
        jtxtSO.setEnabled(false);
        jtxtUbicacion.setEnabled(false);
        jtxtResponsable.setEnabled(false);
        jtxtIP.setEnabled(false);
        jtxtMAC.setEnabled(false);
        jtxtaConfiguracion.setEnabled(false);
    }

    public void InformacionNuevo() {
        jtxtNombre.setEnabled(true);
        jcbxTipo.setEnabled(true);
        jtxtMarca.setEnabled(true);
        jtxtModelo.setEnabled(true);
        jtxtSerie.setEnabled(true);
        jtxtSO.setEnabled(true);
        jtxtUbicacion.setEnabled(true);
        jtxtResponsable.setEnabled(true);
        jtxtIP.setEnabled(true);
        jtxtMAC.setEnabled(true);
        jtxtaConfiguracion.setEnabled(true);

        jbtnNuevo.setEnabled(false);
        jbtnGuardar.setEnabled(true);
        jbtnEditar.setEnabled(false);
        jbtnEliminar.setEnabled(false);
        jbtnCancelar.setEnabled(true);
        jtxtNombre.requestFocus();
    }

    public void LimpiarDatos() {
        jtxtId.setText("");
        jtxtNombre.setText("");
        jcbxTipo.setSelectedIndex(0);
        jtxtMarca.setText("");
        jtxtModelo.setText("");
        jtxtSerie.setText("");
        jtxtSO.setText("");
        jtxtUbicacion.setText("");
        jtxtResponsable.setText("");
        jtxtIP.setText("");
        jtxtMAC.setText("");
        jtxtaConfiguracion.setText("");
    }

    public void CancelarDatos() {
        LimpiarDatos();
        InformacionInicio();
        BotonesInicio();
        jtblEquipos.clearSelection();
    }

    public boolean ValidarDatos() {
        if (jtxtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre del equipo");
            jtxtNombre.requestFocus();
            return false;
        }
        if (jcbxTipo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Seleccione el tipo de equipo");
            jcbxTipo.requestFocus();
            return false;
        }
        return true;
    }

    public Equipo CogerDatos() {
        Equipo equipo = new Equipo(
                jtxtNombre.getText().trim(),
                jcbxTipo.getSelectedItem().toString(),
                jtxtMarca.getText().trim(),
                jtxtModelo.getText().trim(),
                jtxtSerie.getText().trim(),
                jtxtUbicacion.getText().trim(),
                jtxtResponsable.getText().trim(),
                jtxtIP.getText().trim(),
                jtxtMAC.getText().trim().toUpperCase(),
                jtxtSO.getText().trim(),
                jtxtaConfiguracion.getText().trim());
        if (!jtxtId.getText().isEmpty()) {
            equipo.setIdEquipo(Integer.parseInt(jtxtId.getText()));
        }
        return equipo;
    }

    public void CargarDatosEditar() {
        jtblEquipos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int fila = jtblEquipos.getSelectedRow();
                if (fila != -1 && !e.getValueIsAdjusting()) {
                    Equipo equipo = listaEquipos.get(fila);
                    jtxtId.setText(String.valueOf(equipo.getIdEquipo()));
                    jtxtNombre.setText(equipo.getNombre());
                    jcbxTipo.setSelectedItem(equipo.getTipo());
                    jtxtMarca.setText(equipo.getMarca());
                    jtxtModelo.setText(equipo.getModelo());
                    jtxtSerie.setText(equipo.getNumeroSerie());
                    jtxtSO.setText(equipo.getSistemaOperativo());
                    jtxtUbicacion.setText(equipo.getUbicacion());
                    jtxtResponsable.setText(equipo.getResponsable());
                    jtxtIP.setText(equipo.getDireccionIp());
                    jtxtMAC.setText(equipo.getDireccionMac());
                    jtxtaConfiguracion.setText(equipo.getConfiguracion());

                    InformacionInicio();
                    jbtnNuevo.setEnabled(true);
                    jbtnGuardar.setEnabled(false);
                    jbtnEditar.setEnabled(equipo.isActivo());
                    jbtnEliminar.setEnabled(equipo.isActivo());
                    jbtnReactivar.setEnabled(!equipo.isActivo());
                    jbtnCancelar.setEnabled(true);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnlDatos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtxtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jcbxTipo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jtxtMarca = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtModelo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtxtSerie = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtxtSO = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtxtUbicacion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jtxtResponsable = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtxtIP = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtxtMAC = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtxtaConfiguracion = new javax.swing.JTextArea();
        jpnlBotones = new javax.swing.JPanel();
        jbtnNuevo = new javax.swing.JButton();
        jbtnGuardar = new javax.swing.JButton();
        jbtnEditar = new javax.swing.JButton();
        jbtnEliminar = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();
        jbtnReactivar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jtxtBuscar = new javax.swing.JTextField();
        jchkBajas = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblEquipos = new javax.swing.JTable();

        jtxtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtBuscarKeyReleased(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de equipos");

        jpnlDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del equipo"));

        jLabel1.setText("ID");

        jtxtId.setEditable(false);

        jLabel2.setText("Nombre *");

        jLabel3.setText("Tipo *");

        jLabel4.setText("Marca");

        jLabel5.setText("Modelo");

        jLabel6.setText("N.º de serie");

        jLabel7.setText("Sist. operativo");

        jLabel8.setText("Ubicación");

        jLabel9.setText("Responsable");

        jLabel10.setText("Dirección IP");

        jLabel11.setText("MAC");

        jLabel12.setText("Configuración");

        jtxtaConfiguracion.setColumns(20);
        jtxtaConfiguracion.setLineWrap(true);
        jtxtaConfiguracion.setRows(5);
        jScrollPane1.setViewportView(jtxtaConfiguracion);

        javax.swing.GroupLayout jpnlDatosLayout = new javax.swing.GroupLayout(jpnlDatos);
        jpnlDatos.setLayout(jpnlDatosLayout);
        jpnlDatosLayout.setHorizontalGroup(
            jpnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jtxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jcbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jtxtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(295, 295, 295)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(390, 390, 390)
                .addComponent(jtxtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jtxtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(295, 295, 295)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(390, 390, 390)
                .addComponent(jtxtSO, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jtxtUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jtxtResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jtxtIP, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(295, 295, 295)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(390, 390, 390)
                .addComponent(jtxtMAC, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpnlDatosLayout.setVerticalGroup(
            jpnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jtxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jcbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jtxtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jtxtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jtxtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jtxtSO, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addComponent(jtxtUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(jtxtResponsable, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(222, 222, 222)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(222, 222, 222)
                .addComponent(jtxtIP, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(222, 222, 222)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(222, 222, 222)
                .addComponent(jtxtMAC, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(252, 252, 252)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jpnlDatosLayout.createSequentialGroup()
                .addGap(252, 252, 252)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jpnlBotones.setBorder(javax.swing.BorderFactory.createTitledBorder("Acciones"));

        jbtnNuevo.setText("Nuevo");
        jbtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoActionPerformed(evt);
            }
        });

        jbtnGuardar.setText("Guardar");
        jbtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarActionPerformed(evt);
            }
        });

        jbtnEditar.setText("Editar");
        jbtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditarActionPerformed(evt);
            }
        });

        jbtnEliminar.setText("Dar de baja");
        jbtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEliminarActionPerformed(evt);
            }
        });

        jbtnCancelar.setText("Cancelar");
        jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelarActionPerformed(evt);
            }
        });

        jbtnReactivar.setText("Reactivar");
        jbtnReactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReactivarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnlBotonesLayout = new javax.swing.GroupLayout(jpnlBotones);
        jpnlBotones.setLayout(jpnlBotonesLayout);
        jpnlBotonesLayout.setHorizontalGroup(
            jpnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlBotonesLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jpnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jbtnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jbtnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jbtnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jbtnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jbtnReactivar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jpnlBotonesLayout.setVerticalGroup(
            jpnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnlBotonesLayout.createSequentialGroup()
                .addGroup(jpnlBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnlBotonesLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jbtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnlBotonesLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jbtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnlBotonesLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jbtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnlBotonesLayout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(jbtnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnlBotonesLayout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(jbtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnReactivar)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jLabel13.setText("Buscar:");

        jchkBajas.setText("Mostrar dados de baja");
        jchkBajas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkBajasActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(jtblEquipos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1025, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpnlDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jpnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(jchkBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jtxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jchkBajas, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jpnlBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(141, 141, 141)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jpnlDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        CancelarDatos();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        editando = false;
        LimpiarDatos();
        InformacionNuevo();
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jbtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarActionPerformed
        if (ValidarDatos()) {
            Equipo equipo = CogerDatos();
            boolean ok;
            if (editando) {
                ok = dao.updateEquipoDAO(equipo);
            } else {
                ok = dao.insertEquipoDAO(equipo) > 0;
            }
            if (ok) {
                editando = false;
                MostrarEquipos();
                CancelarDatos();
            }
        }
    }//GEN-LAST:event_jbtnGuardarActionPerformed

    private void jbtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarActionPerformed
        if (jtblEquipos.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Primero selecciona un equipo de la tabla");
        } else {
            editando = true;
            InformacionNuevo();
        }
    }//GEN-LAST:event_jbtnEditarActionPerformed

    private void jbtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEliminarActionPerformed
        if (jtblEquipos.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Primero selecciona el equipo que deseas dar de baja");
        } else {
            int id = Integer.parseInt(jtxtId.getText());
            String motivo = JOptionPane.showInputDialog(null,
                    "Va a dar de baja el equipo con ID " + id + "\nIngrese el motivo:",
                    "Dar de baja", JOptionPane.WARNING_MESSAGE);
            if (motivo != null && !motivo.trim().isEmpty()) {
                if (dao.bajaEquipoDAO(id, motivo.trim())) {
                    MostrarEquipos();
                    CancelarDatos();
                }
            } else if (motivo != null) {
                JOptionPane.showMessageDialog(null, "Debe indicar el motivo de la baja");
            }
        }
    }//GEN-LAST:event_jbtnEliminarActionPerformed

    private void jbtnReactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnReactivarActionPerformed
        int id = Integer.parseInt(jtxtId.getText());
        int respuesta = JOptionPane.showConfirmDialog(null,
                "¿Reactivar el equipo con ID " + id + "?", "Reactivar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            if (dao.reactivarEquipoDAO(id)) {
                MostrarEquipos();
                CancelarDatos();
            }
        }
    }//GEN-LAST:event_jbtnReactivarActionPerformed

    private void jtxtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtBuscarKeyReleased
        MostrarEquipos();
    }//GEN-LAST:event_jtxtBuscarKeyReleased

    private void jchkBajasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkBajasActionPerformed
        MostrarEquipos();
    }//GEN-LAST:event_jchkBajasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaEquipos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaEquipos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaEquipos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaEquipos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaEquipos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnEditar;
    private javax.swing.JButton jbtnEliminar;
    private javax.swing.JButton jbtnGuardar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JButton jbtnReactivar;
    private javax.swing.JComboBox<String> jcbxTipo;
    private javax.swing.JCheckBox jchkBajas;
    private javax.swing.JPanel jpnlBotones;
    private javax.swing.JPanel jpnlDatos;
    private javax.swing.JTable jtblEquipos;
    private javax.swing.JTextField jtxtBuscar;
    private javax.swing.JTextField jtxtIP;
    private javax.swing.JTextField jtxtId;
    private javax.swing.JTextField jtxtMAC;
    private javax.swing.JTextField jtxtMarca;
    private javax.swing.JTextField jtxtModelo;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JTextField jtxtResponsable;
    private javax.swing.JTextField jtxtSO;
    private javax.swing.JTextField jtxtSerie;
    private javax.swing.JTextField jtxtUbicacion;
    private javax.swing.JTextArea jtxtaConfiguracion;
    // End of variables declaration//GEN-END:variables
}
