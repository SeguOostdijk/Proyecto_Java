package reservas.logica;

import reservas.persistencia.UniversidadDTO;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Universidad implements Serializable {
    private static Universidad instance;
    private TreeSet<Aula> ListaAulas;
    private HashMap<String, Reservable> listaReservables;

    // Constructor privado para el patrón Singleton
    private Universidad() {
        ListaAulas = new TreeSet<>();
        listaReservables = new HashMap<>();
    }

    // Método para obtener la instancia única de Universidad
    public static Universidad getInstance() {
        if (instance == null) {
            instance = new Universidad();
        }
        return instance;
    }

    // Método para convertir Universidad a un DTO
    public UniversidadDTO toDTO() {
        UniversidadDTO dto = new UniversidadDTO();
        dto.setListaAulas(new TreeSet<>(ListaAulas));
        dto.setListaReservables(new HashMap<>(listaReservables));
        return dto;
    }

    // Método para actualizar Universidad desde un DTO
    public void fromDTO(UniversidadDTO dto) {
        ListaAulas = new TreeSet<>(dto.getListaAulas());
        listaReservables = new HashMap<>(dto.getListaReservables());
    }


    public HashMap<String, Reservable> getListaReservables() {
        return listaReservables;
    }

    public TreeSet<Aula> getListaAulas() {
        return ListaAulas;
    }

    public void setListaAulas(TreeSet<Aula> listaAulas) {
        ListaAulas = listaAulas;
    }

    public void setListaReservables(HashMap<String, Reservable> listaReservables) {
        this.listaReservables = listaReservables;
    }

    public Aula getAula(int codigoAula) {
        for (Aula aula : ListaAulas) {
            if (aula.getNumero() == codigoAula) {
                return aula;
            }
        }
        return null;
    }

    public void poneAula(Aula nuevaAula) {
        ListaAulas.add(nuevaAula);
    }

    public Asignatura getAsignatura(String codAsignatura) {
        return (Asignatura) listaReservables.get(codAsignatura);
    }

    public void poneAsignatura(Asignatura nuevaAsignatura) {
          listaReservables.put(nuevaAsignatura.getCodigoIdentificador(), nuevaAsignatura);
    }

    public CursoExtension getCursoExtension(String codCurso) {
        return (CursoExtension) listaReservables.get(codCurso);
    }

    public void poneCurso(CursoExtension nuevoCurso) {
        listaReservables.put(nuevoCurso.getCodigoIdentificador(), nuevoCurso);
    }

    public EventoInterno getEventoInterno(String codEventoInterno) {
        return (EventoInterno) listaReservables.get(codEventoInterno);
    }

    public void poneEventoInterno(EventoInterno nuevoEventoInterno) {
        listaReservables.put(nuevoEventoInterno.getCodigoIdentificador(), nuevoEventoInterno);
    }

    public EventoExterno getEventoExterno( String codEventoExterno) {
        return (EventoExterno) listaReservables.get(codEventoExterno);
    }

    public void poneEventoExterno(EventoExterno nuevoEventoExterno) {
        listaReservables.put(nuevoEventoExterno.getCodigoIdentificador(), nuevoEventoExterno);
    }

    public Reserva cancelarReserva(int numeroAula, Integer codReserva) {
            Aula elimAula = getAula(numeroAula);
            Reserva reservaCancelada;
            if(elimAula!=null)
              reservaCancelada= elimAula.cancelaReserva(codReserva);
            else
                throw new NoSuchElementException("El numero de aula ingresado no existe");
            return  reservaCancelada;
    }

    public void mostrarAulas(){
        System.out.println(ListaAulas);
    }


    public List<Aula> consultarAula(Integer numeroPiso) {
        Iterator<Aula> it = ListaAulas.iterator();
        ArrayList<Aula> aulasporpiso = new ArrayList<>();
        if (it.hasNext()) {
            Aula a = it.next();
            while (it.hasNext() && a.getPiso() < numeroPiso)
                a = it.next();
            if (it.hasNext())
                while (it.hasNext() && a.getPiso() == numeroPiso) {
                    aulasporpiso.add(a);
                    a = it.next();
                }
        } else
            throw new NoSuchElementException();
        return aulasporpiso;
    }

    public List<Aula> consultarAula(int codigoId) {
        ArrayList<Aula> aulasporcodigo = new ArrayList<>();
        for (Aula aula : ListaAulas) {
            if (aula.hizoReserva(codigoId))
                aulasporcodigo.add(aula);
        }
        if (aulasporcodigo.isEmpty())
            throw new NoSuchElementException();
        return aulasporcodigo;
    }

    public String listaEntidad(Class<? extends Reservable> tipoEntidad) {
        StringBuilder sb = new StringBuilder();
        for (Reservable reservable : listaReservables.values()) {
            if (tipoEntidad.isInstance(reservable))
                sb.append(reservable).append('\n');
        }
        return sb.toString();
    }

    public Montos getMontos() {
        int pisoActual;
        float sumTotal = 0;
        float sumPiso;
        Montos m = new Montos();
        if (!ListaAulas.isEmpty()) {
            Iterator<Aula> it = ListaAulas.iterator();
            Aula a = it.next();
            while (it.hasNext()) {
                sumPiso = 0;
                pisoActual = a.getPiso();
                while (it.hasNext() && a.getPiso() == pisoActual) {
                    m.agregaMontosAula(a.getNumero(), a.montoRecaudado());
                    a = it.next();
                    sumPiso += a.montoRecaudado();
                    sumTotal += a.montoRecaudado();
                }
                m.agregaMontosPiso(sumPiso);
            }
            m.setMontoTotal(sumTotal);
        } else throw new IllegalStateException("ERROR AL GENERAR REPORTE. No hay aulas registradas en el sistema.");
        return m;
    }

    public ReporteAulasReserva getReporteReservas() {
        float sumaTotal = 0;
        ReporteAulasReserva reporte = new ReporteAulasReserva();
        if (!ListaAulas.isEmpty()) {

            for (Aula aula : ListaAulas) {
                reporte.agregarAula(aula);
                sumaTotal += aula.cantidadReservas();
            }
            if (!ListaAulas.isEmpty())
                reporte.setPromReservasAula(sumaTotal / ListaAulas.size());
            else
                reporte.setPromReservasAula(0);
        } else throw new IllegalStateException("ERROR AL GENERAR REPORTE. No hay aulas registradas en el sistema.");
        return reporte;
    }

    public List<Aula> aulasDisponibles(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, int cantidadPersonas){
        List<Aula> listaAulasDisponibles = new ArrayList<>();
        for (Aula aula : ListaAulas) {
            if(aula.estaDisponible(horaInicio,horaFin,fecha) && aula.noSuperaCapacidad(cantidadPersonas))
                listaAulasDisponibles.add(aula);
        }
        return listaAulasDisponibles;
    }

    public Reservable getReservable(String codReservable) {
       return listaReservables.get(codReservable);
    }
}

