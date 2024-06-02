package reservas.logica;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class Universidad {
    private TreeSet<Integer, Aula> ListaAulas = new TreeSet();
    private HashMap<String, Asignatura> ListaAsignaturas = new HashMap();
    private HashMap<String, CursoExtension> ListaCursos = new HashMap();
    private HashMap<String, Evento> ListaEventos = new HashMap();

    public Universidad(TreeSet<Integer, Aula> listaAulas, HashMap<String, Evento> listaEventos, HashMap<String, CursoExtension> listaCursos, HashMap<String, Asignatura> listaAsignaturas) {
        ListaAulas = listaAulas;
        ListaEventos = listaEventos;
        ListaCursos = listaCursos;
        ListaAsignaturas = listaAsignaturas;
    }

    public Aula getAula(String codigoAula) {
        for (Aula aula : ListaAulas) {
            if (aula.getCodigo().equals(codigoAula)) {
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
}
