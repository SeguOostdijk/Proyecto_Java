package reservas.logica;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class CargaXML {
    public static void CargaDatosXML() {
        Universidad uni = Universidad.getInstance();

        try {
            File archivoXML = new File("src/reservas/archivos/archivoXML.xml"); // dirección del archivo
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = dbFactory.newDocumentBuilder();
            final Document documentoXML = builder.parse(archivoXML);

            Element nodoRaiz = documentoXML.getDocumentElement();

            final NodeList children = nodoRaiz.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                final Node n = children.item(i);

                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) n;

                    String cod = element.getAttribute("codigo");

                    switch (element.getTagName()) {
                        case "asignatura":

                            LocalTime hi = LocalTime.parse(element.getElementsByTagName("HoraIncio").item(0).getTextContent());
                            LocalTime hf = LocalTime.parse(element.getElementsByTagName("HoraFin").item(0).getTextContent());
                            String nom = element.getElementsByTagName("nombre").item(0).getTextContent();
                            String diaSem = element.getElementsByTagName("diaDeSemana").item(0).getTextContent();
                            LocalDate fic = LocalDate.parse(element.getElementsByTagName("fehcaInicioCursada").item(0).getTextContent());
                            LocalDate ffc = LocalDate.parse(element.getElementsByTagName("fechaFinCursada").item(0).getTextContent());
                            String cantidadInscriptosStr = element.getElementsByTagName("cantidadDeInscriptos").item(0).getTextContent();
                            int cantIns = cantidadInscriptosStr.isEmpty() ? 0 : Integer.parseInt(cantidadInscriptosStr);
                            Asignatura agregaAsig = new Asignatura(cod, cantIns, hf, nom, diaSem, hi);
                            Asignatura.setFechaFinCursada(ffc);
                            Asignatura.setFechaInicioCursada(fic);
                            uni.poneAsignatura(agregaAsig);


                            break;
                        case "curso":
                            int cantclases = Integer.parseInt(element.getElementsByTagName("cantidadDeClases").item(0).getTextContent());
                            String desc = element.getElementsByTagName("descripcion").item(0).getTextContent();
                            float cost = Float.parseFloat(element.getElementsByTagName("costo").item(0).getTextContent());
                            cantIns = Integer.parseInt(element.getElementsByTagName("cantidadDeInscriptos").item(0).getTextContent());
                            CursoExtension agregaCurso = new CursoExtension(cod, cantIns, cantclases, desc, cost);
                            uni.poneCurso(agregaCurso);


                            break;
                        case "eventoInterno":
                            LocalDate fie = LocalDate.parse(element.getElementsByTagName("fechaInicio").item(0).getTextContent());
                            LocalTime hie = LocalTime.parse(element.getElementsByTagName("horaInicio").item(0).getTextContent());
                            LocalTime hfe = LocalTime.parse(element.getElementsByTagName("horaFin").item(0).getTextContent());
                            String dsc = element.getElementsByTagName("descripcion").item(0).getTextContent();
                            cantIns = Integer.parseInt(element.getElementsByTagName("cantidadDeInscriptos").item(0).getTextContent());
                            EventoInterno agregaEventoI = new EventoInterno(cod, cantIns, hie, hfe, dsc, fie);
                            uni.poneEventoInterno(agregaEventoI);

                            break;
                        case "eventoExterno":
                            LocalDate fieE = LocalDate.parse(element.getElementsByTagName("fechaInicio").item(0).getTextContent());
                            LocalTime hieE = LocalTime.parse(element.getElementsByTagName("horaInicio").item(0).getTextContent());
                            LocalTime hfeE = LocalTime.parse(element.getElementsByTagName("horaFin").item(0).getTextContent());
                            String dscE = element.getElementsByTagName("descripcion").item(0).getTextContent();
                            float costAlq = Float.parseFloat(element.getElementsByTagName("costoAlquiler").item(0).getTextContent());
                            String nomorg = element.getElementsByTagName("nombreOrg").item(0).getTextContent();
                            cantIns = Integer.parseInt(element.getElementsByTagName("cantidadDeInscriptos").item(0).getTextContent());
                            EventoExterno agregaEventoE = new EventoExterno(cod, cantIns, hieE, hfeE, dscE, fieE, costAlq, nomorg);
                            uni.poneEventoExterno(agregaEventoE);

                            break;
                        case "aula":
                            NodeList aulas = documentoXML.getElementsByTagName("aula");
                            for (int j = 0; j < aulas.getLength(); j++) {
                                Node nodoAula = aulas.item(j);
                                if (nodoAula.getNodeType() == Node.ELEMENT_NODE) {
                                    Element elementoAula = (Element) nodoAula;

                                    int numAula = Integer.parseInt(elementoAula.getAttribute("codigo"));
                                    int capacidad = Integer.parseInt(element.getElementsByTagName("capacidad").item(0).getTextContent());

                                    Aula aula = new Aula(capacidad, numAula);
                                    uni.poneAula(aula);

                                    NodeList Reservas = elementoAula.getElementsByTagName("reservas");

                                    for (int m = 0; m < Reservas.getLength(); m++) {
                                        Node nodoReserva = Reservas.item(m);

                                        if (nodoReserva.getNodeType() == Node.ELEMENT_NODE) {
                                            Element elementoReserva = (Element) nodoReserva;
                                            LocalTime horaInicio = LocalTime.parse((elementoReserva.getElementsByTagName("horaInicio").item(0).getTextContent()));
                                            LocalTime horaFin = LocalTime.parse(elementoReserva.getElementsByTagName("horaFin").item(0).getTextContent());
                                            LocalDate fecha = LocalDate.parse(elementoReserva.getElementsByTagName("fecha").item(0).getTextContent());
                                            String reservable = (elementoReserva.getElementsByTagName("reservable").item(0).getTextContent());
                                            try {
                                                aula.agregaReservaXML(reservable, fecha, horaInicio, horaFin);

                                            }catch (Exception e){
                                                System.out.println(e.getMessage());
                                            }
                                        }
                                    }


                                }
                            }
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + element.getTagName());
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

}