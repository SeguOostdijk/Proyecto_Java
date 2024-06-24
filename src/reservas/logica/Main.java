package reservas.logica;


import jdk.jfr.Description;
import reservas.persistencia.Persistencia;

import reservas.ui.MainFrame;

import java.io.File;
import java.io.IOException;

import javax.swing.*;

/**
 Esta clase inicia el programa
 @version 1.0
 @author Francisco Viera, Pablo Marco, Segundo Oostdijk, Bautista Nielsen
 @since 2024

 */
public class Main {
    /**
     Ejecuta el programa
     */
    public static void main(String[] args) {
        File Archivo=new File("universidad.dat");
        if(Archivo.exists()) {
            Persistencia.deserializarUniversidad();
        } else {
            try {
                CargaXML.CargaDatosXML();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        MainFrame mainFrame = new MainFrame();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
