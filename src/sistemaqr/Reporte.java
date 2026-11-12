package sistemaqr;

import java.io.File;
import java.sql.Connection;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Muestra los reportes de JasperReports (carpeta reportes del proyecto) y los exporta a PDF.
 *
 * @author Diana Tituaña
 */
public class Reporte {


    public void mostrar(String archivoJrxml, Map<String, Object> parametros) {
        JasperPrint jp = llenar(archivoJrxml, parametros);
        if (jp != null) {
            JasperViewer.viewReport(jp, false);
        }
    }

    private JasperPrint llenar(String archivoJrxml, Map<String, Object> parametros) {
        JasperPrint jp = null;
        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            JasperReport jr = JasperCompileManager.compileReport(archivoJrxml);
            jp = JasperFillManager.fillReport(jr, parametros, cc);
            cc.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo generar el reporte: " + ex.getMessage());
        }
        return jp;
    }

   
    public void exportarPDF(String archivoJrxml, Map<String, Object> parametros, String nombreSugerido) {
        JasperPrint jp = llenar(archivoJrxml, parametros);
        if (jp == null) {
            return;
        }
        JFileChooser selector = new JFileChooser();
        selector.setFileFilter(new FileNameExtensionFilter("Documento PDF", "pdf"));
        selector.setSelectedFile(new File(nombreSugerido + ".pdf"));
        if (selector.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = selector.getSelectedFile().getAbsolutePath();
            if (!ruta.toLowerCase().endsWith(".pdf")) {
                ruta = ruta + ".pdf";
            }
            try {
                JasperExportManager.exportReportToPdfFile(jp, ruta);
                JOptionPane.showMessageDialog(null, "PDF guardado en:\n" + ruta);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo guardar el PDF: " + ex.getMessage());
            }
        }
    }
}
