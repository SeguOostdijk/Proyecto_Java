package reservas.persistencia;

import reservas.logica.Aula;
import reservas.logica.Reservable;


import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeSet;
/** Patron de diseño DTO*/
public class UniversidadDTO implements Serializable {
    private TreeSet<Aula> ListaAulas;
    private HashMap<String, Reservable> listaReservables;

    public HashMap<String, Reservable> getListaReservables() {
        return listaReservables;
    }

    public void setListaReservables(HashMap<String, Reservable> listaReservables) {
        this.listaReservables = listaReservables;
    }

    public TreeSet<Aula> getListaAulas() {
        return ListaAulas;
    }

    public void setListaAulas(TreeSet<Aula> listaAulas) {
        ListaAulas = listaAulas;
    }
}
