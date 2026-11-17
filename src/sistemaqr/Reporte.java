package sistemaqr;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

// Reportes
public class Reporte {

    public void mostrar(String archivoJasper, Map<String, Object> parametros) {
        JasperPrint jp = llenar(archivoJasper, parametros);
        if (jp != null) {
            JasperViewer.viewReport(jp, false);
        }
    }

    private JasperPrint llenar(String archivoJasper, Map<String, Object> parametros) {
        JasperPrint jp = null;
        try {
            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            JasperReport jr = cargarReporte(archivoJasper);
            jp = JasperFillManager.fillReport(jr, parametros, cc);
            cc.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo generar el reporte: " + ex.getMessage());
        }
        return jp;
    }

    private JasperReport cargarReporte(String archivoJasper) throws Exception {
        if (archivoJasper.startsWith("src/")) {
            try (InputStream reporte = Reporte.class.getResourceAsStream(archivoJasper.substring(3))) {
                if (reporte != null) {
                    return (JasperReport) JRLoader.loadObject(reporte);
                }
            }
        }
        return (JasperReport) JRLoader.loadObjectFromFile(archivoJasper);
    }

    public void exportarPDF(String archivoJasper, Map<String, Object> parametros, String nombreSugerido) {
        JasperPrint jp = llenar(archivoJasper, parametros);
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
