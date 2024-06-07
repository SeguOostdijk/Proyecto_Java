package reservas.logica;
import java.io.Serializable;
import java.util.*;

public class Universidad implements Serializable {
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


    public List<Aula> consultarAula(Integer numeroPiso) {
        Iterator<Aula> it= ListaAulas.iterator();
        ArrayList<Aula> aulasporpiso = new ArrayList<>();
        Aula a=ListaAulas.getFirst();
        while(it.hasNext() && a.getPiso()<numeroPiso)
            a=it.next();
        if(it.hasNext())
          while(it.hasNext()&& a.getPiso()==numeroPiso) {
              aulasporpiso.add(a);
              a = it.next();
          }
        else
            throw new NoSuchElementException();
        return aulasporpiso;
    }
    public List<Aula> consultarAula(String codigoId) {
        ArrayList<Aula> aulasporcodigo = new ArrayList<>();
        for (Aula aula : ListaAulas) {
            if (aula.hizoReserva(CodigoId))
                aulasporcodigo.add(aula);
        }
        if (aulasporcodigo.isEmpty())
            throw new NoSuchElementException();
        return aulasporcodigo;
    }
    public String listaEntidad(Class<? extends Reservable> tipoEntidad){
        StringBuilder sb=new StringBuilder();
        for (Reservable reservable : listaReservables.values()) {
            if (tipoEntidad.isInstance(reservable))
                sb.append(reservable).append('\n');
        }
        return sb.toString();
    }
    public List<MontoPorAula> getMontoPorAula(){
        ArrayList<MontoPorAula> listaMA=new ArrayList<>();
        for (Aula aula : ListaAulas) {
            listaMA.add(new MontoPorAula(aula.getmonto(),aula.getNumero()));
        }
        return listaMA;
    }
    public Montos getMontos(){
        int pisoActual;
        Montos m=new Montos();
        Iterator<Aula> it=ListaAulas.iterator();
        while(it.hasNext()){
            Aula a=it.next();
            m.getMontosAula().add(a.getMonto());
            pisoActual=a.getPiso();
            while(it.hasNext()&&a.getPiso()==pisoActual){
               m.getMontosPiso().add(getMonto)

            }
        }




    }




}

