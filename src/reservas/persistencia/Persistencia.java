package reservas.persistencia;

import reservas.logica.Universidad;

import java.io.*;

public class Persistencia {

    // Método estático para serializar un objeto
    public static void serializar() {
        try {
            FileOutputStream fos = new FileOutputStream("universidad.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(Universidad.getInstance());
            oos.close();
        } catch (IOException e) {
            System.out.println("Exception during serialization: " + e);
        }
    }

    // Método estático para deserializar un objeto
    public static Universidad deserializar() {
        Universidad universidad = null;
        try {
            FileInputStream fis = new FileInputStream("universidad.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            universidad = (Universidad) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("Exception during deserialization: " + e);
        }
        return universidad;
    }
}