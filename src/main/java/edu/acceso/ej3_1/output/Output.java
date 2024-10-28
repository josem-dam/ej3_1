package edu.acceso.ej3_1.output;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public abstract class Output {

    private Map<String, Object> opciones;

    public Output() { super(); }

    public Output cargarOpciones(Map<String, Object> opciones) {
        setOpciones(opciones);
        return this;
    }

    private void setOpciones(Map<String, Object> opciones) {
        this.opciones = opciones;
    }

    protected abstract void escribirFormato(OutputStream salida, Map<String, Object> datos) throws IOException;

    public void escribir(Map<String, Object> datos) throws IOException {
        try (
            // Si no se especificó archivo de salida, volcamos en pantalla.
            OutputStream st = (opciones.get("output") == null)?System.out:Files.newOutputStream(Path.of((String) opciones.get("output")));
        ) {
            escribirFormato(st, datos);
        }
    };
}
