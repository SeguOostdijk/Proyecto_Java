package reservas.logica;

import java.io.Serializable;

public abstract class Reservable implements Serializable {
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
        return "codigo identificador='" + codigoIdentificador + '\'' +
                ", cantidad de inscriptos=" + cantidadInscriptos +
                '}';
    }
}
