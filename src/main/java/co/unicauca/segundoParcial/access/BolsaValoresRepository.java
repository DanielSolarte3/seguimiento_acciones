package co.unicauca.segundoParcial.access;

public class BolsaValoresRepository {
    boolean saveAction(Accion action);
    boolean editAction(String nombreAccion, long precioActual);
}
