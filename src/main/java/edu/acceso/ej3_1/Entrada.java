package edu.acceso.ej3_1;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.acceso.ej3_1.modelo.Autor;
import edu.acceso.ej3_1.modelo.Lector;

/**
 * Modela la entrada XML de datos.
 */
public class Entrada {

    /**
     * Constructor del DOM del XML.
     */
    private static DocumentBuilder builder;
    /**
     * Fábrica para el uso de expresiones XPath.
     */
    private static XPath xpath = XPathFactory.newInstance().newXPath();
    /**
     * Documento donde se almacena el DOM.
     */
    private Document xml;
    /**
     * Fuente de los datos de entrada.
     */
    private URI entrada;
    
    // Cuando la definición de un atributo estático puede provocar excepciones, hay que meterlo dentro de este bloque.
    static {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch(ParserConfigurationException err) {
            throw new RuntimeException("Error al generar el objeto DocumentBuilder");
        }
    }
    
    /**
     * Constructor de la clase.
     * @param entrada URL de los datos de entrada. Si es null, se sobreentiende
     *    que es la entrada estándar.
     * @throws IOException Si no puede accederse a la entrada.
     * @throws MalformedURLException Errores en la URL.
     * @throws SAXException Problemas con el XML de la entrada.
     */
    public Entrada(URI entrada) throws IOException, MalformedURLException, SAXException {
        setEntrada(entrada);
    }

    /**
     * Getter de entrada.
     * @return La URL de la entrada.
     */
    public URI getEntrada() {
        return entrada;
    }

    /**
     * Setter de entrada.
     * @param entrada URL de la entrada de datos.
     * @throws IOException Si no puede accederse a la URL.
     * @throws MalformedURLException Error en el formato de la URL.
     * @throws SAXException Error en el procesamiento del XML.
     */
    private void setEntrada(URI entrada) throws IOException, MalformedURLException, SAXException {
        this.entrada = entrada;
        try(InputStream st = (entrada == null)?System.in:entrada.toURL().openStream()) {
            xml = builder.parse(st);
        }
    }

    /**
     * Obtiene los autores de la biblioteca.
     * @return La lista de autores.
     */
    public Stream<Autor> leerAutores() {
        Stream<String> nombres;
        // Nombres de los autores (sin repetición)
        try {
            NodeList autores = (NodeList) xpath.evaluate("//libro/autor", xml, XPathConstants.NODESET);
            nombres = IntStream.range(0, autores.getLength())
                        .mapToObj(i -> autores.item(i).getTextContent())
                        .distinct();
        }
        catch(XPathExpressionException err) {
            err.printStackTrace();
            assert false: "Expresión para nombre de los autores incorrecta";
            return null;
        }

        return nombres.map(autor -> {
            try {
                int cantidad = ((Double) xpath.evaluate(String.format("count(//libro[autor='%s'])", autor), xml, XPathConstants.NUMBER)).intValue();   
                return new Autor().cargarDatos(autor, cantidad);
            }
            catch(XPathExpressionException err) {
                assert false: "Expresión para nombre de los autores incorrecta";
                return null;
            }
        });
    }

    /**
     * Obtiene los lectores de la biblioteca.
     * @return La lista de lectores.
     */
    public Stream<Lector> leerLectores() {
        NodeList lectores = xml.getElementsByTagName("lector");
        Stream<Node>  ls = IntStream.range(0, lectores.getLength()).mapToObj(lectores::item);

        return ls.map(lector -> {
            String nombre = ((Element) lector).getElementsByTagName("nombre").item(0).getTextContent();
            String id = ((Element) lector).getAttribute("registro");

            try {
                int prestamos = ((Double) xpath.evaluate(String.format("count(//prestamo[@lector='%s'])", id), xml, XPathConstants.NUMBER)).intValue();
                return new Lector().cargarDatos(nombre, prestamos);
            }
            catch(XPathExpressionException err) {
                assert false: "Expresión para nombre de los autores incorrecta";
                return null;
            }
        });
    }

    /**
     * Obtiene el nombre de la biblioteca.
     * @return Nombre de la biblioteca.
     */
    public String nombreBiblioteca() {
        return xml.getDocumentElement().getAttribute("nombre");
    }
}