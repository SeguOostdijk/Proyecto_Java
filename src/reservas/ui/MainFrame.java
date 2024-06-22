package reservas.ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import reservas.logica.*;

public class MainFrame extends JFrame {

    Universidad universidad=Universidad.getInstance();
    public MainFrame() {
        setTitle("Sistema de Gestión de Reservas de Aulas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel paneltitulo=new JPanel();
        paneltitulo.setBounds(550,100,800,200);
        JLabel titulo=new JLabel("Gestión de aulas");
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("", Font.BOLD, 70));
        paneltitulo.setBackground(Color.decode("#298a80"));
        paneltitulo.add(titulo);
        JButton listarAulasButton = new JButton("Listar Aulas");
        JButton reservarAulaButton = new JButton("Agregar Reserva");
        JButton cancelarReservaButton = new JButton("Cancelar Reserva");
        JButton generaReportesbutton = new JButton("Generar Reportes");
        ArrayList<JButton> botones=new ArrayList<>();
        botones.add(listarAulasButton);
        botones.add(reservarAulaButton);
        botones.add(cancelarReservaButton);
        botones.add(generaReportesbutton);
        Dimension buttonSize = new Dimension(300, 60);
        panel.setBackground(Color.decode("#298a80"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy =0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(7, 5, 5, 5);
        for (JButton boton : botones) {
            boton.setPreferredSize(buttonSize);
            boton.setBackground(Color.WHITE);
            boton.setForeground(Color.decode("#298a80").darker());
            boton.setFont(new Font("", Font.BOLD,15));
            panel.add(boton,gbc);
            gbc.gridy++;
        }
        add(paneltitulo);
        add(panel, BorderLayout.CENTER);

        /*cargarDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // implementar cargar datos
                cargarDatos();
            }
        });*/

        listarAulasButton.addActionListener(e -> {
            // implementar listar aulas
            listarAulas();
        });

        reservarAulaButton.addActionListener(e -> {
            // implementar reservar aula
            reservarAula();
        });

        generaReportesbutton.addActionListener(e -> generarReportes());

        cancelarReservaButton.addActionListener(e -> {
            // implementar cancelar reserva
            cancelarReserva();

        });
    }

    private void listarAulas() {
        // Crear panel y componentes para la selección del filtro
        JPanel panel = new JPanel(new FlowLayout());
        JComboBox<String> comboBox = new JComboBox<>();
        JLabel filtrar = new JLabel("Filtrar por:");
        comboBox.addItem("Numero de piso");
        comboBox.addItem("Codigo de curso/evento/asignatura");
        panel.add(filtrar);
        panel.add(comboBox);

        // Mostrar el cuadro de diálogo de selección del filtro
        int resultado = JOptionPane.showConfirmDialog(this, panel, "Consultar aulas", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            // Crear panel y componentes para la entrada del valor
            JPanel panelConsulta = new JPanel(new BorderLayout());
            JLabel pedidoInput = new JLabel();
            JTextField input = new JTextField(15);

            String itemSeleccionado = (String) comboBox.getSelectedItem();
            assert itemSeleccionado != null;
            if (itemSeleccionado.equals("Numero de piso")) {
                pedidoInput.setText("Ingrese el numero de piso (0=Planta baja):");
            } else {
                pedidoInput.setText("Ingrese el Codigo de curso/evento/asignatura:");
            }

            panelConsulta.add(pedidoInput, BorderLayout.NORTH);
            panelConsulta.add(input, BorderLayout.CENTER);

            // Mostrar el cuadro de diálogo de entrada del valor
            int resultadoConsulta = JOptionPane.showConfirmDialog(this, panelConsulta, "Consultar aulas", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (resultadoConsulta == JOptionPane.OK_OPTION) {
                try {
                    if (itemSeleccionado.equals("Numero de piso")) {
                        int numpiso = Integer.parseInt(input.getText());
                        List<Aula> listaAulasPiso = universidad.consultarAula(numpiso);
                        StringBuilder aulasInfo = new StringBuilder("Aulas en el piso " + numpiso + ":\n");
                        for (Aula aula : listaAulasPiso) {
                            aulasInfo.append(aula.toString()).append("\n");
                        }
                        JOptionPane.showMessageDialog(this, aulasInfo.toString(), "Listado de piso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        String codreserva = input.getText();
                        List<Aula> listaAulasReserva = universidad.consultarAula(codreserva);
                        StringBuilder aulasInfo = new StringBuilder("Aulas para el código de reserva " + codreserva + ":\n");
                        for (Aula aula : listaAulasReserva) {
                            aulasInfo.append(aula.toString()).append("\n");
                        }
                        JOptionPane.showMessageDialog(this, aulasInfo.toString(), "Listado de tipo de reserva", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Entrada inválida. Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NoSuchElementException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
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


        asignatura.addActionListener(e -> reservarAulaAsignatura());

        eventoInterno.addActionListener(e -> reservarAulaEventoInterno());

        eventoExterno.addActionListener(e -> reservarAulaEventoExterno());

        cursoExtension.addActionListener(e -> reservarAulaCursoExtension());
        JOptionPane.showOptionDialog(null, panel, "Reservar Aula", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Cancelar"}, "Cancelar");    }



    ////////////////////////////////////RESERVAS/////////////////////////////////////



    private void reservarAulaAsignatura(){
        int resultado;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));

        JLabel tituloAsignatura = new JLabel("Ingrese los datos de la asignatura");
        JLabel codigoAsignatura = new JLabel("Codigo:");
        PlaceholderTextField codigoAsignaturaTexto = new PlaceholderTextField("AA1213");
        JLabel fechaInicio = new JLabel("Fecha de inicio:");
        PlaceholderTextField fechaInicioTexto = new PlaceholderTextField("00-00-0000");

        panel.add(tituloAsignatura);
        panel.add(codigoAsignatura);
        panel.add(codigoAsignaturaTexto);
        panel.add(fechaInicio);
        panel.add(fechaInicioTexto);

        add(panel,BorderLayout.CENTER);
        resultado = JOptionPane.showConfirmDialog(null, panel, "Datos asignatura", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(resultado == JOptionPane.OK_OPTION){
            try{
                String codigoVar = (codigoAsignaturaTexto.getText());
                LocalDate fechaInicioVar = LocalDate.parse(fechaInicioTexto.getText(),DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                List<Aula> aulasDisponibles = universidad.aulasDisponibles(codigoVar,fechaInicioVar);

                JPanel panelAulas = new JPanel();
                JButton aulaButton;

                panelAulas.setLayout(new GridLayout(0,1));
                panelAulas.add(new JLabel("Aulas disponibles, seleccione una: "));
                for(Aula aula : aulasDisponibles){
                    aulaButton = new JButton("Aula: " + aula.getNumero());
                    aulaButton.addActionListener(e -> {
                        try{
                            aula.agregaReservas(codigoVar,fechaInicioVar);
                            JOptionPane.showMessageDialog(null,"Reserva realizada con exito para el Aula: " + aula.getNumero(),"Reserva exitosa",JOptionPane.INFORMATION_MESSAGE);
                        }
                        catch(AulaOcupadaException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la reserva", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                    panelAulas.add(aulaButton);
                }
                JOptionPane.showMessageDialog(null,panelAulas,"Aulas Disponibles",JOptionPane.INFORMATION_MESSAGE);
            }
            catch(DateTimeParseException ex){
                JOptionPane.showMessageDialog(panel,"Error al ingresar datos","Error",JOptionPane.ERROR_MESSAGE);
            }
            catch(NoSuchElementException ex){
                JOptionPane.showMessageDialog(panel,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showConfirmDialog(null,panel,"Reservar Aula",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void reservarAulaEventoInterno(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));

        JLabel opcion = new JLabel("Desea crear un nuevo evento o reservar para uno existente");
        JButton crear = new JButton("Crear");
        JButton existente = new JButton("Existente");

        panel.add(opcion);
        panel.add(crear);
        panel.add(existente);

        crear.addActionListener(e -> {
            int resultado;
            JPanel panel1 = new JPanel();
            panel1.setLayout(new GridLayout(0,1));

            JLabel tituloEventoInterno = new JLabel("Ingrese los datos del evento interno");
            JLabel codigoEventoInterno = new JLabel("Codigo:");
            JTextField codigoEventoInternoTexto = new JTextField();
            JLabel fechaInicio = new JLabel("Fecha de inicio:");
            PlaceholderTextField fechaInicioTexto = new PlaceholderTextField("00-00-0000");
            JLabel horaInicio = new JLabel("Hora de inicio:");
            PlaceholderTextField horaInicioTexto = new PlaceholderTextField("00:00");
            JLabel horaFin = new JLabel("Hora de fin:");
            PlaceholderTextField  horaFinTexto= new PlaceholderTextField("00:00");
            JLabel cantidadInscriptos = new JLabel("Cantidad de inscriptos:");
            PlaceholderTextField cantidadInscriptosTexto = new PlaceholderTextField("00");
            JLabel descripcion = new JLabel("Descripcion del curso:");
            PlaceholderTextField descripcionTexto = new PlaceholderTextField("Por ejemplo curso de cocina");

            panel1.add(tituloEventoInterno);
            panel1.add(codigoEventoInterno);
            panel1.add(codigoEventoInternoTexto);
            panel1.add(fechaInicio);
            panel1.add(fechaInicioTexto);
            panel1.add(horaInicio);
            panel1.add(horaInicioTexto);
            panel1.add(horaFin);
            panel1.add(horaFinTexto);
            panel1.add(cantidadInscriptos);
            panel1.add(cantidadInscriptosTexto);
            panel1.add(descripcion);
            panel1.add(descripcionTexto);

            add(panel1,BorderLayout.CENTER);

            resultado = JOptionPane.showConfirmDialog(null, panel1,"Datos del evento externo",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

            if(resultado==JOptionPane.OK_OPTION) {
                try {
                    String codigoVar = (codigoEventoInternoTexto.getText());
                    LocalDate fechaInicioVar = LocalDate.parse(fechaInicioTexto.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    LocalTime horaInicioVar = LocalTime.parse(horaInicioTexto.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                    LocalTime horaFinVar = LocalTime.parse(horaFinTexto.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                    int cantidadInscriptosVar = Integer.parseInt(cantidadInscriptosTexto.getText());
                    String descripcionVar = descripcionTexto.getText();
                    List<Aula> aulasDisponibles = universidad.aulasDisponibles(fechaInicioVar, horaInicioVar, horaFinVar,cantidadInscriptosVar);

                    JPanel panelAulas = new JPanel();
                    JButton aulaButton;

                    panelAulas.setLayout(new GridLayout(0, 1));
                    panelAulas.add(new JLabel("Aulas disponibles, seleccione una: "));
                    for (Aula aula : aulasDisponibles) {
                        aulaButton = new JButton("Aula: " + aula.getNumero());
                        aulaButton.addActionListener(e1 -> {
                            try {
                                aula.agregaReservasEventoInternoNuevo(codigoVar,cantidadInscriptosVar,horaInicioVar,horaFinVar,descripcionVar,fechaInicioVar);
                                JOptionPane.showMessageDialog(null, "Reserva realizada con exito para el Aula: " + aula.getNumero(), "Reserva exitosa", JOptionPane.INFORMATION_MESSAGE);
                            } catch (AulaOcupadaException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la reserva", JOptionPane.ERROR_MESSAGE);
                            }
                        });
                        panelAulas.add(aulaButton);
                    }
                    JOptionPane.showMessageDialog(null,panelAulas,"Aulas Disponibles",JOptionPane.INFORMATION_MESSAGE);
                }
                catch(DateTimeParseException ex){
                    JOptionPane.showMessageDialog(panel1,"Error al ingresar datos","Error",JOptionPane.ERROR_MESSAGE);
                }
                catch(NoSuchElementException ex){
                    JOptionPane.showMessageDialog(panel1,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        existente.addActionListener(e -> {
            int resultado;
            JPanel panel12 = new JPanel();
            panel12.setLayout(new GridLayout(0,1));

            JLabel tituloEventoInterno = new JLabel("Ingrese los datos del evento interno");
            JLabel codigoEventoInterno = new JLabel("Codigo:");
            JTextField codigoEventoInternoTexto = new JTextField();
            JLabel nombreOrganizacion = new JLabel("Nombre de la organizacion:");
            JTextField nombreOrganizacionTexto = new JTextField();
            JLabel costoAlquiler = new JLabel("Costo de alquiler:");
            JTextField costoALquilerTexto = new JTextField();
            JLabel fechaInicio = new JLabel("Fecha de inicio:");
            PlaceholderTextField fechaInicioTexto = new PlaceholderTextField("00-00-0000");
            JLabel horaInicio = new JLabel("Hora de inicio:");
            PlaceholderTextField horaInicioTexto = new PlaceholderTextField("00:00");
            JLabel horaFin = new JLabel("Hora de fin:");
            PlaceholderTextField  horaFinTexto= new PlaceholderTextField("00:00");
            JLabel cantidadInscriptos = new JLabel("Cantidad de inscriptos:");
            PlaceholderTextField cantidadInscriptosTexto = new PlaceholderTextField("00");

            panel12.add(tituloEventoInterno);
            panel12.add(codigoEventoInterno);
            panel12.add(codigoEventoInternoTexto);
            panel12.add(nombreOrganizacion);
            panel12.add(nombreOrganizacionTexto);
            panel12.add(costoAlquiler);
            panel12.add(costoALquilerTexto);
            panel12.add(fechaInicio);
            panel12.add(fechaInicioTexto);
            panel12.add(horaInicio);
            panel12.add(horaInicioTexto);
            panel12.add(horaFin);
            panel12.add(horaFinTexto);
            panel12.add(cantidadInscriptos);
            panel12.add(cantidadInscriptosTexto);


            add(panel12,BorderLayout.CENTER);

            resultado = JOptionPane.showConfirmDialog(null, panel12,"Datos del evento externo",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

            if(resultado==JOptionPane.OK_OPTION) {
                try {
                    String codigoVar = (codigoEventoInternoTexto.getText());
                    LocalDate fechaInicioVar = LocalDate.parse(fechaInicioTexto.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    LocalTime horaInicioVar = LocalTime.parse(horaInicioTexto.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                    LocalTime horaFinVar = LocalTime.parse(horaFinTexto.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                    int cantidadInscriptosVar = Integer.parseInt(cantidadInscriptosTexto.getText());

                    List<Aula> aulasDisponibles = universidad.aulasDisponibles(fechaInicioVar,horaInicioVar,horaFinVar,cantidadInscriptosVar);

                    JPanel panelAulas = new JPanel();
                    JButton aulaButton;

                    panelAulas.setLayout(new GridLayout(0, 1));
                    panelAulas.add(new JLabel("Aulas disponibles, seleccione una: "));
                    for (Aula aula : aulasDisponibles) {
                        aulaButton = new JButton("Aula: " + aula.getNumero());
                        aulaButton.addActionListener(e12 -> {
                            try {
                                aula.agregaReservasEventoInterno(codigoVar);
                                JOptionPane.showMessageDialog(null, "Reserva realizada con exito para el Aula: " + aula.getNumero(), "Reserva exitosa", JOptionPane.INFORMATION_MESSAGE);
                            } catch (AulaOcupadaException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la reserva", JOptionPane.ERROR_MESSAGE);
                            }
                        });
                        panelAulas.add(aulaButton);
                    }
                    JOptionPane.showMessageDialog(null,panelAulas,"Aulas Disponibles",JOptionPane.INFORMATION_MESSAGE);
                }
                catch(DateTimeParseException ex){
                    JOptionPane.showMessageDialog(panel12,"Error al ingresar datos","Error",JOptionPane.ERROR_MESSAGE);
                }
                catch(NoSuchElementException ex){
                    JOptionPane.showMessageDialog(panel12,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JOptionPane.showOptionDialog(null, panel, "Reservar Aula", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Cancelar"}, "Cancelar");
    }

    private void reservarAulaEventoExterno(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));

        JLabel opcion = new JLabel("Desea crear un nuevo evento o reservar para uno existente");
        JButton crear = new JButton("Crear");
        JButton existente = new JButton("Existente");

        panel.add(opcion);
        panel.add(crear);
        panel.add(existente);

        crear.addActionListener(e -> {
            int resultado;
            JPanel panel1 = new JPanel();
            panel1.setLayout(new GridLayout(0,1));

            JLabel tituloEventoExterno = new JLabel("Ingrese los datos del evento externo");
            JLabel codigoEventoExterno = new JLabel("Codigo:");
            JTextField codigoEventoExternoTexto = new JTextField();
            JLabel nombreOrganizacion = new JLabel("Nombre de la organizacion:");
            JTextField nombreOrganizacionTexto = new JTextField();
            JLabel costoAlquiler = new JLabel("Costo de alquiler:");
            JTextField costoALquilerTexto = new JTextField();
            JLabel fechaInicio = new JLabel("Fecha de inicio:");
            PlaceholderTextField fechaInicioTexto = new PlaceholderTextField("00-00-0000");
            JLabel horaInicio = new JLabel("Hora de inicio:");
            PlaceholderTextField horaInicioTexto = new PlaceholderTextField("00:00");
            JLabel horaFin = new JLabel("Hora de fin:");
            PlaceholderTextField  horaFinTexto= new PlaceholderTextField("00:00");
            JLabel cantidadInscriptos = new JLabel("Cantidad de inscriptos:");
            PlaceholderTextField cantidadInscriptosTexto = new PlaceholderTextField("00");
            JLabel descripcion = new JLabel("Descripcion del curso:");
            PlaceholderTextField descripcionTexto = new PlaceholderTextField("Por ejemplo curso de cocina");

            panel1.add(tituloEventoExterno);
            panel1.add(codigoEventoExterno);
            panel1.add(codigoEventoExternoTexto);
            panel1.add(nombreOrganizacion);
            panel1.add(nombreOrganizacionTexto);
            panel1.add(costoAlquiler);
            panel1.add(costoALquilerTexto);
            panel1.add(fechaInicio);
            panel1.add(fechaInicioTexto);
            panel1.add(horaInicio);
            panel1.add(horaInicioTexto);
            panel1.add(horaFin);
            panel1.add(horaFinTexto);
            panel1.add(cantidadInscriptos);
            panel1.add(cantidadInscriptosTexto);
            panel1.add(descripcion);
            panel1.add(descripcionTexto);

            add(panel1,BorderLayout.CENTER);

            resultado = JOptionPane.showConfirmDialog(null, panel1,"Datos del evento externo",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

    if(resultado==JOptionPane.OK_OPTION) {
        try {
            String codigoVar = (codigoEventoExternoTexto.getText());
            LocalDate fechaInicioVar = LocalDate.parse(fechaInicioTexto.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalTime horaInicioVar = LocalTime.parse(horaInicioTexto.getText(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime horaFinVar = LocalTime.parse(horaFinTexto.getText(), DateTimeFormatter.ofPattern("HH:mm"));
            float costoAlquilerVar = Float.parseFloat(costoALquilerTexto.getText());
            String nombreOrganizacionVar = nombreOrganizacionTexto.getText();
            int cantidadInscriptosVar = Integer.parseInt(cantidadInscriptosTexto.getText());
            String descripcionVar = descripcionTexto.getText();
            List<Aula> aulasDisponibles = universidad.aulasDisponibles(fechaInicioVar, horaInicioVar, horaFinVar,cantidadInscriptosVar);

            JPanel panelAulas = new JPanel();
            JButton aulaButton;

            panelAulas.setLayout(new GridLayout(0, 1));
            panelAulas.add(new JLabel("Aulas disponibles, seleccione una: "));
            for (Aula aula : aulasDisponibles) {
                aulaButton = new JButton("Aula: " + aula.getNumero());
                aulaButton.addActionListener(e1 -> {
                    try {
                        aula.agregaReservasEventoExternoNuevo(fechaInicioVar,horaInicioVar,horaFinVar,codigoVar,nombreOrganizacionVar,costoAlquilerVar,cantidadInscriptosVar,descripcionVar);
                        JOptionPane.showMessageDialog(null, "Reserva realizada con exito para el Aula: " + aula.getNumero(), "Reserva exitosa", JOptionPane.INFORMATION_MESSAGE);
                    } catch (AulaOcupadaException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la reserva", JOptionPane.ERROR_MESSAGE);
                    }
                });
                panelAulas.add(aulaButton);
            }
            JOptionPane.showMessageDialog(null,panelAulas,"Aulas Disponibles",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(DateTimeParseException ex){
            JOptionPane.showMessageDialog(panel1,"Error al ingresar datos","Error",JOptionPane.ERROR_MESSAGE);
        }
        catch(NoSuchElementException ex){
            JOptionPane.showMessageDialog(panel1,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
        });

        existente.addActionListener(e -> {
            int resultado;
            JPanel panel12 = new JPanel();
            panel12.setLayout(new GridLayout(0,1));

            JLabel tituloEventoExterno = new JLabel("Ingrese los datos del evento externo");
            JLabel codigoEventoExterno = new JLabel("Codigo:");
            JTextField codigoEventoExternoTexto = new JTextField();
            JLabel nombreOrganizacion = new JLabel("Nombre de la organizacion:");
            JTextField nombreOrganizacionTexto = new JTextField();
            JLabel costoAlquiler = new JLabel("Costo de alquiler:");
            JTextField costoALquilerTexto = new JTextField();
            JLabel fechaInicio = new JLabel("Fecha de inicio:");
            PlaceholderTextField fechaInicioTexto = new PlaceholderTextField("00-00-0000");
            JLabel horaInicio = new JLabel("Hora de inicio:");
            PlaceholderTextField horaInicioTexto = new PlaceholderTextField("00:00");
            JLabel horaFin = new JLabel("Hora de fin:");
            PlaceholderTextField  horaFinTexto= new PlaceholderTextField("00:00");

            panel12.add(tituloEventoExterno);
            panel12.add(codigoEventoExterno);
            panel12.add(codigoEventoExternoTexto);
            panel12.add(nombreOrganizacion);
            panel12.add(nombreOrganizacionTexto);
            panel12.add(costoAlquiler);
            panel12.add(costoALquilerTexto);
            panel12.add(fechaInicio);
            panel12.add(fechaInicioTexto);
            panel12.add(horaInicio);
            panel12.add(horaInicioTexto);
            panel12.add(horaFin);
            panel12.add(horaFinTexto);


            add(panel12,BorderLayout.CENTER);

            resultado = JOptionPane.showConfirmDialog(null, panel12,"Datos del evento externo",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

    if(resultado==JOptionPane.OK_OPTION) {
        try {
            String codigoVar = (codigoEventoExternoTexto.getText());
            float costoAlquilerVar = Float.parseFloat(costoALquilerTexto.getText());
            String nombreOrganizacionVar = nombreOrganizacionTexto.getText();
            LocalDate fechaInicioVar = LocalDate.parse(fechaInicioTexto.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalTime horaInicioVar = LocalTime.parse(horaInicioTexto.getText(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime horaFinVar = LocalTime.parse(horaFinTexto.getText(), DateTimeFormatter.ofPattern("HH:mm"));


            List<Aula> aulasDisponibles = universidad.aulasDisponibles(codigoVar,fechaInicioVar,horaInicioVar,horaFinVar);

            JPanel panelAulas = new JPanel();
            JButton aulaButton;

            panelAulas.setLayout(new GridLayout(0, 1));
            panelAulas.add(new JLabel("Aulas disponibles, seleccione una: "));
            for (Aula aula : aulasDisponibles) {
                aulaButton = new JButton("Aula: " + aula.getNumero());
                aulaButton.addActionListener(e12 -> {
                    try {
                        aula.agregaReservasEventoExterno(codigoVar,nombreOrganizacionVar,costoAlquilerVar);
                        JOptionPane.showMessageDialog(null, "Reserva realizada con exito para el Aula: " + aula.getNumero(), "Reserva exitosa", JOptionPane.INFORMATION_MESSAGE);
                    } catch (AulaOcupadaException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la reserva", JOptionPane.ERROR_MESSAGE);
                    }
                });
                panelAulas.add(aulaButton);
            }
            JOptionPane.showMessageDialog(null,panelAulas,"Aulas Disponibles",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(DateTimeParseException ex){
            JOptionPane.showMessageDialog(panel12,"Error al ingresar datos","Error",JOptionPane.ERROR_MESSAGE);
        }
        catch(NoSuchElementException ex){
            JOptionPane.showMessageDialog(panel12,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
        });
        JOptionPane.showOptionDialog(null, panel, "Reservar Aula", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Cancelar"}, "Cancelar");
    }

    private void reservarAulaCursoExtension(){
        int resultado;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));

        JLabel tituloCurso = new JLabel("Ingrese los datos del curso de extension");
        JLabel codigoCurso = new JLabel("Codigo:");
        PlaceholderTextField codigoCursoTexto = new PlaceholderTextField("AA123");
        JLabel fechaInicio = new JLabel("Fecha de inicio:");
        PlaceholderTextField fechaInicioTexto = new PlaceholderTextField("00-00-0000");
        JLabel horaInicio = new JLabel("Hora de inicio:");
        PlaceholderTextField horaInicioTexto = new PlaceholderTextField("00:00");
        JLabel horaFin = new JLabel("Hora de fin:");
        PlaceholderTextField  horaFinTexto= new PlaceholderTextField("00:00");

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
        if(resultado == JOptionPane.OK_OPTION){
            try{
                String codigoVar = (codigoCursoTexto.getText());
                LocalDate fechaInicioVar = LocalDate.parse(fechaInicioTexto.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalTime horaInicioVar = LocalTime.parse(horaInicioTexto.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                LocalTime horaFinVar = LocalTime.parse(horaFinTexto.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                List<Aula> aulasDisponibles = universidad.aulasDisponibles(fechaInicioVar,horaInicioVar,horaFinVar,codigoVar);

                JPanel panelAulas = new JPanel();
                JButton aulaButton;

                panelAulas.setLayout(new GridLayout(0,1));
                panelAulas.add(new JLabel("Aulas disponibles, seleccione una: "));
                for(Aula aula : aulasDisponibles){
                    aulaButton = new JButton("Aula: " + aula.getNumero());
                    aulaButton.addActionListener(e -> {
                        try{
                            aula.agregaReservas(codigoVar,fechaInicioVar,horaInicioVar,horaFinVar);
                            JOptionPane.showMessageDialog(null,"Reserva realizada con exito para el Aula: " + aula.getNumero(),"Reserva exitosa",JOptionPane.INFORMATION_MESSAGE);
                        }
                        catch(AulaOcupadaException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error en la reserva", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                    panelAulas.add(aulaButton);
                }
                JOptionPane.showMessageDialog(null,panelAulas,"Aulas Disponibles",JOptionPane.INFORMATION_MESSAGE);
            }
            catch(DateTimeParseException ex){
                JOptionPane.showMessageDialog(panel,"Error al ingresar datos","Error",JOptionPane.ERROR_MESSAGE);
            }
            catch(NoSuchElementException ex){
                JOptionPane.showMessageDialog(panel,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showConfirmDialog(null,panel,"Reservar Aula",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void cancelarReserva() {
        JPanel panel = new JPanel();
        JTextField numeroAulaField = new JTextField(5);
        JTextField codigoReservaField = new JTextField(5);

        panel.add(new JLabel("Número de Aula:"));
        panel.add(numeroAulaField);
        panel.add(new JLabel("Código de Reserva:"));
        panel.add(codigoReservaField);
        Reserva reservaCancelada;
        int result = JOptionPane.showConfirmDialog(null, panel, "Cancelar Reserva", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int numeroAula = Integer.parseInt(numeroAulaField.getText());
                int codigoReserva = Integer.parseInt(codigoReservaField.getText());
                reservaCancelada=Universidad.getInstance().cancelarReserva(numeroAula,codigoReserva);
                JOptionPane.showMessageDialog(panel,"Reserva cancelada con exito"+"\nDatos de la Reserva:\n"+reservaCancelada,"Cancelar Reserva",JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel,"Entrada inválida. Por favor ingrese un numero de aula y codigo de reserva válido","Error",JOptionPane.ERROR_MESSAGE);
            }
            catch (NoSuchElementException ex){
                JOptionPane.showMessageDialog(panel,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void generarReportes(){
        JPanel panel=new JPanel(new FlowLayout());
        JTextArea reporte;
        panel.add(new JLabel("Seleccione el tipo de reporte:"));
        String[] vectorItems={"Montos","Aulas"};
        JComboBox comboBox=new JComboBox(vectorItems);
        panel.add(comboBox);
        int  result= JOptionPane.showConfirmDialog(this, panel, "Generar Reportes",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);//Muestra el panel
        if(result==JOptionPane.OK_OPTION)
        {
            try {
                String itemSeleccionado = (String) comboBox.getSelectedItem();
                assert itemSeleccionado != null;
                if (itemSeleccionado.equals("Montos")) {
                    Montos reporteMontos = universidad.getMontos();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("Reporte de Montos.txt")); //Generar el archivo cada vez que se muestra un reporte permite mantener la información actualizada
                    writer.write(reporteMontos.toString());
                    writer.close();
                    JOptionPane.showMessageDialog(this, reporteMontos, "Reporte Montos", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    ReporteAulasReserva reporteAulas = universidad.getReporteReservas();
                    reporte = new JTextArea(reporteAulas.toString());
                    JScrollPane scrollPane = new JScrollPane(reporte);
                    scrollPane.setPreferredSize(new Dimension(600, 400));
                    BufferedWriter writer = new BufferedWriter(new FileWriter("Reporte de Aulas.txt"));
                    writer.write(reporteAulas.toString());
                    writer.close();
                    JOptionPane.showMessageDialog(this, scrollPane, "Reporte Aulas", JOptionPane.INFORMATION_MESSAGE);
                }

            }
              catch (IllegalStateException e){
                  JOptionPane.showMessageDialog(panel,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
              }
            catch (IOException e){
                System.out.println("Error al generar archivo de reportes");
            }

        }
    }
}


