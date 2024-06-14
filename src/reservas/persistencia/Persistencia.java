package reservas.persistencia;

import reservas.logica.Universidad;

import java.io.*;

import static reservas.logica.Universidad.getInstance;

public class Persistencia {

    public static UniversidadDTO UniversidadAUniversidadDTO(Universidad universidad){
        UniversidadDTO uni = new UniversidadDTO();
        uni.setListaAulas(universidad.getListaAulas());
        uni.setListaReservables(uni.getListaReservables());
        return uni;
    }

    public static void UniversidadDTOAUniversidad(UniversidadDTO universidadDTO){
        Universidad.getInstance().setListaAulas(universidadDTO.getListaAulas());
        Universidad.getInstance().setListaReservables(universidadDTO.getListaReservables());
    }

    // Método estático para serializar un objeto
    public static void serializar() {
        try {
            UniversidadDTO uniDTO = Persistencia.UniversidadAUniversidadDTO(Universidad.getInstance());
            FileOutputStream fos = new FileOutputStream("universidad.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(getInstance());
            oos.close();
        } catch (IOException e) {
            System.out.println("Exception during serialization: " + e);
        }
    }

    // Método estático para deserializar un objeto
    public static Serializable deserializar() {
        Serializable ser = null;
        try {
            FileInputStream fis = new FileInputStream("universidad.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ser = (UniversidadDTO) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("Exception during deserialization: " + e);
        }
        return ser;
    }
}