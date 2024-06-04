package reservas.logica;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class Universidad {
    private TreeSet<Aula> ListaAulas;
    private HashMap<String, Reservable> listaReservables;

    public Universidad(TreeSet<Aula> listaAulas, HashMap<String, Reservable> listaReservables) {
        ListaAulas = listaAulas;
        this.listaReservables = listaReservables;
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
        Asignatura asignatura = (Asignatura) listaReservables.get(codAsignatura);
        return asignatura;
    }

    public void poneAsignatura(Asignatura nuevaAsignatura){
        listaReservables.put(nuevaAsignatura.getCodigoIdentificador(), nuevaAsignatura);
    }

    public CursoExtension getCursoExtension(String codCurso){
        CursoExtension curso =(CursoExtension) listaReservables.get(codCurso);
        return curso;
    }

    public void poneCurso(CursoExtension nuevoCurso){
        listaReservables.put(nuevoCurso.getCodigoIdentificador(),nuevoCurso);
    }

    public Evento getEvento(String codEvento){
        Evento evento = (Evento) listaReservables.get(codEvento);
        return evento;
    }

    public void poneEvento(Evento nuevoEvento){
        listaReservables.put(nuevoEvento.getCodigoIdentificador(),nuevoEvento);
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
