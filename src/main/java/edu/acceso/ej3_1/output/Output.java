package edu.acceso.ej3_1.output;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import edu.acceso.ej3_1.modelo.Autor;
import edu.acceso.ej3_1.modelo.Lector;

public abstract class Output {

    private Map<String, Object> opciones;
    private OutputStream salida;

    public Output() { super(); }

    public Output cargarOpciones(Map<String, Object> opciones) {
        setOpciones(opciones);
        return this;
    }

    private void setOpciones(Map<String, Object> opciones) {
        this.opciones = opciones;
    }

    protected OutputStream abrirSalida() throws IOException {
        // Si no se especificó archivo de salida, volcamos en pantalla.
         salida = (opciones.get("output") == null)?System.out:Files.newOutputStream(Path.of((String) opciones.get("output")));
         return salida;
    }

    protected void cerrarSalida() throws IOException {
        if(salida != System.out) salida.close();
    }

    public abstract void escribir(Autor[] autores, Lector[] lectores) throws IOException;
}
