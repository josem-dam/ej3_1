package edu.acceso.ej3_1;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;

import edu.acceso.ej3_1.output.Output;
import edu.acceso.ej3_1.output.OutputFactory;

/**
 * Clase para el programa principal.
 */
public class Main {

    /**
     * Procesa los argumentos de la línea de órdenes.
     * @param args Argumentos pasados al programa.
     * @return Mapa de claves/valor con las opciones.
     */
    private static Map<String, Object> leerArgumentos(String[] args) {

        Options options = new Options();
        Option salida = Option.builder("o")
                              .longOpt("output")
                              .argName("output")
                              .hasArg()
                              .desc("Archivo de salida")
                              .build();
        options.addOption(salida);

        Option format = Option.builder("f")
                              .longOpt("format")
                              .argName("format")
                              .hasArg()
                              .desc("Formato de la salida")
                              .build();
        options.addOption(format);

        Option entrada = Option.builder("i")
                               .longOpt("input")
                               .argName("input")
                               .hasArg()
                               .desc("Archivo de entrada")
                               .build();

        options.addOption(entrada);
        CommandLineParser parser = new DefaultParser();
        Map<String, Object> opciones = new HashMap<>();

        try {
            CommandLine cmd = parser.parse(options, args);
            opciones.put("output", cmd.getOptionValue("output", (String) null)); 
            opciones.put("format", cmd.getOptionValue("format", "txt"));
            opciones.put("input", cmd.getOptionValue("input", (String) null));

            String input = (String) opciones.get("input");
            if(input != null) {
                if (!input.startsWith("http://")
                    && !input.startsWith("https://")
                    && !input.startsWith("file://")) {
                    input = String.format("file://%s", Path.of(input).toAbsolutePath().toString());
                }

                try {
                    opciones.put("input", new URI(input));
                }
                catch(URISyntaxException err) {
                    System.err.println("La sintaxis de la entrada no es válida");
                    System.exit(2);
                }
            }
        }
        catch(ParseException err) {
            err.printStackTrace();
            System.exit(2);
        }

        return opciones;
    }

    /**
     * Programa principal
     * @param args Argumentos de la línea de órdenes.
     */
    public static void main(String[] args) {
        Map<String, Object> opciones = leerArgumentos(args);

        Entrada entrada = null;

        try {
            entrada = new Entrada((URI) opciones.get("input"));
        }
        catch(IOException err) {
            err.printStackTrace();
            System.err.println("El archivo no puede leerse");
            System.exit(1);
        }
        catch(SAXException err) {
            System.err.println("El documento XML no está bien formado");
        }

        Map<String, Object> datos = Map.of(
            "autores", entrada.leerAutores(),
            "lectores", entrada.leerLectores(),
            "nombre", entrada.nombreBiblioteca()
        );

        Output salida = new OutputFactory(opciones).crearSalida();
        try {
            salida.escribir(datos);
        }
        catch(IOException err) {
            System.err.println("No puede escribirse la salida");
            System.exit(1);
        }
    }
}