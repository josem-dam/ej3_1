package edu.acceso.ej3_1.output;

import java.io.IOException;
import java.io.OutputStream;

import edu.acceso.ej3_1.modelo.Autor;
import edu.acceso.ej3_1.modelo.Lector;

public class XmlOutput extends Output {

    @Override
    public void escribir(Autor[] autores, Lector[] lectores) throws  IOException {
        OutputStream salida = abrirSalida();
        cerrarSalida();
        throw new UnsupportedOperationException("XML no soportado");
    }
}
