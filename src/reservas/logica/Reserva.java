package reservas.logica;

public class Reserva {
  private static int numCodigo=0;
  private final int CODIGO=numCodigo;
  private String fecha;
  private String horaInicio;
  private String horaFin;
  private Reservable reservable;

  public Reserva(String fecha, String horaInicio, String horaFin, Reservable reservable) {
    numCodigo++;
    this.fecha = fecha;
    this.horaInicio = horaInicio;
    this.horaFin = horaFin;
    this.reservable = reservable;
  }

  public int getCODIGO() {
    return CODIGO;
  }

  public String getFecha() {
    return fecha;
  }

  public void setFecha(String fecha) {
    this.fecha = fecha;
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

  public Reservable getReservable() {
    return reservable;
  }

  public void setReservable(Reservable reservable) {
    this.reservable = reservable;
  }

  @Override
  public String toString() {
    return "Reserva{" +
            "Codigo de reserva=" + CODIGO +
            ", fecha='" + fecha + '\'' +
            ", hora de inicio='" + horaInicio + '\'' +
            ", hora de finalizacion='" + horaFin + '\'' +
            ", tipo=" + reservable.getClass().getSimpleName() +
            '}';
  }
}
