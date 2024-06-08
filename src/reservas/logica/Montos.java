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
        for (Map.Entry<Integer, Float> integerFloatEntry : montosAula.entrySet())
            sb.append("aula: "+integerFloatEntry.getKey()+" monto recaudado: "+integerFloatEntry.getValue()+"/n");
        for (int i = 0; i < montosPiso.size(); i++)
            sb.append("Piso:"+i+" monto recaudado:"+montosPiso.get(i)+"/n");
        sb.append("Monto total de la institucion:"+montoTotal);
        return sb.toString();
    }

}
