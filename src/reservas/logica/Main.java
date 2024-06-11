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
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Main {
    public static void CargaDatosXML(Universidad uni) {
        try {
            File archivoXML = new File("C:\\Users\\pablo\\Proyecto_Java\\src\\reservas\\archivos\\archivoXML.xml"); // direccion del archivo
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
                        int cantIns = Integer.parseInt(element.getAttribute("cantidadDeInscriptos"));

                        switch (element.getTagName()){
                            case "asignatura" :
                                LocalTime hi = (LocalTime.parse(element.getAttribute("HoraIncio")));
                                LocalTime hf = (LocalTime.parse(element.getAttribute("HoraFin")));
                                String nom = element.getAttribute("nombre");
                                String diaSem = element.getAttribute("diaDeSemana");
                                LocalDate fic = LocalDate.parse(element.getAttribute("fehcaInicioCursada"));
                                LocalDate ffc = LocalDate.parse(element.getAttribute("fechaFinCursada"));

                                Asignatura agregaAsig = new Asignatura(cod,cantIns,hf,nom,diaSem,hi);
                                Asignatura.setFechaFinCursada(ffc);
                                Asignatura.setFechaInicioCursada(fic);
                                uni.poneAsignatura(agregaAsig);

                                break;
                            case "curso":
                                int cantclases = Integer.parseInt(element.getAttribute("cantidadDeClases"));
                                String desc = element.getAttribute("descripcion");
                                float cost = Float.parseFloat(element.getAttribute("costo"));

                                CursoExtension agregaCurso = new CursoExtension(cod,cantIns,cantclases,desc,cost);
                                uni.poneCurso(agregaCurso);

                                break;
                            case "eventoInterno":
                                LocalDate fie = LocalDate.parse(element.getAttribute("fechaInicio"));
                                LocalTime hie = LocalTime.parse(element.getAttribute("horaInicio"));
                                LocalTime hfe = LocalTime.parse(element.getAttribute("horaFin"));
                                String dsc = element.getAttribute("descripcion");

                                EventoInterno agregaEventoI = new EventoInterno(cod,cantIns,hie,hfe,dsc,fie);
                                uni.poneEventoInterno(agregaEventoI);

                                break;
                            case "eventoExterno":
                                LocalDate fieE = LocalDate.parse(element.getAttribute("fechaInicio"));
                                LocalTime hieE = LocalTime.parse(element.getAttribute("horaInicio"));
                                LocalTime hfeE = LocalTime.parse(element.getAttribute("horaFin"));
                                String dscE = element.getAttribute("descripcion");
                                Float costAlq = Float.parseFloat(element.getAttribute("costoAlquiler"));
                                String nomorg = element.getAttribute("nombreOrg");

                                EventoExterno agregaEventoE = new EventoExterno(cod, cantIns, hieE, hfeE, dscE, fieE, costAlq, nomorg);
                                uni.poneEventoExterno(agregaEventoE);
                                break;
                            case "aula":
                                NodeList aulas = documentoXML.getElementsByTagName("aula");
                                for(int j = 0; j < aulas.getLength(); j++){
                                    Node nodoAula = aulas.item(j);
                                    if(nodoAula.getNodeType() == Node.ELEMENT_NODE){
                                        Element elementoAula = (Element) nodoAula;

                                        int numAula = Integer.parseInt(element.getAttribute("codigo"));
                                        int capacidad = Integer.parseInt(element.getAttribute("capacidad"));

                                        Aula aula = new Aula(capacidad,numAula);

                                        NodeList Reservas = elementoAula.getElementsByTagName("reservas");

                                        for(int m = 0; m < Reservas.getLength(); m++){
                                            Node nodoReserva = Reservas.item(m);

                                            if(nodoReserva.getNodeType() == Node.ELEMENT_NODE){
                                                Element elementoReserva = (Element) nodoReserva;

                                                LocalTime horaInicio = LocalTime.ofSecondOfDay(Integer.parseInt(elementoReserva.getElementsByTagName("horaInicio").item(0).getTextContent()));
                                                LocalTime horaFin = LocalTime.ofSecondOfDay(Integer.parseInt(elementoReserva.getElementsByTagName("horaFin").item(0).getTextContent()));
                                                LocalDate fecha = LocalDate.parse(elementoReserva.getElementsByTagName("fecha").item(0).getTextContent());
                                                int reservable = Integer.parseInt(elementoReserva.getElementsByTagName("reservable").item(0).getTextContent());

                                                aula.agregaReservas(reservable,fecha,horaInicio,horaFin); // HAY QUE CAMBIAR!!
                                            }
                                        }
                                        uni.poneAula(aula);
                                    }
                                }
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + element.getTagName());
                        }
                }
            }
        } catch(ParserConfigurationException | SAXException | IOException e){
            System.out.println("Error " + e.getMessage());
        }
    }

    public static void main(String[]args){
        Universidad CAECE = new Universidad();

        Main.CargaDatosXML(CAECE);
    }
}
