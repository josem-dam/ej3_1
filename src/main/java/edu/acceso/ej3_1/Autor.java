package edu.acceso.ej3_1;

public class Autor {

    private String nombre;
    private int libros;

    public Autor() { super(); }

    public Autor cargarDatos(String nombre, int libros) {
        setNombre(nombre);
        setLibros(libros);
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLibros() {
        return libros;
    }

    public void setLibros(int libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return String.format("%s: %d", nombre, libros);
    }
}
