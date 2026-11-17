package sistemaqr;

public class VentanaLeerImagenQR extends javax.swing.JFrame {

    public VentanaLeerImagenQR() {
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jbtnSeleccionar = new javax.swing.JButton();
        jlblArchivo = new javax.swing.JLabel();
        jlblImagen = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtxtaDatos = new javax.swing.JTextArea();
        jlblEstado = new javax.swing.JLabel();
        jbtnVerReporte = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Leer código QR desde una imagen");
        jbtnSeleccionar.setText("Seleccionar imagen...");
        jlblArchivo.setText("Ningún archivo seleccionado");
        jlblImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblImagen.setText("Vista previa");
        jlblImagen.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtxtaDatos.setEditable(false);
        jtxtaDatos.setLineWrap(true);
        jtxtaDatos.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jtxtaDatos);
        jlblEstado.setText("Seleccione la imagen de una etiqueta QR");
        jlblEstado.setOpaque(true);
        jbtnVerReporte.setText("Ver reporte");
        jbtnVerReporte.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnSeleccionar)
                        .addComponent(jlblArchivo, 0, 320, Short.MAX_VALUE)
                        .addComponent(jbtnVerReporte))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(jlblImagen, 240, 300, 300)
                        .addComponent(jScrollPane1, 240, 465, Short.MAX_VALUE))
                .addComponent(jlblEstado, 0, 780, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtnSeleccionar)
                        .addComponent(jlblArchivo)
                        .addComponent(jbtnVerReporte))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jlblImagen, 240, 300, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, 240, 300, Short.MAX_VALUE))
                .addComponent(jlblEstado, 30, 30, 30));
        pack();
    }//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnSeleccionar;
    private javax.swing.JButton jbtnVerReporte;
    private javax.swing.JLabel jlblArchivo;
    private javax.swing.JLabel jlblEstado;
    private javax.swing.JLabel jlblImagen;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jtxtaDatos;
    // End of variables declaration//GEN-END:variables
}
