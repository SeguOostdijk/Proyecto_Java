package reservas.logica;

import java.io.Serializable;
import java.util.Objects;
/**
 * Es una clase abstracta que define atributos y metodos comunes a todos los tipos de reserva (asignatura,curso,eventos)
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservable that = (Reservable) o;
        return Objects.equals(codigoIdentificador, that.codigoIdentificador);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigoIdentificador);
    }
}
