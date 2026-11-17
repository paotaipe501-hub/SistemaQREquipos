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
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.util.List;

// Servicio QR
public class ServicioQR {

    // Generar QR
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

    public boolean guardarPNG(BufferedImage imagen, File archivo) {
        try {
            ImageIO.write(imagen, "png", archivo);
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo guardar la imagen: " + ex.getMessage());
            return false;
        }
    }

    // Leer QR
    public String leerQR(BufferedImage imagen) {
        if (imagen == null) {
            return null;
        }
        try {
            Map<DecodeHintType, Object> opciones = new HashMap<>();
            opciones.put(DecodeHintType.POSSIBLE_FORMATS, List.of(BarcodeFormat.QR_CODE));
            opciones.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            BinaryBitmap mapa = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(imagen)));
            Result resultado = new MultiFormatReader().decode(mapa, opciones);
            return resultado.getText().trim();
        } catch (NotFoundException ex) {
            return null;
        }
    }

    public String leerQRArchivo(File archivo) {
        try {
            return leerQR(ImageIO.read(archivo));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo abrir la imagen: " + ex.getMessage());
            return null;
        }
    }
}
