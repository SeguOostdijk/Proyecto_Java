package reservas.logica;

import reservas.persistencia.Persistencia;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class Aula implements Serializable,Comparable<Aula>{
    private int numeroAula;
    private int capacidadMaxima;
    private TreeMap<Integer,Reserva> listaReservas;


    public Aula(int capacidadMaxima, Integer numeroAula) {
        this.capacidadMaxima = capacidadMaxima;
        this.numeroAula = numeroAula;
        listaReservas = new TreeMap<>();
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

    public boolean estaDisponible(LocalTime horaInicio, LocalTime horaFin, LocalDate fecha) {
        for (Reserva reserva : listaReservas.values()) {
            if (reserva.getFecha().isEqual(fecha)) {
                // Verificar si hay superposición
                if (horaInicio.isBefore(reserva.getHoraFin()) && horaFin.isAfter(reserva.getHoraInicio())) {
                    return false; // Hay superposición
                }
            }
        }
        return true; // No hay superposición
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

    public void agregaReservas(String codigoAsignatura,LocalDate fecha) throws AulaOcupadaException {  //asignatura o evento interno
        Asignatura asignatura = Universidad.getInstance().getAsignatura(codigoAsignatura);
        LocalDate fechaFin = Asignatura.getFechaFinCursada();
        LocalDate fechaActual = fecha;
        while(!fechaActual.isAfter(fechaFin)) {
            Reserva nuevaReserva = new Reserva(fechaActual, asignatura.getHoraInicio(), asignatura.getHoraFin(), asignatura);
            if (estaDisponible(asignatura.getHoraInicio(), asignatura.getHoraFin(), fechaActual))
                listaReservas.put(nuevaReserva.getCODIGO(), nuevaReserva);
            else
                throw new AulaOcupadaException("No se pudo realizar la reserva para el dia:" + fechaActual);
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
            if (estaDisponible(horaInicio, horaFin, fechaActual))
                listaReservas.put(nuevaReserva.getCODIGO(), nuevaReserva);
            else
                throw new AulaOcupadaException("No se pudo realizar la reserva para el dia" + fechaActual);
            clasesReservadas++;
            fechaActual = fechaActual.plusWeeks(1);
        }
        Persistencia.serializarUniversidad();
    }

    public void agregaReservasEventoInterno(String codigoEventoInterno) throws AulaOcupadaException {
        EventoInterno eventoInterno = Universidad.getInstance().getEventoInterno(codigoEventoInterno);
        LocalDate fecha = eventoInterno.getFechaInicio();
        LocalTime horaInicio = eventoInterno.getHoraInicio();
        LocalTime horaFin = eventoInterno.getHoraFin();

        Reserva nuevaReserva = new Reserva(fecha,horaInicio,horaFin,eventoInterno);
        if(estaDisponible(horaInicio,horaFin,fecha) && noSuperaCapacidad(eventoInterno.getCantidadInscriptos()))
            listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
        else
            throw new AulaOcupadaException("No se pudo realizar la reserva para el dia:" + fecha);

        Persistencia.serializarUniversidad();
    }

    public void agregaReservasEventoInternoNuevo(String codigo, int cantidadInscriptos, LocalTime horaInicio, LocalTime horaFin, String descripcion, LocalDate fecha) throws AulaOcupadaException {
        EventoInterno eventoInterno = new EventoInterno(codigo,cantidadInscriptos,horaInicio,horaFin,descripcion,fecha);
        Reserva nuevaReserva = new Reserva(fecha,horaInicio,horaFin,eventoInterno);

        if(estaDisponible(horaInicio,horaFin,fecha) && noSuperaCapacidad(cantidadInscriptos))
            listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
        else
            throw new AulaOcupadaException("No se pudo realizar la reserva para el dia:" + fecha);

        Persistencia.serializarUniversidad();
    }

    public void agregaReservasEventoExterno(String codigoEventoExterno, String nombreOrganizacion, float costoAlquiler) throws AulaOcupadaException {
        EventoExterno eventoExterno = Universidad.getInstance().getEventoExterno(codigoEventoExterno);
        LocalDate fechaInicio = eventoExterno.getFechaInicio();
        LocalTime horaInicio = eventoExterno.getHoraInicio();
        LocalTime horaFin = eventoExterno.getHoraFin();
        eventoExterno.setNombreOrganizacion(nombreOrganizacion);
        eventoExterno.setCostoAlquiler(costoAlquiler);
        Reserva nuevaReserva = new Reserva(fechaInicio,horaInicio,horaFin,eventoExterno);

        if(estaDisponible(horaInicio,horaFin,fechaInicio))
            listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
        else
            throw new AulaOcupadaException("No se pudo realizar la reserva para el dia:" + fechaInicio);

      Persistencia.serializarUniversidad();
    }

    public void agregaReservasEventoExternoNuevo(LocalDate fechaInicio,LocalTime horaInicio,LocalTime horaFin,String codigo,String nombreOrganizacion,float costoAlquiler,int cantidadInscriptos,String descripcion) throws AulaOcupadaException {
        EventoExterno eventoExterno = new EventoExterno(codigo,cantidadInscriptos,horaInicio,horaFin,descripcion,fechaInicio,costoAlquiler,nombreOrganizacion);
        Reserva nuevaReserva = new Reserva(fechaInicio,horaInicio,horaFin,eventoExterno);

        if(estaDisponible(horaInicio,horaFin,fechaInicio))
            listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
        else
            throw new AulaOcupadaException("No se pudo realizar la reserva para el dia:" + fechaInicio);

        Persistencia.serializarUniversidad();
    }

    public void agregaReservaXML(String codReservable,LocalDate fecha,LocalTime horaInicio,LocalTime horaFin) throws AulaOcupadaException {
        Reservable tipoReservable= Universidad.getInstance().getReservable(codReservable);
        if(tipoReservable!=null) {

            if (estaDisponible(horaInicio, horaFin, fecha) && noSuperaCapacidad(tipoReservable.getCantidadInscriptos())) {
                if(tipoReservable instanceof CursoExtension)
                    agregaReservas(codReservable,fecha,horaInicio,horaFin);
                else if (tipoReservable instanceof Asignatura) {
                    agregaReservas(codReservable,fecha);
                }
                else{
                    Reserva nuevaReserva = new Reserva(fecha, horaInicio, horaFin, tipoReservable);
                    listaReservas.put(nuevaReserva.getCODIGO(),nuevaReserva);
                }
            }
        }
        else
            throw new NoSuchElementException("ERROR.Codigo de reservable inexistente");
       Persistencia.serializarUniversidad();
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(" \n Aula numero ")
                .append(numeroAula)
                .append("\n Capacidad maxima=")
                .append(capacidadMaxima)
                .append("\n Cantidad de reservas=")
                .append(listaReservas.size())
                .append("\n Lista de Reservas:").append("\n");
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
