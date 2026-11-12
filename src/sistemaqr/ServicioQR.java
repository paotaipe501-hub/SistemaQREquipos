
package sistemaqr;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author juliozuritalopez
 */

public class ServicioQR {

    // Generar un código QR
    public static BufferedImage generarQR(String texto)
            throws WriterException {

        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID está vacío");
        }

        BitMatrix matriz = new QRCodeWriter().encode(
                texto,
                BarcodeFormat.QR_CODE,
                250,
                250
        );

        return MatrixToImageWriter.toBufferedImage(matriz);
    }

    // Crear etiqueta con el QR y el ID del equipo
    public static BufferedImage generarEtiqueta(String id)
            throws WriterException {

        BufferedImage qr = generarQR(id);

        BufferedImage etiqueta = new BufferedImage(
                350, 320, BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = etiqueta.createGraphics();

        try {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 350, 320);

            g.drawImage(qr, 50, 15, null);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 17));

            FontMetrics fm = g.getFontMetrics();
            int x = (350 - fm.stringWidth(id)) / 2;

            g.drawString(id, Math.max(5, x), 295);
        } finally {
            g.dispose();
        }

        return etiqueta;
    }

    // Guardar la etiqueta como PNG
    public static void guardarPNG(
            BufferedImage imagen, File archivo)
            throws IOException {

        if (imagen == null || archivo == null) {
            throw new IllegalArgumentException(
                    "Imagen o archivo no válido"
            );
        }

        if (!ImageIO.write(imagen, "PNG", archivo)) {
            throw new IOException(
                    "No se pudo guardar el PNG"
            );
        }
    }

    // Leer código QR desde una imagen
    public static String leerQR(BufferedImage imagen) {

        if (imagen == null) {
            return null;
        }

        try {
            BinaryBitmap bitmap = new BinaryBitmap(
                new HybridBinarizer(
                    new BufferedImageLuminanceSource(imagen)
                )
            );

            Result resultado =
                    new MultiFormatReader().decode(bitmap);

            return resultado.getText();

        } catch (NotFoundException e) {
            return null;
        }
    }
}
