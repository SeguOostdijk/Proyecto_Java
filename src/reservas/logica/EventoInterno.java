package reservas.logica;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventoInterno extends Evento{
    public EventoInterno(String codigoIdentificador, int cantidadInscriptos, LocalTime horaInicio, LocalTime horaFin, String descripcion, LocalDate fechaInicio){
        super(codigoIdentificador,cantidadInscriptos,horaInicio,horaFin,descripcion,fechaInicio);
    }
}
