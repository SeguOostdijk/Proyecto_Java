package reservas.logica;

import java.util.ArrayList;
import java.util.TreeSet;

public class Montos {
    private ArrayList<Float> montosPiso;
    private TreeSet<Float> montosAula:
    private float montoTotal;
    public  Montos(){
        montosAula=new TreeSet<>();
        montosPiso=new ArrayList<>();
        montoTotal=0;
    }

    public float getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }

    public ArrayList<Float> getMontosPiso() {
        return montosPiso;
    }

    public TreeSet<Float> getMontosAula() {
        return montosAula;
    }
}
