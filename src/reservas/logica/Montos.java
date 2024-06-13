package reservas.logica;

import java.util.*;

public class Montos {
    private ArrayList<Float> montosPiso;
    private TreeMap<Integer,Float> montosAula;
    private float montoTotal;
    public  Montos(){
        montosAula=new TreeMap<>();
        montosPiso=new ArrayList<>();
        montoTotal=0;
    }

    public float getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void agregaMontosPiso(float monto) {
        montosPiso.add(monto);
    }
    public void agregaMontosAula(int clave,float monto){
        montosAula.put(clave,monto);
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("Montos por Aula:\n");
        for (Map.Entry<Integer, Float> integerFloatEntry : montosAula.entrySet())
            sb.append("Aula "+integerFloatEntry.getKey()+": Monto recaudado="+integerFloatEntry.getValue()+"\n");
        sb.append("\nMontos por piso:\n");
        sb.append("Planta baja:"+" Monto recaudado="+montosPiso.get(0)+"\n");
        for (int i = 1; i < montosPiso.size(); i++)
            sb.append("Piso "+i+": Monto recaudado="+montosPiso.get(i)+"\n");
        sb.append("\nMonto total de la institucion="+montoTotal);
        return sb.toString();
    }

}
