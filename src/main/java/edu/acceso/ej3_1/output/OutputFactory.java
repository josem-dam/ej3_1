package edu.acceso.ej3_1.output;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

public class OutputFactory {

    public enum Outputs {
        TXT(TxtOutput.class),
        JSON(null),  // Formato soportado en el futuro.
        XML(XmlOutput.class);

        private Class<? extends Output> tipo;

        public static Outputs getOutput(String formato) {
            return Arrays.stream(Outputs.values())
                    .filter(v -> v.name().toLowerCase().equals(formato.toLowerCase()))
                    .findFirst().orElse(null);
        }

        Outputs(Class<? extends Output> tipo) {
            this.tipo = tipo;
        }

        public Class<? extends Output> getTipo() {
            return tipo;
        }
    }

    private Map<String, Object> opciones;
    private Class<? extends Output> tipoOutput;

    public OutputFactory(Map<String, Object> opciones) {
        setOpciones(opciones);
    }

    private void setOpciones(Map<String, Object> opciones) {
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