package sistemaqr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Genera y lee codigos QR con la libreria ZXing.
 *
 * @author Diana Tituaña
 */
public class ServicioQR {

    // ------------------------------------------------------------ GENERAR
    public BufferedImage generarQR(String texto, int tamanio) {
        BufferedImage imagen = null;
        try {
            Map<EncodeHintType, Object> opciones = new HashMap<>();
            opciones.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            opciones.put(EncodeHintType.MARGIN, 2);
            BitMatrix matriz = new QRCodeWriter().encode(texto, BarcodeFormat.QR_CODE, tamanio, tamanio, opciones);
            imagen = MatrixToImageWriter.toBufferedImage(matriz);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo generar el QR: " + ex.getMessage());
        }
        return imagen;
    }

    // ------------------------------------------------------------ ETIQUETA
    /** Etiqueta para pegar en el equipo: el QR guarda solo el ID del equipo. */
    public BufferedImage generarEtiqueta(int idEquipo, String nombre) {
        int tam = 300;
        BufferedImage qr = generarQR(String.valueOf(idEquipo), tam);
        if (qr == null) {
            return null;
        }
        BufferedImage etiqueta = new BufferedImage(tam, tam + 70, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = etiqueta.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, tam, tam + 70);
        g.drawImage(qr, 0, 0, null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        escribirCentrado(g, "ID " + idEquipo, tam, tam + 24);
        String texto = nombre;
        if (texto.length() > 34) {
            texto = texto.substring(0, 33) + "...";
        }
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        escribirCentrado(g, texto, tam, tam + 50);
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(0, 0, tam - 1, tam + 69);
        g.dispose();
        return etiqueta;
    }

    private void escribirCentrado(Graphics2D g, String texto, int ancho, int y) {
        FontMetrics fm = g.getFontMetrics();
        int x = (ancho - fm.stringWidth(texto)) / 2;
        g.drawString(texto, Math.max(4, x), y);
    }
    
        // ------------------------------------------------------------ GUARDAR
    public boolean guardarPNG(BufferedImage imagen, File archivo) {
        try {
            ImageIO.write(imagen, "png", archivo);
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo guardar la imagen: " + ex.getMessage());
            return false;
        }
    }
}
