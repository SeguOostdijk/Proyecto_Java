package reservas.logica;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;
/**
 Define atributos y metodos necesarios para almacenar los montos generados por la universidad
 */
public class Montos implements Serializable {
    private ArrayList<Float> montosPiso;
    private TreeMap<Integer,Float> montosAula;
    private double montoTotal;
    public  Montos(){
        montosAula=new TreeMap<>();
        montosPiso=new ArrayList<>();
        montoTotal=0;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void sumaPiso(int piso,float monto){
        montosPiso.add(piso,monto);
    }

    public void agregaMontosAula(int clave,float monto){
        montosAula.put(clave,monto);
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        DecimalFormat df = new DecimalFormat("#,###");
        df.setMaximumFractionDigits(0);
        sb.append("Montos por Aula:\n");
        for (Map.Entry<Integer, Float> integerFloatEntry : montosAula.entrySet())
            sb.append("Aula ").append(integerFloatEntry.getKey()).append(": Monto recaudado=").append(df.format(integerFloatEntry.getValue())).append("\n");
        sb.append("\nMontos por piso:\n");
        sb.append("Planta baja:" + " Monto recaudado=").append(df.format(montosPiso.getFirst())).append("\n");
        for (int i = 1; i < montosPiso.size(); i++)
            sb.append("Piso ").append(i).append(": Monto recaudado=").append(df.format(montosPiso.get(i))).append("\n");
        sb.append("\nMonto total de la institucion=").append(df.format(montoTotal));
        return sb.toString();
    }

}
