
package sistemaqr;

import java.awt.image.BufferedImage;
import java.io.File;
/**
 *
 * @author juliozuritalopez
 */

public class PruebaQR {

    public static void main(String[] args) throws Exception {

        BufferedImage etiqueta =
                ServicioQR.generarEtiqueta("EQ-001");

        File archivo = new File("prueba_equipo.png");

        ServicioQR.guardarPNG(etiqueta, archivo);

        BufferedImage qr =
                ServicioQR.generarQR("EQ-001");

        String resultado = ServicioQR.leerQR(qr);

        System.out.println("QR leído: " + resultado);
        System.out.println("Archivo: "
                + archivo.getAbsolutePath());
    }
}
