package reservas.logica;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class Aula {
    int numeroAula;
    int capacidadMaxima;
    HashMap <Integer,Reserva> listaReservas;
    Universidad universidad;

    public Aula(int capacidadMaxima, Integer numeroAula) {
        this.capacidadMaxima = capacidadMaxima;
        this.numeroAula = numeroAula;
        listaReservas = new HashMap();
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

    public void agregaReservas(String codigoAsignatura) {
        try {
            Asignatura asignatura = universidad.getAsignatura(codigoAsignatura);
            Reserva nuevaReserva = new Reserva(Asignatura.getFechaInicioCursada(),asignatura.getHoraInicio(),asignatura.getHoraFin(),asignatura);
            listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
            System.out.println("Reserva para asignatura " + asignatura.getNombre() + " realizada con exito");
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    public void agregaReservas(String codigoCurso,String fechaInicio,String horaInicio,String horaFin){
        try{
            CursoExtension curso = universidad.getCursoExtension(codigoCurso);
            Reserva nuevaReserva = new Reserva(fechaInicio,horaInicio,horaFin,curso);
            listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
            System.out.println("Reserva para curso " + curso.getDescripcion() + " realizada con exito");
        } catch(NoSuchElementException e){
            System.out.println((e.getMessage()));
        }
    }

    public void agregaReservas(String codigoEvento){
        try{
            Evento evento = universidad.getEvento(codigoEvento);
            Reserva nuevaReserva = new Reserva(evento.getFechaInicio(),evento.getHoraInicio(),evento.getHoraFin(),evento);
            listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
            System.out.println("Reserva para evento " + " realizada con exito");
        } catch(NoSuchElementException e){
            System.out.println(e.getMessage());
        }
    }

    public void agregaReservas(String codigoEvento, String nombreOrganizacion, float costoAlquiler){
        try{
            Evento evento = universidad.getEvento(codigoEvento);
            Reserva nuevaReserva = new Reserva(evento.getFechaInicio(),evento.getHoraInicio(),evento.getHoraFin(),evento);
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
