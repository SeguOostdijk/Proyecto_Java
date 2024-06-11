package reservas.logica;
import java.io.Serializable;
import java.util.*;

public class Universidad implements Serializable {
    private TreeSet<Aula> ListaAulas;
    private HashMap<String, Reservable> listaReservables;

    public Universidad() {
        ListaAulas = new TreeSet<>();
        listaReservables = new HashMap<>();
    }


    public Aula getAula(int codigoAula) {
        for (Aula aula : ListaAulas) {
            if (aula.getNumero() == codigoAula) {
                return aula;
            }
        }
        throw new NoSuchElementException("No se encontro el aula con codigo " + codigoAula);
    }

    public void poneAula(Aula nuevaAula) {
        ListaAulas.add(nuevaAula);
    }

    public Asignatura getAsignatura(int codAsignatura) {
        return (Asignatura) listaReservables.get(codAsignatura);
    }

    public void poneAsignatura(Asignatura nuevaAsignatura) {
        listaReservables.put(nuevaAsignatura.getCodigoIdentificador(), nuevaAsignatura);
    }

    public CursoExtension getCursoExtension(int codCurso) {
        return (CursoExtension) listaReservables.get(codCurso);
    }

    public void poneCurso(CursoExtension nuevoCurso) {
        listaReservables.put(nuevoCurso.getCodigoIdentificador(), nuevoCurso);
    }

    public EventoInterno getEventoInterno(int codEventoInterno) {
        return (EventoInterno) listaReservables.get(codEventoInterno);
    }

    public void poneEventoInterno(EventoInterno nuevoEventoInterno) {
        listaReservables.put(nuevoEventoInterno.getCodigoIdentificador(), nuevoEventoInterno);
    }

    public EventoExterno getEventoExterno(int codEventoExterno) {
        return (EventoExterno) listaReservables.get(codEventoExterno);
    }

    public void poneEventoExterno(EventoExterno nuevoEventoExterno) {
        listaReservables.put(nuevoEventoExterno.getCodigoIdentificador(), nuevoEventoExterno);
    }

    public void cancelarReserva(Aula aula, Integer codReserva) {
        try {
            Aula elimAula = getAula(aula.getNumero());
            // elimAula.cancela reserva de la clase reserva
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
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
}

