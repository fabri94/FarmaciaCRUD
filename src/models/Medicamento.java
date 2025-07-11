package models;

import interfaces.ISerializableCSV;
import java.time.LocalDate;

/**
 *
 * @author Fabri
 */
public class Medicamento extends ProductoFarmaceutico implements ISerializableCSV{
    private boolean conReceta;
    
    public Medicamento(){
        
    }

    public Medicamento(String nombreComercial, String dosis, LocalDate fechaVencimiento) {
        super(nombreComercial, dosis, fechaVencimiento);
    }
    public Medicamento(String nombreComercial, String dosis, LocalDate fechaVencimiento, boolean conReceta) {
        this(nombreComercial, dosis, fechaVencimiento);
        this.conReceta = conReceta;
    }

    public boolean isConReceta() {
        return conReceta;
    }

    public void setConReceta(boolean conReceta) {
        this.conReceta = conReceta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Medicamento\n");
        sb.append(super.toString());
        sb.append(", ¿Requiere receta?: ").append(conReceta ? "SI" : "NO");
        return sb.toString();
    }
    
    @Override
    public String toCSV(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toCSV());
        sb.append(getClass().getSimpleName()).append(",");
        sb.append(this.conReceta ? "SI" : "NO").append(",");
        return sb.toString();
    }
    
    @Override
    public Medicamento fromCSV(String[] columnas){
        String nombre = columnas[0];
        String dosis = columnas[1];
        LocalDate vencimiento = LocalDate.parse(columnas[2]);
        String tipo = columnas[3]; 
        boolean receta = columnas[4].equalsIgnoreCase("SI");
        return new Medicamento(nombre,dosis,vencimiento,receta);       
    }
    
    
}

