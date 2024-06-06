package reservas.logica;

public class MontoPorAula {
    float monto;
    String numeroAula;

    public MontoPorAula(float monto, String numeroAula) {
        this.monto = monto;
        this.numeroAula = numeroAula;
    }

    public String getNumeroAula() {
        return numeroAula;
    }

    public float getMonto() {
        return monto;
    }
}
