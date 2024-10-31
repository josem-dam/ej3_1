package edu.acceso.ej3_1.output;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.acceso.ej3_1.modelo.Autor;
import edu.acceso.ej3_1.modelo.Lector;

/**
 * Modela la salida XML.
 */
public class XmlOutput extends Output {

    /**
     * Genera la salida XML.
     * @param salida Flujo de salida.
     * @param datos Datos a escribir.
     * @throws IOException Si no puede escribirse la salida.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void escribirFormato(OutputStream salida, Map<String, Object> datos) throws IOException {
        try (OutputStreamWriter sw = new OutputStreamWriter(salida)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Creación del DOM.

            Document xml = builder.newDocument();
            Element root = xml.createElement("biblioteca");
            root.setAttribute("nombre", (String) datos.get("nombre"));
            xml.appendChild(root);

            Element autores = xml.createElement("autores");
            root.appendChild(autores);
            Stream<Autor> sautores = (Stream<Autor>) datos.get("autores");
            for(Autor autor: (Iterable<Autor>) sautores::iterator) {
                Element a = xml.createElement("autor");
                autores.appendChild(a);
                a.setAttribute("nombre", autor.getNombre());
                a.setAttribute("libros", Integer.toString(autor.getLibros()));
            }

            Element lectores = xml.createElement("lectores");
            root.appendChild(lectores);
            Stream<Lector> slectores = (Stream<Lector>) datos.get("lectores");
            for(Lector lector: (Iterable<Lector>) slectores::iterator) {
                Element l = xml.createElement("lector");
                lectores.appendChild(l);
                l.setAttribute("nombre", lector.getNombre());
                l.setAttribute("peticiones", Integer.toString(lector.getPrestamos()));
            }

            // Escritura en el archivo.

            DOMSource source = new DOMSource(xml);
            TransformerFactory tfactory = TransformerFactory.newInstance();
            Transformer transformer = tfactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StreamResult result = new StreamResult(sw);
            transformer.transform(source, result);
        }
        catch(ParserConfigurationException | TransformerException err) {
            assert false: "Configuración del XML incorrecta";
            System.exit(3);
        }
    }
}
