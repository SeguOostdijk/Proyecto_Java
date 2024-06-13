package reservas.logica;

import reservas.persistencia.Persistencia;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public Reserva cancelaReserva(int codigoEntidad){
        Reserva reservaCancelada;
        if(listaReservas.containsKey(codigoEntidad)) {
            reservaCancelada = listaReservas.get(codigoEntidad);
            listaReservas.remove(codigoEntidad);
        }
        else
            throw new NoSuchElementException("La reserva que intenta eliminar no existe");
        return reservaCancelada;

    }

    public int cantidadReservas(){
        if(listaReservas.isEmpty())
            return 0;
        else
            return listaReservas.size();

    }

    public boolean hizoReserva(int codigoId){
        for (Reserva reserva : listaReservas.values()) {
            if(reserva.getCODIGO()==codigoId)
                return true;
        }
        return false;
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

    public void agregaReservas(String codigoAsignatura) {  //asignatura o evento interno
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

        Persistencia.serializar();

    }

    public void agregaReservas(String codigoCurso, LocalDate fechaInicio, LocalTime horaInicio, LocalTime horaFin){
        CursoExtension curso = Universidad.getInstance().getCursoExtension(codigoCurso);
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

    public void agregaReservasEventoInterno(String codigoEventoInterno){
        EventoInterno eventoInterno = universidad.getEventoInterno(codigoEventoInterno);
        LocalDate fechaInicio = eventoInterno.getFechaInicio();
        LocalTime horaInicio = eventoInterno.getHoraInicio();
        LocalTime horaFin = eventoInterno.getHoraFin();

        Reserva nuevaReserva = new Reserva(fechaInicio,horaInicio,horaFin,eventoInterno);
        if(estaDisponible(horaInicio,horaFin,fechaInicio) && noSuperaCapacidad(eventoInterno.getCantidadInscriptos()))
            listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
        else
            throw new NoSuchElementException("No se pudo realizar la reserva");
    }

    public void agregaReservasEventoExterno(String codigoEventoExterno, String nombreOrganizacion, float costoAlquiler){
        EventoExterno eventoExterno = universidad.getEventoExterno(codigoEventoExterno);
        LocalDate fechaInicio = eventoExterno.getFechaInicio();
        LocalTime horaInicio = eventoExterno.getHoraInicio();
        LocalTime horaFin = eventoExterno.getHoraFin();
        eventoExterno.setNombreOrganizacion(nombreOrganizacion);
        eventoExterno.setCostoAlquiler(costoAlquiler);

        Reserva nuevaReserva = new Reserva(fechaInicio,horaInicio,horaFin,eventoExterno);
        if(estaDisponible(horaInicio,horaFin,fechaInicio) && noSuperaCapacidad(eventoExterno.getCantidadInscriptos()))
            listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
        else
            throw new NoSuchElementException("No se pudo realizar la reserva");
    }

    public void horariosDisponibles(LocalDate fecha,LocalTime horaInicio,LocalTime horaFin){
        for (Reserva reserva : listaReservas.values()) {
            if(fecha==reserva.getFecha()){

            }
        }
    }
    public void agregaReservaXML(String codReservable,LocalDate fecha,LocalTime horaInicio,LocalTime horaFin,Universidad uni){
        Reservable tipoReservable= uni.getReservable(codReservable);
        if(tipoReservable!=null){
            Reserva nuevaReserva=new Reserva(fecha,horaInicio,horaFin,tipoReservable);
            if(estaDisponible(horaInicio,horaFin,fecha) && noSuperaCapacidad(tipoReservable.getCantidadInscriptos()))
                listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
        }
        else
            throw new NoSuchElementException("ERROR.Codigo de reservable inexistente");
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append( " \n Aula numero " + numeroAula +
                "\n Capacidad maxima=" + capacidadMaxima +
                "\n Cantidad de reservas="+listaReservas.size()+
                "\n Lista de Reservas:"+"\n");
        for (Reserva reserva : listaReservas.values()) {
            sb.append(reserva);
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Aula o) {
        return this.getNumero()-o.getNumero();
    }
}
