package exceptions;

/**
 *
 * @author Fabri
 */
public class ProductoDuplicadoException extends RuntimeException{

    public ProductoDuplicadoException(String mensaje) {
        super(mensaje);
    }
    
}
