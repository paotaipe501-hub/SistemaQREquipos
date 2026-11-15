package sistemaqr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

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
}
