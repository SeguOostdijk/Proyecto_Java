package reservas.logica;
import java.io.Serializable;
import java.util.*;

public class Universidad implements Serializable{
    private TreeSet<Aula> ListaAulas;
    private HashMap<String, Reservable> listaReservables;

    public Universidad( HashMap<String, Reservable> listaReservables) {
        ListaAulas=new TreeSet<>(new Comparator<Aula>() {
            @Override
            public int compare(Aula o1, Aula o2) {
                return o1.getNumero()-o2.getNumero();
            }
        });
        this.listaReservables = listaReservables;
    }

    public Aula getAula(int codigoAula) {
        for (Aula aula : ListaAulas) {
            if (aula.getNumero()==codigoAula) {
                return aula;
            }
        }
        throw new NoSuchElementException("No se encontro el aula con codigo " + codigoAula);
    }

    public void poneAula(Aula nuevaAula){
        ListaAulas.add(nuevaAula);
    }

    public Asignatura getAsignatura(int codAsignatura) {
        return (Asignatura) listaReservables.get(codAsignatura);
    }

    public void poneAsignatura(Asignatura nuevaAsignatura){
        listaReservables.put(nuevaAsignatura.getCodigoIdentificador(), nuevaAsignatura);
    }

    public CursoExtension getCursoExtension(int codCurso){
        return  (CursoExtension) listaReservables.get(codCurso);
    }

    public void poneCurso(CursoExtension nuevoCurso){
        listaReservables.put(nuevoCurso.getCodigoIdentificador(),nuevoCurso);
    }

    public Evento getEvento(int codEvento){
        return (Evento) listaReservables.get(codEvento);
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
            listaMA.add(new MontoPorAula(aula.montoRecaudado(),aula.getNumero()));
        }
        return listaMA;
    }
    public Montos getMontos(){
        int pisoActual;
        float sumTotal=0;
        float sumPiso;
        Montos m=new Montos();
        Iterator<Aula> it=ListaAulas.iterator();
        Aula a=it.next();
        while(it.hasNext()){
            sumPiso=0;
            pisoActual=a.getPiso();
            while(it.hasNext()&&a.getPiso()==pisoActual){
                m.agregaMontosAula(a.getNumero(),a.montoRecaudado());
                a=it.next();
                sumPiso+=a.montoRecaudado();
                sumTotal+=a.montoRecaudado();
            }
            m.agregaMontosPiso(sumPiso);
        }
        m.setMontoTotal(sumTotal);
        return m;
    }
    public ReporteAulasReserva getReporteReservas(){
        float sumaTotal=0;
        ReporteAulasReserva reporte=new ReporteAulasReserva();
        for (Aula aula : ListaAulas) {
            reporte.agregarAula(aula);
            sumaTotal+=aula.cantidadReservas();
        }
        if(!ListaAulas.isEmpty())
            reporte.setPromReservasAula(sumaTotal/ListaAulas.size());
        else
            reporte.setPromReservasAula(0);
        return  reporte;
    }




}

