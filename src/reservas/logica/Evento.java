package reservas.logica;

import java.time.LocalDate;
import java.time.LocalTime;
/**
 * Es una clase abstracta que define los atributos y metodos comunes entre eventos internos y externos
 */
public abstract class Evento extends Reservable{
    private LocalDate fechaInicio;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String descripcion;

    public Evento(String codigoIdentificador, int cantidadInscriptos, LocalTime horaInicio, LocalTime horaFin, String descripcion,LocalDate fechaInicio) {
        super(codigoIdentificador, cantidadInscriptos);
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.descripcion = descripcion;
        this.fechaInicio= fechaInicio;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "fecha de inicio='" + fechaInicio + '\'' +
                ", hora de inicio='" + horaInicio + '\'' +
                ", hora de finalizacion='" + horaFin + '\'' +
                ", descripcion='" + descripcion + '\'' +
                 super.toString();
    }
}
