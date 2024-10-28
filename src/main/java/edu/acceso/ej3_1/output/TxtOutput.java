package edu.acceso.ej3_1.output;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import edu.acceso.ej3_1.modelo.Autor;
import edu.acceso.ej3_1.modelo.Lector;

public class TxtOutput extends Output {

    @Override
    protected void escribirFormato(OutputStream salida, Map<String, Object> datos) throws IOException {
        try (OutputStreamWriter sw = new OutputStreamWriter(salida)) {
            sw.write("Listado de autores con la cantidad de libros disponibles:\n");
            for(Autor autor: (Autor[]) datos.get("autores")) {
                sw.write(String.format("  - %s.\n", autor));
            }
            sw.write("\nListado de lectores con sus solicitudes de préstamo:\n");
            for(Lector lector: (Lector[]) datos.get("lectores")) {
                sw.write(String.format("  - %s.\n", lector));
            }
        }
    }
}
