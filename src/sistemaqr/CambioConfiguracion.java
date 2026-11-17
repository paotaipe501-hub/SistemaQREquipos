package sistemaqr;

/** Configuración anterior de un equipo, registrada al guardar un cambio. */
public record CambioConfiguracion(
        int idHistorial,
        int idEquipo,
        String nombreEquipo,
        String fechaCambio,
        String direccionIp,
        String direccionMac,
        String sistemaOperativo,
        String configuracion) {
}
