package reservas.logica;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class Aula implements Serializable,Comparable<Aula>{
    private int numeroAula;
    private int capacidadMaxima;
    private HashMap <Integer,Reserva> listaReservas;
    private Universidad universidad;


    public Aula(int capacidadMaxima, Integer numeroAula) {
        this.capacidadMaxima = capacidadMaxima;
        this.numeroAula = numeroAula;
        listaReservas = new HashMap();
    }

    public float montoRecaudado(){
        float sumadorCostos=0;
        for (Reserva reserva : listaReservas.values()) {
            if(reserva.getReservable() instanceof EventoExterno)
                sumadorCostos+=((EventoExterno) reserva.getReservable()).getCostoAlquiler();
        }
        return sumadorCostos;
    }

    public boolean noSuperaCapacidad(int cantidadPersonas){
        return cantidadPersonas<=capacidadMaxima;
    }

    public boolean estaDisponible(LocalTime horaInicio,LocalTime horaFin, LocalDate dia){
        for (Reserva reserva : listaReservas.values()) {
            if(reserva.getFecha().isEqual(dia))
                if(horaFin.isBefore(reserva.getHoraFin()) && horaInicio.isAfter(reserva.getHoraInicio()))
                    return false;
        }
        return true;
    }

    public void cancelaReserva(int codigoEntidad){
        listaReservas.remove(codigoEntidad);
    }

    public int cantidadReservas(){
        if(listaReservas.isEmpty())
            return 0;
        else
            return listaReservas.size();

    }

    public int getCapacidadmaxima() {
        return capacidadMaxima;
    }

    public int getNumero() {
        return numeroAula;
    }

    public int getPiso(){
        return numeroAula/100;
    }

    public void agregaReservas(int codigoAsignatura) {  //asignatura o evento interno
            Asignatura asignatura = universidad.getAsignatura(codigoAsignatura);
            LocalDate fechaInicio = Asignatura.getFechaInicioCursada();
            LocalDate fechaFin = Asignatura.getFechaFinCursada();

            LocalDate fechaActual = fechaInicio;

            while(!fechaActual.isAfter(fechaFin)) {
                Reserva nuevaReserva = new Reserva(fechaActual, asignatura.getHoraInicio(), asignatura.getHoraFin(), asignatura);
                if (estaDisponible(asignatura.getHoraInicio(), asignatura.getHoraFin(), fechaActual) && noSuperaCapacidad(asignatura.getCantidadInscriptos()))
                    listaReservas.put(nuevaReserva.getCODIGO(), nuevaReserva);
                else
                    throw new NoSuchElementException("No se pudo realizar la reserva");
                fechaActual = fechaActual.plusWeeks(1);
            }
    }

    public void agregaReservas(int codigoCurso, LocalDate fechaInicio, LocalTime horaInicio, LocalTime horaFin){
            CursoExtension curso = universidad.getCursoExtension(codigoCurso);
            LocalDate fechaActual = fechaInicio;
            int clasesReservadas = 0;
            int cantidadClases = curso.getCantidadClases();

            while(clasesReservadas < cantidadClases) {
                Reserva nuevaReserva = new Reserva(fechaInicio, horaInicio, horaFin, curso);
                if (estaDisponible(horaInicio, horaFin, fechaActual) && noSuperaCapacidad(curso.getCantidadInscriptos()))
                    listaReservas.put(nuevaReserva.getCODIGO(), nuevaReserva);
                else
                    throw new NoSuchElementException("No se pudo realizar la reserva");
                clasesReservadas++;
                fechaActual = fechaActual.plusWeeks(1);
            }
    }


    public void agregaReservasExterno(int codigoEvento, String nombreOrganizacion, float costoAlquiler){
            Evento evento = universidad.getEvento(codigoEvento);
            Reserva nuevaReserva = new Reserva(evento.getFechaInicio(),evento.getHoraInicio(),evento.getHoraFin(),evento);
            if(estaDisponible(evento.getHoraInicio(),evento.getHoraFin(),evento.getFechaInicio()) && noSuperaCapacidad(evento.getCantidadInscriptos()))
                listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
            System.out.println("Reserva para evento " + " realizada con exito");
    }

    @Override
    public String toString() {
        return "Aula{" +
                "numero de aula=" + numeroAula +
                ", capacidad maxima=" + capacidadMaxima +
                ", cantidad de reservas="+listaReservas.size()+
                ", lista de Reservas=" + listaReservas +
                '}';
    }

    @Override
    public int compareTo(Aula o) {
        return this.getNumero()-o.getNumero();
    }
}
