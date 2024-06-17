package reservas.logica;


import reservas.persistencia.Persistencia;

import reservas.ui.MainFrame;

import java.io.File;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        File Archivo=new File("universidad.dat");
        if(Archivo.exists()) {
            Persistencia.deserializarUniversidad();
        } else
            CargaXML.CargaDatosXML();
        MainFrame mainFrame = new MainFrame();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
