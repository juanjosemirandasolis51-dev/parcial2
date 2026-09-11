package org.example.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

public class Log {

    private static PrintWriter escritor;

    public static void iniciar() {

        try {

            File carpeta = new File("logs");

            if (!carpeta.exists()) {
                carpeta.mkdir();
            }

            escritor = new PrintWriter(new FileWriter("logs/ejecucion.txt"));
            escribir("Fecha de ejecucion " + new Date());
            escribir("");

        } catch (Exception error) {
            System.out.println("No se pudo crear el archivo de log");
        }

    }

    public static void escribir() {
        escribir("");
    }

    public static void escribir(String texto) {

        System.out.println(texto);

        if (escritor != null) {
            escritor.println(texto);
        }

    }

    public static void cerrar() {

        if (escritor != null) {
            escritor.close();
        }

    }

}
