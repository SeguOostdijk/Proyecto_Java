package reservas.logica;

import reservas.persistencia.Persistencia;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Aula implements Serializable,Comparable<Aula>{
    private int numeroAula;
    private int capacidadMaxima;
    private HashMap <Integer,Reserva> listaReservas;


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
            else if (reserva.getReservable() instanceof CursoExtension) {
                sumadorCostos+=((CursoExtension)reserva.getReservable()).getCostoTotal();

            }
        }
        return sumadorCostos;
    }

    public boolean noSuperaCapacidad(int cantidadPersonas){
        return cantidadPersonas<=capacidadMaxima;
    }

    public boolean estaDisponible(LocalTime horaInicio,LocalTime horaFin, LocalDate dia){
        for (Reserva reserva : listaReservas.values()) {
            if(reserva.getFecha().isEqual(dia))
                if(horaFin.isBefore(reserva.getHoraFin()) && horaInicio.isAfter(reserva.getHoraInicio()) || horaInicio==reserva.getHoraInicio())
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

       Persistencia.serializarUniversidad();
        return reservaCancelada;
    }

    public int cantidadReservas(){
        if(listaReservas.isEmpty())
            return 0;
        else
            return listaReservas.size();

    }

    public boolean hizoReserva(String codigoId) {
        boolean bandera = false;
        Iterator<Reserva> iterator = listaReservas.values().iterator();

        while (!bandera && iterator.hasNext()) {
            Reserva reserva = iterator.next();
            Reservable reservable = reserva.getReservable();
            if (reservable.getCodigoIdentificador().equals(codigoId)) {
                bandera = true;
            }
        }

        return bandera;
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
        Asignatura asignatura = Universidad.getInstance().getAsignatura(codigoAsignatura);
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

        Persistencia.serializarUniversidad();
    }

    public void agregaReservas(String codigoCurso, LocalDate fechaInicio, LocalTime horaInicio, LocalTime horaFin) throws AulaOcupadaException {
        CursoExtension curso = Universidad.getInstance().getCursoExtension(codigoCurso);
        LocalDate fechaActual = fechaInicio;
        int clasesReservadas = 0;
        int cantidadClases = curso.getCantidadClases();

        while(clasesReservadas < cantidadClases) {
            Reserva nuevaReserva = new Reserva(fechaActual, horaInicio, horaFin, curso);
            if (estaDisponible(horaInicio, horaFin, fechaActual) && noSuperaCapacidad(curso.getCantidadInscriptos()))
                listaReservas.put(nuevaReserva.getCODIGO(), nuevaReserva);
            else
                throw new AulaOcupadaException("No se pudo realizar la reserva para el dia" + fechaActual);
            clasesReservadas++;
            fechaActual = fechaActual.plusWeeks(1);
        }
        Persistencia.serializarUniversidad();
    }

    public void agregaReservasEventoInterno(String codigoEventoInterno){
        EventoInterno eventoInterno = Universidad.getInstance().getEventoInterno(codigoEventoInterno);
        LocalDate fechaInicio = eventoInterno.getFechaInicio();
        LocalTime horaInicio = eventoInterno.getHoraInicio();
        LocalTime horaFin = eventoInterno.getHoraFin();

        Reserva nuevaReserva = new Reserva(fechaInicio,horaInicio,horaFin,eventoInterno);
        if(estaDisponible(horaInicio,horaFin,fechaInicio) && noSuperaCapacidad(eventoInterno.getCantidadInscriptos()))
            listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
        else
            throw new NoSuchElementException("No se pudo realizar la reserva");

        Persistencia.serializarUniversidad();
    }

    public void agregaReservasEventoExterno(String codigoEventoExterno, String nombreOrganizacion, float costoAlquiler){
        EventoExterno eventoExterno = Universidad.getInstance().getEventoExterno(codigoEventoExterno);
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

      Persistencia.serializarUniversidad();
    }

    public void horariosDisponibles(LocalDate fecha,LocalTime horaInicio,LocalTime horaFin){
        for (Reserva reserva : listaReservas.values()) {
            if(fecha==reserva.getFecha()){

            }
        }
    }
    public void agregaReservaXML(String codReservable,LocalDate fecha,LocalTime horaInicio,LocalTime horaFin){
        Reservable tipoReservable= Universidad.getInstance().getReservable(codReservable);
        if(tipoReservable!=null){
            Reserva nuevaReserva=new Reserva(fecha,horaInicio,horaFin,tipoReservable);
            if(estaDisponible(horaInicio,horaFin,fecha) && noSuperaCapacidad(tipoReservable.getCantidadInscriptos()))
                listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
        }
        else
            throw new NoSuchElementException("ERROR.Codigo de reservable inexistente");

       Persistencia.serializarUniversidad();
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
