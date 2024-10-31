package edu.acceso.ej3_1.output;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.stream.Stream;

import edu.acceso.ej3_1.modelo.Autor;
import edu.acceso.ej3_1.modelo.Lector;

/**
 * Modela la salida TXT.
 */
@SuppressWarnings("unchecked")
public class TxtOutput extends Output {

    /**
     * Genera la salida TXT.
     * @param salida Flujo de salida.
     * @param datos Datos a escribir.
     * @throws IOException Si no puede escribirse la salida.
     */
    @Override
    protected void escribirFormato(OutputStream salida, Map<String, Object> datos) throws IOException {
        try (OutputStreamWriter sw = new OutputStreamWriter(salida)) {
            sw.write("Listado de autores con la cantidad de libros disponibles:\n");
            Stream<Autor> autores = ((Stream<Autor>) datos.get("autores"));
            for(Autor autor: (Iterable<Autor>) autores::iterator) {
                sw.write(String.format("  - %s.\n", autor));
            }

            sw.write("\nListado de lectores con sus solicitudes de préstamo:\n");
            Stream<Lector> lectores = ((Stream<Lector>) datos.get("lectores"));
            for(Lector lector: (Iterable<Lector>) lectores::iterator) {
                sw.write(String.format("  - %s.\n", lector));
            }
        }
    }
}
