package co.unicauca.segundoParcial.model;

public interface IObserver {

    void notificar(String nombreAccion, long precioActual);
}
