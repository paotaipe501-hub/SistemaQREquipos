package sistemaqr;

// Modelo
public record CambioConfiguracion(
        int idHistorial,
        int idEquipo,
        String nombreActual,
        String fechaCambio,
        String camposCambiados,
        String nombre,
        String tipo,
        String marca,
        String modelo,
        String numeroSerie,
        String ubicacion,
        String responsable,
        String direccionIp,
        String direccionMac,
        String sistemaOperativo,
        String configuracion) {
}
