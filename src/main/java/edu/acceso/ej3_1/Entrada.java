package edu.acceso.ej3_1;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
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
            throw new RuntimeException("Error al generar el ibjeto DocumentBuilder");
        }
    }
    
    public Entrada(Path archivo) throws IOException, SAXException {
        setArchivo(archivo);

        try(InputStream st = Files.newInputStream(archivo)) {
            xml = builder.parse(st);
        }
    }

    public Path getArchivo() {
        return archivo;
    }

    private void setArchivo(Path archivo) {
        this.archivo = archivo;
    }

    public Autor[] leerAutores() {
        NodeList autores = null;
        try {
            autores = (NodeList) xpath.evaluate("//libro/nombre", xml, XPathConstants.NODESET);
        }
        catch(XPathExpressionException err) {
            err.printStackTrace();
            assert false: "Expresión para nombre de los autores incorrecta";
        }
    }
}
