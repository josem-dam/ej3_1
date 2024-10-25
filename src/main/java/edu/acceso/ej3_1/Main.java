package edu.acceso.ej3_1;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import org.xml.sax.SAXException;

public class Main {
    public static void main(String[] args) {
        Path archivo = Path.of(System.getProperty("user.dir"), "src", "main", "resources", "biblioteca.xml");
        System.out.println(archivo);
        Entrada entrada = null;

        try {
            entrada = new Entrada(archivo);
        }
        catch(IOException err) {
            err.printStackTrace();
            System.err.println("El archivo no puede leerse");
            System.exit(1);
        }
        catch(SAXException err) {
            System.err.println("El documento XML no está bien formado");
        }

        Autor[] autores = entrada.leerAutores();
        System.out.println(Arrays.toString(autores));
        Lector[] lectores = entrada.leerLectores();
        System.out.println(Arrays.toString(lectores));
        System.out.println(entrada.nombreBiblioteca());
    }
}