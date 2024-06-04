package reservas.logica;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class Universidad {
    private TreeSet<Aula> ListaAulas;
    private HashMap<String, Asignatura> ListaAsignaturas;
    private HashMap<String, CursoExtension> ListaCursos;
    private HashMap<String, Evento> ListaEventos;

    public Universidad(TreeSet<Aula> listaAulas, HashMap<String, Asignatura> listaAsignaturas, HashMap<String, CursoExtension> listaCursos, HashMap<String, Evento> listaEventos) {
        ListaAulas = listaAulas;
        ListaAsignaturas = listaAsignaturas;
        ListaCursos = listaCursos;
        ListaEventos = listaEventos;
    }

    public Aula getAula(Integer codigoAula) {
        for (Aula aula : ListaAulas) {
            if (aula.getNumero().equals(codigoAula)) {
                return aula;
            }
        }
        throw new NoSuchElementException("No se encontro el aula con codogio " + codigoAula);
    }

    public void poneAula(Aula nuevaAula){
       ListaAulas.add(nuevaAula);
    }

    public Asignatura getAsignatura(String codAsignatura) {
        Asignatura asignatura = ListaAsignaturas.get(codAsignatura);
        if (asignatura == null) {
            throw new NoSuchElementException("Asignatura con código " + codAsignatura + " no encontrada.");
        }
        return asignatura;
    }

    public void poneAsignatura(Asignatura nuevaAsignatura){
        ListaAsignaturas.put(nuevaAsignatura.getCodigoIdentificador(), nuevaAsignatura);
    }

    public CursoExtension getCursoExtension(String codCurso){
        CursoExtension curso = ListaCursos.get(codCurso);
        if(curso == null){
            throw new NoSuchElementException("Curso con codigo " + codCurso + "no encontrada");
        }
        return curso;
    }

    public void poneCurso(CursoExtension nuevoCurso){
        ListaCursos.put(nuevoCurso.getCodigoIdentificador(),nuevoCurso);
    }

    public Evento getEvento(String codEvento){
        Evento evento = ListaEventos.get(codEvento);
        if(evento == null){
            throw new NoSuchElementException("Evento con codigo " + codEvento + "no encontrada");
        }
        return evento;
    }

    public void poneEvento(Evento nuevoEvento){
        ListaEventos.put(nuevoEvento.getCodigoIdentificador(),nuevoEvento);
    }

    public void cancelarReserva(Aula aula, Integer codReserva){
        try{
            Aula elimAula = getAula(aula.getNumero());
            // elimAula.cancela reserva de la clase reserva
        } catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        }
    }

    public void consultarAula(Integer NumeroPiso){
        Iterator<Aula> RecorreLista = ListaAulas.iterator();

        while(RecorreLista.hasNext()){
            Aula aula = RecorreLista.next();
            if(aula.getNumero()/100 == NumeroPiso/100){
                for(Aula aulaEnPiso : ListaAulas){
                    System.out.println(aulaEnPiso);
                }
            }
        }
    }

}
