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
import reservas.logica.*;
import java.util.Date;



public class MainFrame extends JFrame {
    private Universidad universidad;

    public MainFrame() {
        setTitle("Sistema de Gestión de Reservas de Aulas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        universidad = new Universidad();
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());

        JButton listarAulasButton = new JButton("Listar Aulas");
        JButton reservarAulaButton = new JButton("Agregar Reserva");
        JButton cancelarReservaButton = new JButton("Cancelar Reserva");
        JButton generaReportes = new JButton("Generar Reportes");

        // Establecer un tamaño preferido más pequeño para los botones
        Dimension buttonSize = new Dimension(250, 50);
        listarAulasButton.setPreferredSize(buttonSize);
        reservarAulaButton.setPreferredSize(buttonSize);
        cancelarReservaButton.setPreferredSize(buttonSize);
        generaReportes.setPreferredSize(buttonSize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy =0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);

        panel.add(listarAulasButton,gbc);
        gbc.gridy++;
        panel.add(reservarAulaButton,gbc);
        gbc.gridy++;
        panel.add(cancelarReservaButton,gbc);
        gbc.gridy++;
        panel.add(generaReportes,gbc);

        add(panel, BorderLayout.CENTER);

        /*cargarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // implementar cargar datos
                cargarDatos();
            }
        });*/

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
                        LocalTime inicio = LocalTime.parse(element.getElementsByTagName("inicio").item(0).getTextContent());
                        LocalTime fin = LocalTime.parse(element.getElementsByTagName("fin").item(0).getTextContent());
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
        panel.setLayout(new GridLayout(0,1));

        JLabel titulo = new JLabel("¿Para que desea reservas un aula?");
        JButton asignatura = new JButton("Asignatura");
        JButton eventoInterno = new JButton("Evento Interno");
        JButton eventoExterno = new JButton("Evento Externo");
        JButton cursoExtension = new JButton("Curso de extension");

        panel.add(titulo);
        panel.add(asignatura);
        panel.add(eventoInterno);
        panel.add(eventoExterno);
        panel.add(cursoExtension);
        add(panel,BorderLayout.CENTER);


        asignatura.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resultado;
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0,1));

                JLabel tituloAsignatura = new JLabel("Ingrese los datos de la asignatura");
                JLabel codigoAsignatura = new JLabel("Codigo:");
                JTextField codigoAsignaturaTexto = new JTextField();

                panel.add(tituloAsignatura);
                panel.add(codigoAsignatura);
                panel.add(codigoAsignaturaTexto);
                add(panel,BorderLayout.CENTER);
                resultado = JOptionPane.showConfirmDialog(null, panel, "Datos asignatura", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            }
        });

        eventoInterno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resultado;
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0,1));

                JLabel tituloEventoInterno = new JLabel("Ingrese los datos del evento interno");
                JLabel codigoEventoInterno = new JLabel("Codigo:");
                JTextField codigoEventoInternoTexto = new JTextField();

                panel.add(tituloEventoInterno);
                panel.add(codigoEventoInterno);
                panel.add(codigoEventoInternoTexto);
                add(panel,BorderLayout.CENTER);
                resultado = JOptionPane.showConfirmDialog(null,panel,"Datos evento interno",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
            }
        });

        eventoExterno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resultado;
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0,1));

                JLabel tituloEventoExterno = new JLabel("Ingrese los datos del evento externo");
                JLabel codigoEventoExterno = new JLabel("Codigo:");
                JTextField codigoEventoExternoTexto = new JTextField();
                JLabel nombreOrganizacion = new JLabel("Nombre de la organizacion:");
                JTextField nombreOrganizacionTexto = new JTextField();
                JLabel costoAlquiler = new JLabel("Costo de alquiler:");
                JTextField costoALquilerTexto = new JTextField();

                panel.add(tituloEventoExterno);
                panel.add(codigoEventoExterno);
                panel.add(codigoEventoExternoTexto);
                panel.add(nombreOrganizacion);
                panel.add(nombreOrganizacionTexto);
                panel.add(costoAlquiler);
                panel.add(costoALquilerTexto);
                add(panel,BorderLayout.CENTER);

                resultado = JOptionPane.showConfirmDialog(null,panel,"Datos del evento externo",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
            }
        });

        cursoExtension.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resultado;
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0,1));

                JLabel tituloCurso = new JLabel("Ingrese los datos del curso de extension");
                JLabel codigoCurso = new JLabel("Codigo:");
                JTextField codigoCursoTexto = new JTextField();
                JLabel fechaInicio = new JLabel("Fecha de inicio:");
                JTextField fechaInicioTexto = new JTextField();
                JLabel horaInicio = new JLabel("Hora de inicio:");
                JTextField horaInicioTexto = new JTextField();
                JLabel horaFin = new JLabel("Hora de fin:");
                JTextField  horaFinTexto= new JTextField();

                panel.add(tituloCurso);
                panel.add(codigoCurso);
                panel.add(codigoCursoTexto);
                panel.add(fechaInicio);
                panel.add(fechaInicioTexto);
                panel.add(horaInicio);
                panel.add(horaInicioTexto);
                panel.add(horaFin);
                panel.add(horaFinTexto);

                resultado = JOptionPane.showConfirmDialog(null,panel,"Curso de extension",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
            }
        });
        JOptionPane.showConfirmDialog(null, panel, "Reservar Aula", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
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