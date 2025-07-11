package models;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Fabri
 */
public abstract class ProductoFarmaceutico {
    private String nombreComercial;
    private String dosis;
    private LocalDate fechaVencimiento;
    
    public ProductoFarmaceutico(){
        
    }
    
    public ProductoFarmaceutico(String nombreComercial, String dosis, LocalDate fechaVencimiento) {
        this.nombreComercial = nombreComercial;
        this.dosis = dosis;
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getDosis() {
        return dosis;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre comercial = ").append(nombreComercial);
        sb.append(", Dosis = ").append(dosis);
        sb.append(", Fecha de vencimiento = ").append(fechaVencimiento);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductoFarmaceutico other = (ProductoFarmaceutico) obj;
        if (!Objects.equals(this.nombreComercial, other.nombreComercial)) {
            return false;
        }
        return Objects.equals(this.dosis, other.dosis);
    }
    
    public String toCSV(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.nombreComercial).append(",");
        sb.append(this.dosis).append(",");
        sb.append(this.fechaVencimiento).append(",");
        return sb.toString();
    }
}

