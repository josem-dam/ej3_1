package edu.acceso.ej3_1.modelo;

/**
 * Modela un autor de libros.
 */
public class Autor {

    /**
     * Nombre del autor.
     */
    private String nombre;
    /**
     * Cantidad de libros que ha escrito.
     */
    private int libros;

    /**
     * Constructor.
     */
    public Autor() { super(); }

    /**
     * Carga los datos que definen al autor.
     * @param nombre Nombre del autor.
     * @param libros Cantidad de libros escritos.
     * @return El propio autor.
     */
    public Autor cargarDatos(String nombre, int libros) {
        setNombre(nombre);
        setLibros(libros);
        return this;
    }

    /**
     * Getter de nombre
     * @return Nombre del autor.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter de nombre
     * @param nombre Nombre del autor.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de libros.
     * @return Cantidad de libros.
     */
    public int getLibros() {
        return libros;
    }

    /**
     * Setter de libros.
     * @param libros Cantidad de libros.
     */
    public void setLibros(int libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return String.format("%s: %d", nombre, libros);
    }
}
