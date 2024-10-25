package edu.acceso.ej3_1;

public class Lector {

    private String nombre;
    private int prestamos;

    public Lector() { super(); }

    public Lector cargarDatos(String nombre, int prestamos) {
        setNombre(nombre);
        setPrestamos(prestamos);
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(int prestamos) {
        this.prestamos = prestamos;
    }

    @Override
    public String toString() {
        return String.format("%s: %d", nombre, prestamos);
    }
}
