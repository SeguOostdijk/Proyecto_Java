package reservas.logica;

public class Evento extends Reservable{
    private String fechaInicio;
    private String horaInicio;
    private String horaFin;
    private String descripcion;
    private boolean externo;

    public Evento(String codigoIdentificador, int cantidadInscriptos, String horaInicio, String horaFin, String descripcion, boolean externo,String fechaInicio) {
        super(codigoIdentificador, cantidadInscriptos);
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.descripcion = descripcion;
        this.externo = externo;
        this.fechaInicio= fechaInicio;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        fechaInicio = fechaInicio;
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
                "fechaInicio='" + fechaInicio + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFin='" + horaFin + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", externo=" + externo +
                 super.toString();
    }
}
