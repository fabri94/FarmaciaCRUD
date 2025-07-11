package models;

import exceptions.*;
import interfaces.ICRUD;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Fabri
 * @param <T>
 */
public class Farmacia <T extends ProductoFarmaceutico> implements ICRUD<T>{
    private ArrayList<T> productos;

    public Farmacia() {
        this.productos = new ArrayList<>();
    }
    
    @Override
    public void agregar(T elemento) {
        if(elemento!=null){
           if(productos.contains(elemento)){
            throw new ProductoDuplicadoException("El producto farmaceutico ya esta registrado");
            }if(elemento.getFechaVencimiento().isBefore(LocalDate.now())){
                throw new ProductoVencidoException("El producto está vencido.");
            }
            productos.add(elemento);
        }
    }

    @Override
    public void eliminar(T elemento) {
        if(elemento!=null){
            productos.remove(elemento);
        }
    }

    @Override
    public ArrayList<T> obtenerTodos() {
        return this.productos;
    }
    
    /*public void agregarProducto(ProductoFarmaceutico p){
        if(productos.contains(p)){
            throw new ProductoDuplicadoException("El producto farmaceutico ya esta registrado");
        }if(p.getFechaVencimiento().isBefore(LocalDate.now())){
            throw new ProductoVencidoException("El producto está vencido.");
        }
        productos.add(p);
    }
    
    public void eliminarProducto(ProductoFarmaceutico p){
        productos.remove(p);
    }

    public ArrayList<ProductoFarmaceutico> getProductos() {
        return productos;
    }*/
    
    public ArrayList<Medicamento> medicamentosProximosAVencer() {
        ArrayList<Medicamento> proximos = new ArrayList<>();
        
        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusDays(30);

        for (ProductoFarmaceutico p : productos) {
            if (p instanceof Medicamento m) 
            {
                LocalDate vencimiento = m.getFechaVencimiento();
                if(!vencimiento.isBefore(hoy) && !vencimiento.isAfter(limite)) 
                {
                    proximos.add(m);
                }
            }
        }
        return proximos;
    }
}
