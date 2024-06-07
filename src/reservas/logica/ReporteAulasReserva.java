package reservas.logica;

import java.util.Comparator;
import java.util.TreeSet;

public class ReporteAulasReserva {
    private TreeSet<Aula> listaAulasReservas;
    private float promReservasAula;
    public ReporteAulasReserva(){
        listaAulasReservas=new TreeSet<>(new Comparator<Aula>() {
            @Override
            public int compare(Aula o1, Aula o2) {
                return o1.cantidadReservas()-o2.cantidadReservas();
            }
        });
    }

    public TreeSet<Aula> getListaAulasReservas() {
        return listaAulasReservas;
    }


    public float getPromReservasAula() {
        return promReservasAula;
    }

    public void setPromReservasAula(float promReservasAula) {
        this.promReservasAula = promReservasAula;
    }
    public void agregarAula(Aula a){
        listaAulasReservas.add(a);
    }

    @Override
    public String toString() {  //Para mostrar el reporte directamente invocando a la funcion
        StringBuilder sb = new StringBuilder();
        sb.append("Reporte de Aulas ordenadas por cantidad de reservas:\n");
        for (Aula aula : listaAulasReservas) {
            sb.append(aula).append('\n');
        }
        sb.append("Promedio de reservas por aula: ").append(promReservasAula);
        return sb.toString();
    }
}
