package edu.acceso.ej3_1;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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

public class Entrada {

    private static DocumentBuilder builder;
    private static XPath xpath = XPathFactory.newInstance().newXPath();
    private Document xml;
    private Path archivo;
    
    static {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch(ParserConfigurationException err) {
            throw new RuntimeException("Error al generar el objeto DocumentBuilder");
        }
    }
    
    public Entrada(Path archivo) throws IOException, SAXException {
        setArchivo(archivo);

        try(InputStream st = Files.newInputStream(archivo)) {
            xml = builder.parse(st);
            System.out.println("HOLA");
        }
    }

    public Path getArchivo() {
        return archivo;
    }

    private void setArchivo(Path archivo) {
        this.archivo = archivo;
    }

    public Autor[] leerAutores() {
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
        }).toArray(Autor[]::new);
    }

    public Lector[]  leerLectores() {
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
        }).toArray(Lector[]::new);
    }

    public String nombreBiblioteca() {
        return xml.getDocumentElement().getAttribute("nombre");
    }
}