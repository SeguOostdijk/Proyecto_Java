package reservas.logica;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventoExterno extends Evento{
    private float costoAlquiler;

    public EventoExterno(String codigoIdentificador, int cantidadInscriptos, LocalTime horaInicio, LocalTime horaFin, String descripcion, LocalDate fechaInicio, float costoAlquiler){
        super(codigoIdentificador,cantidadInscriptos,horaInicio,horaFin,descripcion,fechaInicio);
        this.costoAlquiler=costoAlquiler;
    }

    public float getCostoAlquiler() {
        return costoAlquiler;
    }

    public void setCostoAlquiler(float costoAlquiler) {
        this.costoAlquiler = costoAlquiler;
    }
}
