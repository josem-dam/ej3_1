package edu.acceso.ej3_1.output;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

/**
 * Implementa el patrón factory para seleccionar una salida (Output).
 */
public class OutputFactory {

    /**
     * Enumera los distintos tipos de salida que hay.
     */
    public enum Outputs {
        TXT(TxtOutput.class),
        JSON(null),  // Formato soportado en el futuro.
        XML(XmlOutput.class);

        /**
         * La clase que implementa la salida en un determinado formato.
         */
        private Class<? extends Output> tipo;

        /**
         * Devuelve cuál es la salida en función de una cadena. P.ej. "txt" devolverá
         * Outputs.TXT.
         * @param formato La cadena que representa el formato.
         * @return La clase que implementa el formato.
         */
        public static Outputs getOutput(String formato) {
            return Arrays.stream(Outputs.values())
                    .filter(v -> v.name().toLowerCase().equals(formato.toLowerCase()))
                    .findFirst().orElse(null);
        }

        /**
         * Constructor
         * @param tipo La clase que implementa en concreto el formato.
         */
        Outputs(Class<? extends Output> tipo) {
            this.tipo = tipo;
        }

        /**
         * Getter de tipo.
         * @return La clase que implementa el formato.
         */
        public Class<? extends Output> getTipo() {
            return tipo;
        }
    }

    /**
     * Opciones del programa.
     */
    private Map<String, Object> opciones;
    /**
     * Clase que implementa la salida del programa.
     */
    private Class<? extends Output> tipoOutput;

    /**
     * Constructor
     * @param opciones Opciones del programa.
     */
    public OutputFactory(Map<String, Object> opciones) {
        setOpciones(opciones);
    }

    /**
     * Setter de opciones.
     * @param opciones Opciones del programa.
     * @throws IllegalArgumentException Cuando el formato no existe.
     * @throws UnsupportedOperationException Cuando la salida del formato no está implementada.
     */
    private void setOpciones(Map<String, Object> opciones) throws IllegalArgumentException, UnsupportedOperationException {
        this.opciones = opciones;

        Outputs salida = Outputs.getOutput((String) opciones.get("format"));
        if(salida == null) {
            throw new IllegalArgumentException(String.format("%s: formato desconocido"));
        }

        tipoOutput = salida.getTipo();
        if(tipoOutput == null) {
            throw new UnsupportedOperationException(String.format("%s: formato no soportado"));
        }
    }

    /**
     * Crea un objeto de salida.
     * @return Un objeto que implementa el formato de salida.
     */
    public Output crearSalida() {
        try {
            return tipoOutput.getDeclaredConstructor().newInstance().cargarOpciones(opciones);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException err) {
            err.printStackTrace();
            return null;
        }
    }
}