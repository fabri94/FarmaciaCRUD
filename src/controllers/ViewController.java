package controllers;

import exceptions.*;
import interfaces.ISerializableCSV;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.*;
import utils.ProcesadorArchivoCSV;
import utils.SerializadoraJSON;

/**
 * FXML Controller class
 *
 * @author Fabri
 */
public class ViewController implements Initializable {
    @FXML
    private Button btnAgregar;
    
    @FXML
    private Button btnModificar;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    private Button btnVolver;
    
    @FXML
    private ListView<ProductoFarmaceutico> listViewProductoFarmaceuticos;
    
    private Farmacia farmacia;
    
    private ProcesadorArchivoCSV<ISerializableCSV> procesador;
    
    private static final String ARCHIVO_CSV = "datos.csv";
    
    private static final String ENCABEZADO_CSV = "Nombre comercial,Dosis,Fecha de Vencimiento,Tipo,Con Receta|Tipo suplemento";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        farmacia = new Farmacia();
        procesador = new ProcesadorArchivoCSV<>();
        this.leerArchivoCSV();
    }    
    
    @FXML
    public void abrirFormulario(ProductoFarmaceutico productoExistente){
        try{
            //Cargo la vista del FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formulario.fxml"));
            //SETEO LA ESCENA
            Scene scene = new Scene(loader.load());
            
            FormularioController controller = loader.getController();
            
            controller.mostrarDatosProductoFarmaceutico(productoExistente);
            
            Stage stage = new Stage();
            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);            
            stage.showAndWait();
            
            ProductoFarmaceutico resultado = controller.getProducto();
            
            /*if(resultado!=null){
                if(productoExistente==null){
                    farmacia.agregarProductoFarmaceutico(resultado);
                }
            }*/
            
            if(resultado!=null) 
            {
                if(productoExistente==null){
                    try{
                        farmacia.agregar(resultado);
                    }catch(ProductoDuplicadoException e){
                        mostrarAlerta("ERROR","Producto repetido","No se puede agregar el producto\n"+resultado.toString()+"\n"+e.getMessage());
                    }catch(ProductoVencidoException e){
                        mostrarAlerta("ERROR","Producto vencido","No se puede agregar el producto\n"+resultado.toString()+"\n"+e.getMessage());
                    }
                }
            }            
            this.actualizarVista();
            
        }catch(IOException ex){
            
        }
    }
    
    @FXML
    public void agregarProductoFarmaceutico(ActionEvent e){
        this.abrirFormulario(null);
    }
        
    @FXML
    public void modificarProductoFarmaceutico(ActionEvent e){
        ProductoFarmaceutico producto = listViewProductoFarmaceuticos.getSelectionModel().getSelectedItem();
        if(producto!=null){
            this.abrirFormulario(producto);
        }
        this.actualizarVista();
    }
    
    @FXML
    public void eliminarProductoFarmaceutico(ActionEvent e){
        ProductoFarmaceutico producto = this.listViewProductoFarmaceuticos.getSelectionModel().getSelectedItem();
        if(producto!=null){
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar eliminacion");
            alerta.setHeaderText("¿Esta seguro que desea eliminar este producto?\n");
            alerta.setContentText(producto.toString());
            
            Optional<ButtonType> resultado = alerta.showAndWait();
            
            if(resultado.isPresent()&&resultado.get()==ButtonType.OK){
                this.farmacia.eliminar(producto);
                this.actualizarVista();
            }
        }
    }
    
    public void actualizarVista(){
        this.listViewProductoFarmaceuticos.getItems().clear();
        this.listViewProductoFarmaceuticos.getItems().addAll(farmacia.obtenerTodos());
        this.guardarArchivoCSV();
    }
    
    public void mostrarProximosVencimiento() 
    {
        List<Medicamento> proximos = farmacia.medicamentosProximosAVencer();
        this.listViewProductoFarmaceuticos.getItems().clear();
        this.listViewProductoFarmaceuticos.getItems().addAll(proximos);
        btnVolver.setVisible(true);
    }
    
    @FXML
    public void volverListaCompleta(ActionEvent e){
        this.actualizarVista();
        btnVolver.setVisible(false);        
    }
    
    public void guardarArchivoCSV() {
        List<ISerializableCSV> data = new ArrayList<>();
        for (Object p : farmacia.obtenerTodos()) {
            if (p instanceof ISerializableCSV serializable) {
                data.add(serializable);
            }
        }
        procesador.guardarCSV(data, ARCHIVO_CSV, ENCABEZADO_CSV);
    }
    
    
    public void leerArchivoCSV() {
        List<String[]> datosLeidos = procesador.leerCSV(ARCHIVO_CSV);
        ArrayList<ProductoFarmaceutico> productos = new ArrayList<>();

        for (String[] datos : datosLeidos) {
            ProductoFarmaceutico producto = null;
            switch (datos[3]) {
                case "Medicamento" -> producto = new Medicamento().fromCSV(datos);
                case "Suplemento" -> producto = new Suplemento().fromCSV(datos);
            }
            if (producto != null) {
                productos.add(producto);
            }
        }
        farmacia = new Farmacia();
        farmacia.obtenerTodos().addAll(productos);
        actualizarVista();
}
    
    public void guardarArchivoJSON(){
        ArrayList<Medicamento> proximos = farmacia.medicamentosProximosAVencer();
        SerializadoraJSON.guardar(proximos, "medicamentos_avencer_30dias_omenos.json");
    }
    
    private void mostrarAlerta(String titulo, String header, String mensaje){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}