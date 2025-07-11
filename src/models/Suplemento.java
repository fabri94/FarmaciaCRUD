package models;

import interfaces.ISerializableCSV;
import java.time.LocalDate;

/**
 *
 * @author Fabri
 */
public class Suplemento extends ProductoFarmaceutico implements ISerializableCSV{
    private ObjetivoSuplemento objetivo;
    
    public Suplemento(){
        
    }

    public Suplemento(String nombreComercial, String dosis, LocalDate fechaVencimiento) {
        super(nombreComercial, dosis, fechaVencimiento);
    }
    
    public Suplemento(String nombreComercial, String dosis, LocalDate fechaVencimiento, ObjetivoSuplemento objetivo) {
        this(nombreComercial, dosis, fechaVencimiento);
        this.objetivo = objetivo;
    }

    public ObjetivoSuplemento getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(ObjetivoSuplemento objetivo) {
        this.objetivo = objetivo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Suplemento\n");
        sb.append(super.toString());
        sb.append(", Objetivo = ").append(objetivo);
        return sb.toString();
    }

    @Override
    public String toCSV(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toCSV());
        sb.append(getClass().getSimpleName()).append(",");
        sb.append(this.objetivo).append(",");
        return sb.toString();
    }
    
    @Override
    public Suplemento fromCSV(String[] columnas){
        String nombre = columnas[0];
        String dosis = columnas[1];
        LocalDate vencimiento = LocalDate.parse(columnas[2]);
        String tipo = columnas[3]; 
        ObjetivoSuplemento objetivo = ObjetivoSuplemento.valueOf(columnas[4]);
        return new Suplemento(nombre,dosis,vencimiento,objetivo);       
    }
    
    
    
}

