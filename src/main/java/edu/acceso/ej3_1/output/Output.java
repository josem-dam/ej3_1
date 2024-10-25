package edu.acceso.ej3_1.output;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;

import edu.acceso.ej3_1.modelo.Autor;
import edu.acceso.ej3_1.modelo.Lector;

public abstract class Output {

    private Map<String, Object> opciones;
    protected InputStream input;
    protected OutputStream output;

    public Output() { super(); }

    public Output cargarOpciones(Map<String, Object> opciones) throws IOException, MalformedURLException {
        setOpciones(opciones);
        setInput();
        return this;
    }

    private void setOpciones(Map<String, Object> opciones) {
        this.opciones = opciones;
    }

    public void setInput() throws IOException, MalformedURLException {
        input = ((URI) opciones.get("input")).toURL().openStream();
    }

    public abstract void escribir(Autor[] autores, Lector[] lectores);
}
