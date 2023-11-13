package co.unicauca.segundoParcial.access;

import co.unicauca.segundoParcial.model.Accion;

public class BolsaValoresRepository {
    boolean saveAction(Accion action){
        return true;
    }
    boolean editAction(String nombreAccion, long precioActual);
}
