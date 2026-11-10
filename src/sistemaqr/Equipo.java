package sistemaqr;

/**
 * Equipo de la tabla equipo. Su ID es lo que va dentro del codigo QR.
 *
 * @author Diana Tituaña
 */
public class Equipo {

    private int idEquipo;
    private String nombre;
    private String tipo;
    private String marca;
    private String modelo;
    private String numeroSerie;
    private String ubicacion;
    private String responsable;
    private String direccionIp;
    private String direccionMac;
    private String sistemaOperativo;
    private String configuracion;
    private String estado;
    private String fechaRegistro;
    private String fechaBaja;
    private String motivoBaja;

    public Equipo() {
        this.estado = "ACTIVO";
    }

    public Equipo(String nombre, String tipo, String marca, String modelo,
            String numeroSerie, String ubicacion, String responsable, String direccionIp,
            String direccionMac, String sistemaOperativo, String configuracion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.ubicacion = ubicacion;
        this.responsable = responsable;
        this.direccionIp = direccionIp;
        this.direccionMac = direccionMac;
        this.sistemaOperativo = sistemaOperativo;
        this.configuracion = configuracion;
        this.estado = "ACTIVO";
    }

    public boolean isActivo() {
        return "ACTIVO".equals(estado);
    }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) { this.idEquipo = idEquipo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public String getDireccionIp() { return direccionIp; }
    public void setDireccionIp(String direccionIp) { this.direccionIp = direccionIp; }

    public String getDireccionMac() { return direccionMac; }
    public void setDireccionMac(String direccionMac) { this.direccionMac = direccionMac; }

    public String getSistemaOperativo() { return sistemaOperativo; }
    public void setSistemaOperativo(String sistemaOperativo) { this.sistemaOperativo = sistemaOperativo; }

    public String getConfiguracion() { return configuracion; }
    public void setConfiguracion(String configuracion) { this.configuracion = configuracion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getFechaBaja() { return fechaBaja; }
    public void setFechaBaja(String fechaBaja) { this.fechaBaja = fechaBaja; }

    public String getMotivoBaja() { return motivoBaja; }
    public void setMotivoBaja(String motivoBaja) { this.motivoBaja = motivoBaja; }

    @Override
    public String toString() {
        return idEquipo + " - " + nombre;
    }
}