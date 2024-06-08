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
                                break;
                            case "evento":
                                LocalDate fie =

                                // codigo="301">
                                // <cantidadDeInscriptos>100</cantidadDeInscriptos>
                                <fechaInicio>05-05-24</fechaInicio>
                                <horaInicio>10</horaInicio>
                                <horaFin>12</horaFin>
                                <descripcion>Evento de intruduccion a la facultad</descripcion>
                                <tipo>interno</tipo>

                                break;
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






/*public class Main {
    public static void cargaDatos(Productos gestor){
        try{
            File archivoXML = new File("C:\\Users\\pablo\\IdeaProjects\\pruebaXML\\src\\archivo.xml"); // direccion del archivo
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = dbFactory.newDocumentBuilder();
            final Document documentoXML = builder.parse(archivoXML);

            Element nodoRaiz = documentoXML.getDocumentElement();

            final NodeList children = nodoRaiz.getChildNodes();
            for(int i = 0; i < children.getLength(); i++){
                producto p = new producto();

                final Node n = children.item(i);

                if(n.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) n;  //transformamos el nodo a elemento

                    p.setId(Integer.parseInt(element.getAttribute("id")));
                    //System.out.println("Id: " + element.getAttribute("id"));

                    p.setNombre(element.getElementsByTagName("nombre").item(0).getTextContent());
                    //System.out.println("Nombre: " + element.getElementsByTagName("nombre").item(0).getTextContent());

                    p.setPrecio(Double.parseDouble(element.getElementsByTagName("precio").item(0).getTextContent()));
                    //System.out.println("Precio: " + element.getElementsByTagName("precio").item(0).getTextContent());

                    p.setCantidad(Integer.parseInt(element.getElementsByTagName("cantidad").item(0).getTextContent()));
                    //System.out.println("Cantidad: " + element.getElementsByTagName("cantidad").item(0).getTextContent());

                    gestor.add(p);

                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e){
            System.out.println("Error " + e.getMessage());
        }
    }

    public static void muestraLista(Productos gestor){
        producto p = gestor.buscaProducto(2); --> eso es id no indice
        System.out.println(p.nombre);
    }

    public static void main(String[] args) {
        Productos gestorProductos = new Productos(); // vendria a ser universidad

        Main.cargaDatos(gestorProductos);
        Main.muestraLista(gestorProductos);
    }
}*/