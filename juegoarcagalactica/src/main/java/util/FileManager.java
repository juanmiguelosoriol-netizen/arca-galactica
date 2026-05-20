package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase encargada de leer y escribir archivos de texto.
 * Se usa para manejar archivos como el ranking del juego.
 */
public class FileManager {

    /**
     * Constructor privado para evitar crear objetos de esta clase.
     */
    private FileManager() {
    }

    /**
     * Lee todas las lineas de un archivo.
     *
     * @param file archivo que se desea leer
     * @return lista con las lineas leidas del archivo
     */
    public static List<String> leerFile(File file) {
        final var lista = new ArrayList<String>();

        try {
            final var scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                lista.add(scanner.nextLine());
            }

            scanner.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Error al abrir el file");
            System.err.printf("FileNotFoundException: %s%n", fileNotFoundException.getLocalizedMessage());
        }

        return lista;
    }

    /**
     * Escribe una lista de lineas dentro de un archivo.
     *
     * @param file  archivo donde se escribira la informacion
     * @param lista lista de lineas que se desea guardar
     */
    public static void escribirFile(File file, List<String> lista) {
        try {
            final var fileWriter = new FileWriter(file);

            for (var linea : lista) {
                final var lineaEscribir = String.format("%s%n", linea);
                fileWriter.write(lineaEscribir);
            }

            fileWriter.close();
        } catch (IOException ioException) {
            System.out.println("Error al escribir en el file");
            System.err.printf("IOException: %s%n", ioException.getLocalizedMessage());
        }
    }
}