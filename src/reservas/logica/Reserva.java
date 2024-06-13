package reservas.logica;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Reserva implements Serializable {
  private static int numCodigo=0;
  private int CODIGO;
  private LocalDate fecha;
  private LocalTime horaInicio;
  private LocalTime horaFin;
  private Reservable reservable;

  public Reserva(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Reservable reservable) {
    numCodigo++;
    CODIGO = numCodigo;
    this.fecha = fecha;
    this.horaInicio = horaInicio;
    this.horaFin = horaFin;
    this.reservable = reservable;
  }

  public int getCODIGO() {
    return CODIGO;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
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

  public Reservable getReservable() {
    return reservable;
  }

  public void setReservable(Reservable reservable) {
    this.reservable = reservable;
  }

  @Override
  public String toString() {
    return  "{Codigo de reserva=" + CODIGO +
            ", fecha='" + fecha + '\'' +
            ", hora de inicio='" + horaInicio + '\'' +
            ", hora de finalizacion='" + horaFin + '\'' +
            ", tipo=" + reservable.getClass().getSimpleName() +
            "}\n";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Reserva reserva = (Reserva) o;
    return CODIGO == reserva.CODIGO;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(CODIGO);
  }
}
