package edu.acceso.ej3_1.output;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * Clase abstracta de la que derivan todas las salidas.
 */
public abstract class Output {

    /**
     * Opciones del programa que se pasan a la salida.
     */
    private Map<String, Object> opciones;

    /**
     * Constructor
     */
    public Output() { super(); }

    /**
     * Carga las opciones en el objeto
     * @param opciones Las opciones del programa.
     * @return El propio objeto.
     */
    public Output cargarOpciones(Map<String, Object> opciones) {
        setOpciones(opciones);
        return this;
    }

    /**
     * Setter de opciones.
     * @param opciones Las opciones del programa.
     */
    private void setOpciones(Map<String, Object> opciones) {
        this.opciones = opciones;
    }

    /**
     * Implementa cómo se escriben los datos en la salida.
     * @param salida EL flujo de salida.
     * @param datos Los datos a escribir.
     * @throws IOException Cuando no puede generarse una salida.
     */
    protected abstract void escribirFormato(OutputStream salida, Map<String, Object> datos) throws IOException;

    /**
     * Escribe los datos en la salida.
     * @param datos Los datos a escribir.
     * @throws IOException Cuando no puede escribirse la salida.
     */
    public void escribir(Map<String, Object> datos) throws IOException {
        try (
            // Si no se especificó archivo de salida, volcamos en pantalla.
            OutputStream st = (opciones.get("output") == null)?System.out:Files.newOutputStream(Path.of((String) opciones.get("output")));
        ) {
            escribirFormato(st, datos);
        }
    };
}
