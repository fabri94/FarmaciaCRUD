package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.*;

/**
 * FXML Controller class
 *
 * @author Fabri
 */
public class FormularioController implements Initializable {

    @FXML
    private ChoiceBox<String> cbTipo;
    
    @FXML
    private ChoiceBox<ObjetivoSuplemento> cbObjetivo;
    
    @FXML
    private CheckBox chkReceta;
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtDosis;
    
    @FXML
    private DatePicker pickerFechaVencimiento;
           
    @FXML
    private Button btnAceptar;
    
    @FXML
    private Button btnCancelar;
        
    private ProductoFarmaceutico producto;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbTipo.getItems().addAll("MEDICAMENTO","SUPLEMENTO");
        this.cbTipo.setValue("MEDICAMENTO");
        cbObjetivo.getItems().addAll(ObjetivoSuplemento.values());
        this.cbObjetivo.setValue(ObjetivoSuplemento.ENERGIA);
        tipoCambiado();
    }   
    
    @FXML
    public void tipoCambiado(){
        String tipo = cbTipo.getValue();

        if(tipo.equals("MEDICAMENTO")) 
        {
            chkReceta.setVisible(true);
            cbObjetivo.setVisible(false);
        }else if(tipo.equals("SUPLEMENTO")) 
        {
            chkReceta.setVisible(false);
            cbObjetivo.setVisible(true);
        }
    }
    
    @FXML
    public void aceptar(){
        String tipo = cbTipo.getValue();
        String nombre = txtNombre.getText();
        String dosis = txtDosis.getText();
        LocalDate vencimiento = pickerFechaVencimiento.getValue();        
        
        
        if (tipo.equals("MEDICAMENTO")) 
        {
            boolean receta = chkReceta.isSelected();
            if(producto!=null){
                this.producto = modificarProducto(nombre, dosis, vencimiento, tipo, String.valueOf(receta)); 
            }else{                
                this.producto= crearProducto(nombre, dosis, vencimiento, tipo, String.valueOf(receta));
            }
        }else if (tipo.equals("SUPLEMENTO")) 
        {
            ObjetivoSuplemento objetivo = cbObjetivo.getValue();                   
            if(producto!=null){
                this.producto = modificarProducto(nombre, dosis, vencimiento, tipo, objetivo.name());            
            }else
            {
                this.producto = crearProducto(nombre, dosis, vencimiento, tipo, objetivo.name());            
            }
        }
        this.cerrar();
    }
    
    @FXML
    public void cancelar(){
        this.cerrar();
    }
    
    public ProductoFarmaceutico getProducto(){
        return this.producto;
    }
    
    private ProductoFarmaceutico modificarProducto(String nombre, String dosis, LocalDate vencimiento, String tipo, String datoExtra){
        producto.setNombreComercial(nombre);
        producto.setDosis(dosis);
        producto.setFechaVencimiento(vencimiento);

        if(producto instanceof Medicamento m) 
        {
            boolean receta = Boolean.parseBoolean(datoExtra);
            m.setConReceta(receta);
        }
        if(producto instanceof Suplemento s) 
        {
            ObjetivoSuplemento objetivo = ObjetivoSuplemento.valueOf(datoExtra.toUpperCase());
            s.setObjetivo(objetivo);
        }
        return producto;
    }
    
    private ProductoFarmaceutico crearProducto(String nombre, String dosis, LocalDate vencimiento, String tipo, String datoExtra){
        ProductoFarmaceutico productoCreado = null;
        switch(tipo){
            case "MEDICAMENTO":
                
            boolean requiereReceta = Boolean.parseBoolean(datoExtra);
            productoCreado = new Medicamento(nombre, dosis, vencimiento, requiereReceta);
            break;
            
            case "SUPLEMENTO":
            ObjetivoSuplemento objetivo = ObjetivoSuplemento.valueOf(datoExtra.toUpperCase());
            productoCreado = new Suplemento(nombre, dosis, vencimiento, objetivo);
            break;
        }
        return productoCreado;
    }
    
    private void cerrar(){
        Stage stage =(Stage) btnCancelar.getScene().getWindow();
        
        stage.close();
    }

    public void mostrarDatosProductoFarmaceutico(ProductoFarmaceutico productoExistente) {
        this.producto = productoExistente;
        if(producto != null) 
        {
            txtNombre.setText(producto.getNombreComercial());
            txtDosis.setText(producto.getDosis());
            pickerFechaVencimiento.setValue(producto.getFechaVencimiento());

            if(producto instanceof Medicamento m) 
            {
                cbTipo.setValue("MEDICAMENTO");
                chkReceta.setVisible(true);
                cbObjetivo.setVisible(false);
                chkReceta.setSelected(m.isConReceta());
            } else if(producto instanceof Suplemento s) 
            {
                cbTipo.setValue("SUPLEMENTO");
                cbObjetivo.setVisible(true);
                chkReceta.setVisible(false);
                cbObjetivo.setValue(s.getObjetivo());
            }
        }
    }
}