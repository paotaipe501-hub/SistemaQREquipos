package sistemaqr;

import java.sql.Connection;
import java.util.Map;
import javax.swing.JOptionPane;
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
}
