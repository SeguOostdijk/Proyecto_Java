package reservas.logica;

public abstract class Reservable {
    private final String codigoIdentificador;
    private int cantidadInscriptos;

    public Reservable(String codigoIdentificador, int cantidadInscriptos) {
        this.codigoIdentificador = codigoIdentificador;
        this.cantidadInscriptos = cantidadInscriptos;
    }

    public String getCodigoIdentificador() {
        return codigoIdentificador;
    }

    public int getCantidadInscriptos() {
        return cantidadInscriptos;
    }

    public void setCantidadInscriptos(int cantidadInscriptos) {
        this.cantidadInscriptos = cantidadInscriptos;
    }

    @Override
    public String toString() {
        return "codigoIdentificador='" + codigoIdentificador + '\'' +
                ", cantidadInscriptos=" + cantidadInscriptos +
                '}';
    }
}
