package co.unicauca.segundoParcial.access;

import co.unicauca.segundoParcial.model.Accion;

public interface BolsaValoresRepository {
    boolean saveAction(Accion action);
    boolean editAction(String nombreAccion, long precioActual);
}
