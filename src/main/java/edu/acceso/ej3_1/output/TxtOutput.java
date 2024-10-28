package edu.acceso.ej3_1.output;

import java.io.IOException;
import java.io.OutputStream;

import edu.acceso.ej3_1.modelo.Autor;
import edu.acceso.ej3_1.modelo.Lector;

public class TxtOutput extends Output {

    @Override
    public void escribir(Autor[] autores, Lector[] lectores) throws IOException {
        OutputStream salida = abrirSalida();
        cerrarSalida();
        if(salida != System.out) salida.close();
    }
}
