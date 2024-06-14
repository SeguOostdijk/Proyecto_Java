package reservas.persistencia;

import reservas.logica.Universidad;

import java.io.*;

public class Persistencia {

    private static final String FILE_NAME = "universidad.dat";

    public static void serializarUniversidad() {
        Universidad universidad = Universidad.getInstance();
        UniversidadDTO dto = universidad.toDTO();

        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(dto);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void deserializarUniversidad() {
        Universidad universidad = Universidad.getInstance();

        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            UniversidadDTO dto = (UniversidadDTO) in.readObject();
            universidad.fromDTO(dto);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}