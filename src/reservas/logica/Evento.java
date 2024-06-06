package reservas.logica;

import java.time.LocalDate;
import java.time.LocalTime;

public class Evento extends Reservable{
    private LocalDate fechaInicio;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String descripcion;
    private boolean externo;

    public Evento(String codigoIdentificador, int cantidadInscriptos, LocalTime horaInicio, LocalTime horaFin, String descripcion, boolean externo,LocalDate fechaInicio) {
        super(codigoIdentificador, cantidadInscriptos);
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.descripcion = descripcion;
        this.externo = externo;
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

    public boolean isExterno() {
        return externo;
    }

    public void setExterno(boolean externo) {
        this.externo = externo;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "fecha de inicio='" + fechaInicio + '\'' +
                ", hora de inicio='" + horaInicio + '\'' +
                ", hora de finalizacion='" + horaFin + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", externo=" + externo +
                 super.toString();
    }
}
