package reservas.logica;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventoExterno extends Evento{
    private float costoAlquiler;
    String nombreOrganizacion;

    public EventoExterno(String codigoIdentificador, int cantidadInscriptos, LocalTime horaInicio, LocalTime horaFin, String descripcion, LocalDate fechaInicio, float costoAlquiler,String nombreOrganizacion){
        super(codigoIdentificador,cantidadInscriptos,horaInicio,horaFin,descripcion,fechaInicio);
        this.costoAlquiler=costoAlquiler;
        this.nombreOrganizacion=nombreOrganizacion;
    }

    public float getCostoAlquiler() {
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
}
