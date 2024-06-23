package reservas.logica;


import reservas.persistencia.Persistencia;

import reservas.ui.MainFrame;

import java.io.File;
import java.io.IOException;

import javax.swing.*;


public class Main {
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
