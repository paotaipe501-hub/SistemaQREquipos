package sistemaqr;

/**
 * Una lectura de codigo QR registrada en la tabla lectura_qr.
 *
 * @author Diana Tituaña
 */
public class Lectura {

    private int idLectura;
    private String fechaLectura;
    private int idEquipo;
    private String nombre;
    private String ubicacion;

    public Lectura(int idLectura, String fechaLectura, int idEquipo, String nombre, String ubicacion) {
        this.idLectura = idLectura;
        this.fechaLectura = fechaLectura;
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public int getIdLectura() { return idLectura; }
    public String getFechaLectura() { return fechaLectura; }
    public int getIdEquipo() { return idEquipo; }
    public String getNombre() { return nombre; }
    public String getUbicacion() { return ubicacion; }
}