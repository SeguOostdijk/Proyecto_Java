package reservas.logica;

import java.time.LocalDate;
import java.time.LocalTime;
/**
 * Define los atributos y metodos de eventos internos (los mismos que su clase padre evento)
 */
public class EventoInterno extends Evento{
    public EventoInterno(String codigoIdentificador, int cantidadInscriptos, LocalTime horaInicio, LocalTime horaFin, String descripcion, LocalDate fechaInicio){
        super(codigoIdentificador,cantidadInscriptos,horaInicio,horaFin,descripcion,fechaInicio);
    }
}
