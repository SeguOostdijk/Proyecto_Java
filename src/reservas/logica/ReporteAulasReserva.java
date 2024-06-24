package reservas.logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
/**
 * Define metodos y atributos necesarios para generar un reporte de las reservas de cada aula y el promedio de reservas por aula
 */
public class ReporteAulasReserva implements Serializable {
    private ArrayList<Aula> listaAulasReservas;
    private float promReservasAula;

    public ReporteAulasReserva() {
        listaAulasReservas = new ArrayList<>();
    }

    public ArrayList<Aula> getListaAulasReservas() {
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

    /** Para mostrar el reporte directamente invocando a la funcion */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reporte de Aulas ordenadas por cantidad de reservas:\n");
        listaAulasReservas.sort(Comparator.comparingInt(Aula::cantidadReservas).reversed());
        for (Aula aula : listaAulasReservas) {
            sb.append(aula).append('\n');
        }
        promReservasAula= (float) (Math.round(promReservasAula * 100d)/100d);
        sb.append("Promedio de reservas por aula: ").append(promReservasAula);
        return sb.toString();
    }
}
