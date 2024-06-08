package reservas.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.TreeSet;
import reservas.logica.*;
import java.util.Date;


public class MainFrame extends JFrame {
    private Universidad universidad;

    public MainFrame() {
        setTitle("Sistema de Gestión de Reservas de Aulas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        universidad = new Universidad(new TreeSet<>(), new HashMap<>());
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel();

        JButton cargarDatosButton = new JButton("Cargar Datos");
        JButton listarAulasButton = new JButton("Listar Aulas");
        JButton reservarAulaButton = new JButton("Reservar Aula");
        JButton cancelarReservaButton = new JButton("Cancelar Reserva");

        panel.add(cargarDatosButton);
        panel.add(listarAulasButton);
        panel.add(reservarAulaButton);
        panel.add(cancelarReservaButton);
        add(panel, BorderLayout.CENTER);

        cargarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // implementar cargar datos
                cargarDatos();
            }
        });

        listarAulasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // implementar listar aulas
                listarAulas();
            }
        });

        reservarAulaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // implementar reservar aula
                reservarAula();
            }
        });

        cancelarReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // implementar cancelar reserva
                cancelarReserva();
            }
        });
    }

    private void cargarDatos() {
        try {
            // Abre un JFileChooser para que el usuario seleccione un archivo
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);

            // Si el usuario elige un archivo
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                // Configura el analizador de documentos XML
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);
                doc.getDocumentElement().normalize();

                // Obtiene la lista de elementos 'aula' del documento XML
                NodeList aulasList = doc.getElementsByTagName("aula");

                // Itera sobre cada elemento 'aula'
                for (int i = 0; i < aulasList.getLength(); i++) {
                    Node node = aulasList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        // Obtiene el número y la capacidad del aula
                        int numero = Integer.parseInt(element.getElementsByTagName("numero").item(0).getTextContent());
                        int capacidad = Integer.parseInt(element.getElementsByTagName("capacidad").item(0).getTextContent());

                        // Crea un objeto Aula con los datos obtenidos y lo agrega a la universidad
                        Aula aula = new Aula(capacidad, numero);
                        universidad.poneAula(aula);
                    }
                }

                // Repite el proceso para elementos 'asignatura'
                NodeList asignaturasList = doc.getElementsByTagName("asignatura");
                for (int i = 0; i < asignaturasList.getLength(); i++) {
                    Node node = asignaturasList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        // Obtiene los datos de la asignatura
                        String codigo = element.getElementsByTagName("codigo").item(0).getTextContent();
                        String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                        String dia = element.getElementsByTagName("dia").item(0).getTextContent();
                        String inicio = element.getElementsByTagName("inicio").item(0).getTextContent();
                        String fin = element.getElementsByTagName("fin").item(0).getTextContent();
                        int inscriptos = Integer.parseInt(element.getElementsByTagName("inscriptos").item(0).getTextContent());

                        // Crea un objeto Asignatura con los datos obtenidos y lo agrega a la universidad
                        Asignatura asignatura = new Asignatura(codigo, inscriptos, fin, nombre, dia, inicio);
                        universidad.poneAsignatura(asignatura);
                    }
                }

                // Repite el proceso para elementos 'curso'
                NodeList cursosList = doc.getElementsByTagName("curso");
                for (int i = 0; i < cursosList.getLength(); i++) {
                    Node node = cursosList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        // Obtiene los datos del curso de extensión
                        String codigo = element.getElementsByTagName("codigo").item(0).getTextContent();
                        String descripcion = element.getElementsByTagName("descripcion").item(0).getTextContent();
                        int inscriptos = Integer.parseInt(element.getElementsByTagName("inscriptos").item(0).getTextContent());
                        int clases = Integer.parseInt(element.getElementsByTagName("clases").item(0).getTextContent());
                        float costo = Float.parseFloat(element.getElementsByTagName("costo").item(0).getTextContent());

                        // Crea un objeto CursoExtension con los datos obtenidos y lo agrega a la universidad
                        CursoExtension curso = new CursoExtension(codigo, inscriptos, clases, descripcion, costo);
                        universidad.poneCurso(curso);
                    }
                }

                // Repite el proceso para elementos 'evento'
                NodeList eventoInternoList = doc.getElementsByTagName("evento interno");
                for (int i = 0; i < eventoInternoList.getLength(); i++) {
                    Node node = eventoInternoList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        // Obtiene los datos del evento
                        String codigo = element.getElementsByTagName("codigo").item(0).getTextContent();
                        String descripcion = element.getElementsByTagName("descripcion").item(0).getTextContent();
                        LocalDate fecha = LocalDate.parse(element.getElementsByTagName("fecha").item(0).getTextContent());
                        LocalTime inicio = LocalTime.parse(element.getElementsByTagName("inicio").item(0).getTextContent());
                        LocalTime fin = LocalTime.parse(element.getElementsByTagName("fin").item(0).getTextContent());
                        int participantes = Integer.parseInt(element.getElementsByTagName("participantes").item(0).getTextContent());
                        // Crea un objeto Evento con los datos obtenidos y lo agrega a la universidad
                        EventoInterno evento = new EventoInterno(codigo, participantes, inicio, fin, descripcion, fecha);
                        universidad.poneEventoInterno(evento);

                        // Crea un objeto Evento con los datos obtenidos y lo agrega a la universidad
                        Evento eventoInterno = new EventoInterno(codigo, participantes, inicio, fin, descripcion, fecha);
                        universidad.poneEventoInterno(evento);
                    }
                }
                JOptionPane.showMessageDialog(this, "Datos cargados exitosamente.");

                NodeList eventoExternoList = doc.getElementsByTagName("evento externo");
                for (int i = 0; i < eventoExternoList.getLength(); i++) {
                    Node node = eventoExternoList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        // Obtiene los datos del evento
                        String codigo = element.getElementsByTagName("codigo").item(0).getTextContent();
                        String descripcion = element.getElementsByTagName("descripcion").item(0).getTextContent();
                        LocalDate fecha = LocalDate.parse(element.getElementsByTagName("fecha").item(0).getTextContent());
                        LocalTime inicio = LocalTime.parse(element.getElementsByTagName("inicio").item(0).getTextContent());
                        LocalTime fin = LocalTime.parse(element.getElementsByTagName("fin").item(0).getTextContent());
                        int participantes = Integer.parseInt(element.getElementsByTagName("participantes").item(0).getTextContent());
                        int costoalquiler = Integer.parseInt(element.getElementsByTagName("costo alquiler").item(0).getTextContent());
                        String nombreorganizacion = element.getElementsByTagName("nombre organizacion").item(0).getTextContent();
                        // Crea un objeto Evento con los datos obtenidos y lo agrega a la universidad
                        EventoExterno evento = new EventoExterno(codigo, participantes, inicio, fin, descripcion, fecha, costoalquiler, nombreorganizacion);
                        universidad.poneEventoExterno(evento);
                    }
                }
                JOptionPane.showMessageDialog(this, "Datos cargados exitosamente.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + ex.getMessage());
        }
    }


    private void listarAulas() {
        // Implementar la lógica para listar aulas
        JOptionPane.showMessageDialog(this, "Listar Aulas no implementado.");
    }

    private void reservarAula() {
        JPanel panel = new JPanel();
        JTextField numeroAulaField = new JTextField(5);
        JTextField fechaField = new JTextField(10);
        JTextField inicioField = new JTextField(5);
        JTextField finField = new JTextField(5);
        JTextField quienHizoReservaField = new JTextField(20);

        panel.add(new JLabel("Número de Aula:"));
        panel.add(numeroAulaField);
        panel.add(new JLabel("Fecha (dd-mm-aaaa):"));
        panel.add(fechaField);
        panel.add(new JLabel("Inicio (HH):"));
        panel.add(inicioField);
        panel.add(new JLabel("Fin (HH):"));
        panel.add(finField);
        panel.add(new JLabel("Quién Hizo la Reserva:"));
        panel.add(quienHizoReservaField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Reservar Aula", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int numeroAula = Integer.parseInt(numeroAulaField.getText());
                Date fecha = new SimpleDateFormat("dd-MM-yyyy").parse(fechaField.getText());
                int inicio = Integer.parseInt(inicioField.getText());
                int fin = Integer.parseInt(finField.getText());
                String quienHizoReserva = quienHizoReservaField.getText();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados.");
            }
        }
    }


    private void cancelarReserva() {
        JPanel panel = new JPanel();
        JTextField numeroAulaField = new JTextField(5);
        JTextField codigoReservaField = new JTextField(5);

        panel.add(new JLabel("Número de Aula:"));
        panel.add(numeroAulaField);
        panel.add(new JLabel("Código de Reserva:"));
        panel.add(codigoReservaField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Cancelar Reserva", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int numeroAula = Integer.parseInt(numeroAulaField.getText());
                int codigoReserva = Integer.parseInt(codigoReservaField.getText());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);
            }
        });
    }
}