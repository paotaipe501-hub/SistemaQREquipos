package sistemaqr;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VentanaLeerImagenQR extends javax.swing.JFrame {

    public VentanaLeerImagenQR() {
        initComponents();
    }

    private void seleccionarImagen() {
        JFileChooser selector = new JFileChooser();
        selector.setFileFilter(new FileNameExtensionFilter("Imágenes PNG y JPG", "png", "jpg", "jpeg"));
        selector.setAcceptAllFileFilterUsed(false);
        if (selector.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = selector.getSelectedFile();
        jlblArchivo.setText(archivo.getName());
        jlblImagen.setIcon(null);
        jlblImagen.setText("Vista previa");
        try {
            BufferedImage imagen = ImageIO.read(archivo);
            if (imagen == null) {
                jlblEstado.setText("El archivo no es una imagen válida.");
                return;
            }
            mostrarVistaPrevia(imagen);
        } catch (IOException ex) {
            jlblEstado.setText("No se pudo abrir la imagen: " + ex.getMessage());
        }
    }

    private void mostrarVistaPrevia(BufferedImage imagen) {
        double escala = Math.min(1.0, Math.min(
                280.0 / imagen.getWidth(), 280.0 / imagen.getHeight()));
        int ancho = Math.max(1, (int) Math.round(imagen.getWidth() * escala));
        int alto = Math.max(1, (int) Math.round(imagen.getHeight() * escala));
        Image vista = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        jlblImagen.setIcon(new ImageIcon(vista));
        jlblImagen.setText("");
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
        jbtnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeleccionarActionPerformed(evt);
            }
        });
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

    private void jbtnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeleccionarActionPerformed
        seleccionarImagen();
    }//GEN-LAST:event_jbtnSeleccionarActionPerformed

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
