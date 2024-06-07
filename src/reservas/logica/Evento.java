package reservas.logica;

import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Evento extends Reservable{
    private LocalDate fechaInicio;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String descripcion;
    private float costoAlquiler;

    public Evento(String codigoIdentificador, int cantidadInscriptos, LocalTime horaInicio, LocalTime horaFin, String descripcion,float costoAlquiler,LocalDate fechaInicio) {
        super(codigoIdentificador, cantidadInscriptos);
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.descripcion = descripcion;
        this.costoAlquiler = costoAlquiler;
        this.fechaInicio= fechaInicio;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        fechaInicio = fechaInicio;
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

    public float getCostoAlquiler() {
        return costoAlquiler;
    }

    public void setCostoAlquiler(float costoAlquiler) {
        this.costoAlquiler = costoAlquiler;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "fecha de inicio='" + fechaInicio + '\'' +
                ", hora de inicio='" + horaInicio + '\'' +
                ", hora de finalizacion='" + horaFin + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", costoAlquiler=" + costoAlquiler +
                 super.toString();
    }
}
