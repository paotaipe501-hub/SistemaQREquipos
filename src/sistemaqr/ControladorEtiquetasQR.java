package sistemaqr;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.List;

/**
 *
 * @author juliozuritalopez
 */
public class ControladorEtiquetasQR {

    private BufferedImage etiquetaActual;

    private final DAO dao = new DAO();

    public List<Equipo> obtenerEquiposActivos() {
        return dao.selectEquiposDAO(false);
    }

    public BufferedImage generarEtiqueta(Equipo equipo)
            throws Exception {

        if (equipo == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un equipo");
        }

        if (!equipo.isActivo()) {
            throw new IllegalArgumentException(
                    "El equipo seleccionado no está activo");
        }

        return generarEtiqueta(
                String.valueOf(equipo.getIdEquipo()));
    }

    public BufferedImage generarEtiqueta(String idEquipo) throws Exception {

        if (idEquipo == null || idEquipo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un equipo");
        }

        etiquetaActual
                = ServicioQR.generarEtiqueta(idEquipo);

        return etiquetaActual;
    }

    public void guardarEtiqueta(File archivo) throws Exception {

        if (etiquetaActual == null) {
            throw new IllegalStateException(
                    "Primero debe generar una etiqueta");
        }

        ServicioQR.guardarPNG(etiquetaActual, archivo);
    }

    public void imprimirEtiqueta() throws Exception {

        if (etiquetaActual == null) {
            throw new IllegalStateException(
                    "Primero debe generar una etiqueta");
        }

        PrinterJob trabajo = PrinterJob.getPrinterJob();

        trabajo.setPrintable((graphics, pagina, indice) -> {

            if (indice > 0) {
                return java.awt.print.Printable.NO_SUCH_PAGE;
            }

            Graphics2D g = (Graphics2D) graphics.create();

            try {
                g.translate(
                        pagina.getImageableX(),
                        pagina.getImageableY());

                double escala = Math.min(
                        pagina.getImageableWidth()
                        / etiquetaActual.getWidth(),
                        pagina.getImageableHeight()
                        / etiquetaActual.getHeight());

                escala = Math.min(escala, 1.0);

                g.scale(escala, escala);
                g.drawImage(etiquetaActual, 0, 0, null);

            } finally {
                g.dispose();
            }

            return java.awt.print.Printable.PAGE_EXISTS;
        });

        if (trabajo.printDialog()) {
            trabajo.print();
        }
    }

}
