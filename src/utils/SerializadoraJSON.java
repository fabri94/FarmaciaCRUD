package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Fabri
 */
public class SerializadoraJSON {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() 
    {
            @Override
            public void write(JsonWriter out, LocalDate value) throws IOException 
            {
                out.value(value.toString());
            }

            @Override
            public LocalDate read(JsonReader in) throws IOException 
            {
                return LocalDate.parse(in.nextString());
            }
        }).setPrettyPrinting().create();

    public static <T> void guardar(List<T> lista, String ruta) 
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) 
        {
            gson.toJson(lista, writer);
        }catch(IOException e) {
            System.out.println("Error al guardar archivo JSON: "+e.getMessage());
        }
    }
}
