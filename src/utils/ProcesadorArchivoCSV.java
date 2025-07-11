package utils;

import interfaces.ISerializableCSV;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fabri
 * @param <T>
 */
public class ProcesadorArchivoCSV<T extends ISerializableCSV>{
    
    
    public void guardarCSV(List<T> lista, String path, String encabezado) {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
            // Siempre escribimos el encabezado primero
            bw.write(encabezado);
            bw.newLine();

            // Luego escribimos todos los elementos actuales
            for(T item : lista){
                bw.write(item.toCSV());
                bw.newLine();
            }

        }catch(IOException e) {
            System.err.println("Error al escribir el archivo " + path + ": " + e.getMessage());
        }
    }

    public List<String[]> leerCSV(String path) {
        List<String[]> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea;
            boolean primera = true;
            while ((linea = br.readLine()) != null) {
                if (primera) {
                    // Saltar encabezado
                    primera = false;
                    continue;
                }
                if (!linea.isEmpty()) {
                    lista.add(linea.split(","));
                }
            }
        }catch(IOException e) {
            System.err.println("Error al leer el archivo " + path + ": " + e.getMessage());
        }
        return lista;
    }
}

