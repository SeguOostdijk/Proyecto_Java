package reservas.logica;

import java.time.LocalDate;
import java.time.LocalTime;
/**
 * Define los atributos y metodos de eventos internos
 */
public class EventoExterno extends Evento {
    private double costoAlquiler;
    String nombreOrganizacion;

    public EventoExterno(String codigoIdentificador, int cantidadInscriptos, LocalTime horaInicio, LocalTime horaFin, String descripcion, LocalDate fechaInicio, double costoAlquiler, String nombreOrganizacion) {
        super(codigoIdentificador, cantidadInscriptos, horaInicio, horaFin, descripcion, fechaInicio);
        this.costoAlquiler = costoAlquiler;
        this.nombreOrganizacion = nombreOrganizacion;
    }

    public double getCostoAlquiler() {
        return costoAlquiler;
    }

    public void setCostoAlquiler(float costoAlquiler) {
        this.costoAlquiler = costoAlquiler;
    }

    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }

    public void setNombreOrganizacion(String nombreOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
    }

    @Override
    public String toString() {
        return  super.toString() +
                "costoAlquiler=" + costoAlquiler +
                ", nombreOrganizacion='" + nombreOrganizacion + '\'' +
                "} ";
    }
}