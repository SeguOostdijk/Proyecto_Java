package reservas.logica;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class Aula implements Serializable {
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
            if(reserva.getReservable() instanceof Evento)
                sumadorCostos+=((Evento) reserva.getReservable()).getCostoAlquiler();
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

    public void agregaReservas(int codigoReservador) {  //asignatura o evento interno
        try {
            Reservable reservable = universidad.getRe(codigoAsignatura);
            Reserva nuevaReserva = new Reserva(asignatura.getFechaInicioCursada(),asignatura.getHoraInicio(),asignatura.getHoraFin(),asignatura);
            if(estaDisponible(asignatura.getHoraInicio(),asignatura.getHoraFin(),asignatura.get) && noSuperaCapacidad(asignatura.getCantidadInscriptos()))
                listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
            System.out.println("Reserva para asignatura " + asignatura.getNombre() + " realizada con exito");
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    public void agregaReservas(int codigoCurso, LocalDate fechaInicio, LocalTime horaInicio, LocalTime horaFin){
        try{
            CursoExtension curso = universidad.getCursoExtension(codigoCurso);
            Reserva nuevaReserva = new Reserva(fechaInicio,horaInicio,horaFin,curso);
            if(estaDisponible(horaInicio,horaFin,fechaInicio) && noSuperaCapacidad(curso.getCantidadInscriptos()))
                listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
            System.out.println("Reserva para curso " + curso.getDescripcion() + " realizada con exito");
        } catch(NoSuchElementException e){
            System.out.println((e.getMessage()));
        }
    }


    public void agregaReservas(int codigoEvento, String nombreOrganizacion, float costoAlquiler){
        try{
            Evento evento = universidad.getEvento(codigoEvento);
            Reserva nuevaReserva = new Reserva(evento.getFechaInicio(),evento.getHoraInicio(),evento.getHoraFin(),evento);
            if(estaDisponible(evento.getHoraInicio(),evento.getHoraFin(),evento.getFechaInicio()) && noSuperaCapacidad(evento.getCantidadInscriptos()))
                listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
            System.out.println("Reserva para evento " + " realizada con exito");
        }catch(NoSuchElementException e){
            System.out.println(e.getMessage());
        }
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
}
