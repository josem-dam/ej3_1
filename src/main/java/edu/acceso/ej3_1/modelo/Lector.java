package edu.acceso.ej3_1.modelo;

/**
 * Modela un lector.
 */
public class Lector {

    /**
     * Nombre del lector.
     */
    private String nombre;
    /**
     * Cantidad de préstamos.
     */
    private int prestamos;

    /**
     * Constructor.
     */
    public Lector() { super(); }

    /**
     * Carga todos los datos del lector.
     * @param nombre Nombre del lector.
     * @param prestamos Cantidad de préstamos que ha solicitado.
     * @return El propio lector.
     */
    public Lector cargarDatos(String nombre, int prestamos) {
        setNombre(nombre);
        setPrestamos(prestamos);
        return this;
    }

    /**
     * Getter de nombre
     * @return Nombre del lector.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter de nombre
     * @param nombre Nombre del lector.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter de préstamos.
     * @return La cantidad de préstamos.
     */
    public int getPrestamos() {
        return prestamos;
    }

    /**
     * Setter de préstamos.
     * @param prestamos La cantidad de préstamos.
     */
    public void setPrestamos(int prestamos) {
        this.prestamos = prestamos;
    }

    @Override
    public String toString() {
        return String.format("%s: %d", nombre, prestamos);
    }
}