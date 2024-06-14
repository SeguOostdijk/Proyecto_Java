package reservas.logica;

import java.time.LocalDate;
import java.time.LocalTime;

public class Asignatura extends Reservable{
    private String nombre;
    private static LocalDate fechaInicioCursada;
    private static LocalDate fechaFinCursada;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Asignatura(String codigoIdentificador, int cantidadInscriptos, LocalTime horaFin, String nombre, String diaSemana, LocalTime horaInicio) {
        super(codigoIdentificador, cantidadInscriptos);
        this.horaFin = horaFin;
        this.nombre = nombre;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static LocalDate getFechaInicioCursada() {
        return fechaInicioCursada;
    }

    public static void setFechaInicioCursada(LocalDate fechaInicioCursada) {
        Asignatura.fechaInicioCursada = fechaInicioCursada;
    }

    public static LocalDate getFechaFinCursada() {
        return fechaFinCursada;
    }

    public static void setFechaFinCursada(LocalDate fechaFinCursada) {
        Asignatura.fechaFinCursada = fechaFinCursada;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
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

    @Override
    public String toString() {
        return "Asignatura{" +
                "nombre='" + nombre + '\'' +
                ", dia de la semana='" + diaSemana + '\'' +
                ", hora de inicio='" + horaInicio + '\'' +
                ", hora de finalización='" + horaFin + '\'' +
                ", " + super.toString() +
                '}';
    }
}
