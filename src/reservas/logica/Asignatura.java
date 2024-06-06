package reservas.logica;

public class Asignatura extends Reservable{
    private String nombre;
    private static String fechaInicioCursada;
    private static String fechaFinCursada;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;

    public Asignatura(String codigoIdentificador, int cantidadInscriptos, String horaFin, String nombre, String diaSemana, String horaInicio) {
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

    public static String getFechaInicioCursada() {
        return fechaInicioCursada;
    }

    public static void setFechaInicioCursada(String fechaInicioCursada) {
        Asignatura.fechaInicioCursada = fechaInicioCursada;
    }

    public static String getFechaFinCursada() {
        return fechaFinCursada;
    }

    public static void setFechaFinCursada(String fechaFinCursada) {
        Asignatura.fechaFinCursada = fechaFinCursada;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public String toString() {
        return "Asignatura{" +
                "nombre='" + nombre + '\'' +
                ", dia de la semana='" + diaSemana + '\'' +
                ", hora de inicio='" + horaInicio + '\'' +
                ", hora de finalizacion='" + horaFin + '\'' +
                  super.toString();
    }
}
