package edu.acceso.ej3_1.output;

import java.util.Arrays;

public class OutputFactory {

    public enum Output {
        TXT(TxtOutput.class),
        XML(XmlOutput.class);

        private Class<? extends Output> tipo;

        public static Output getOutput(String formato) {
            return Arrays.stream(Output.values())
                    .filter(v -> v.name().toLowerCase().equals(formato.toLowerCase()))
                    .findFirst().orElse(null);
        }

        Output(Class<? extends Output> tipo) {
            this.tipo = tipo;
        }
    }
}
