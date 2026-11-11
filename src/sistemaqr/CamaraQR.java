package sistemaqr;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Maneja la cámara web USB externa: la lista, la abre, toma cuadros y la cierra.
 *
 * @author Paola Taipe
 */
public class CamaraQR {

    private Webcam camara;

    public List<Webcam> listarCamaras() {
        List<Webcam> lista = new ArrayList<>();
        try {
            lista = Webcam.getWebcams(5000);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo buscar las cámaras: " + ex.getMessage());
        }
        return lista;
    }

    public boolean abrir(Webcam seleccionada) {
        cerrar();
        try {
            camara = seleccionada;
            Dimension vga = WebcamResolution.VGA.getSize();
            for (Dimension d : camara.getViewSizes()) {
                if (d.width == vga.width && d.height == vga.height) {
                    camara.setViewSize(vga);
                }
            }
            camara.open();
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo abrir la cámara. "
                    + "Revise que esté conectada y que ningún otro programa la use.\n" + ex.getMessage());
            camara = null;
            return false;
        }
    }

    public WebcamPanel crearPanelVideo() {
        WebcamPanel panel = new WebcamPanel(camara, false);
        panel.setFillArea(true);
        panel.start();
        return panel;
    }

    public BufferedImage tomarCuadro() {
        if (camara != null && camara.isOpen()) {
            return camara.getImage();
        }
        return null;
    }

    public void cerrar() {
        if (camara != null && camara.isOpen()) {
            camara.close();
        }
        camara = null;
    }

    public boolean estaAbierta() {
        return camara != null && camara.isOpen();
    }
}