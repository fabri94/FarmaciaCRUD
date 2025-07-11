package interfaces;

import java.util.ArrayList;

/**
 *
 * @author Fabri
 */
public interface ICRUD <T>{
    void agregar(T elemento);
    //void actualizar(T elementoAnterior, T elementoModificado);
    void eliminar(T elemento);
    ArrayList<T> obtenerTodos();
}
