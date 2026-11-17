package sistemaqr;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class VentanaReporte extends javax.swing.JFrame {

    // Instancias: DAO, Reporte
    DAO dao = new DAO();
    Reporte reporte = new Reporte();

    public VentanaReporte() {
        initComponents();
    }

    public int ValidarId() {
        int id;
        try {
            id = Integer.parseInt(jtxtIdEquipo.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un ID válido (solo números)");
            jtxtIdEquipo.requestFocus();
            return 0;
        }
        Equipo e = dao.buscarEquipoDAO(id);
        if (e == null) {
            jlblEquipo.setText(" ");
            JOptionPane.showMessageDialog(null, "No existe un equipo con ID " + id);
            return 0;
        }
        jlblEquipo.setText("Equipo: " + e.getNombre());
        return id;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtxtIdEquipo = new javax.swing.JTextField();
        jlblEquipo = new javax.swing.JLabel();
        jbtnVerReporte = new javax.swing.JButton();
        jbtnExportarPDF = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reporte de equipo por ID");

        jLabel1.setText("ID del equipo:");

        jtxtIdEquipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtIdEquipoActionPerformed(evt);
            }
        });

        jlblEquipo.setFont(jlblEquipo.getFont().deriveFont(jlblEquipo.getFont().getStyle() | java.awt.Font.BOLD));
        jlblEquipo.setText(" ");

        jbtnVerReporte.setText("Ver reporte");
        jbtnVerReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnVerReporteActionPerformed(evt);
            }
        });

        jbtnExportarPDF.setText("Exportar PDF");
        jbtnExportarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExportarPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtIdEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlblEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnVerReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jbtnExportarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtIdEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jlblEquipo)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnVerReporte)
                    .addComponent(jbtnExportarPDF))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnVerReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnVerReporteActionPerformed
        int id = ValidarId();
        if (id > 0) {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_EQUIPO", id);
            reporte.mostrar("src/Reportes/ReporteEquipo.jasper", parametros);
        }
    }//GEN-LAST:event_jbtnVerReporteActionPerformed

    private void jbtnExportarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExportarPDFActionPerformed
        int id = ValidarId();
        if (id > 0) {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_EQUIPO", id);
            reporte.exportarPDF("src/Reportes/ReporteEquipo.jasper", parametros, "Reporte_Equipo_ID_" + id);
        }
    }//GEN-LAST:event_jbtnExportarPDFActionPerformed

    private void jtxtIdEquipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtIdEquipoActionPerformed
        jbtnVerReporte.doClick();
    }//GEN-LAST:event_jtxtIdEquipoActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaReporte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaReporte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaReporte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaReporte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaReporte().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jbtnExportarPDF;
    private javax.swing.JButton jbtnVerReporte;
    private javax.swing.JLabel jlblEquipo;
    private javax.swing.JTextField jtxtIdEquipo;
    // End of variables declaration//GEN-END:variables
}
