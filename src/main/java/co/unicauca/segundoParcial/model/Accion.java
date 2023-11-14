package co.unicauca.segundoParcial.model;

import lombok.Data;

import java.util.List;

@Data
public class Accion extends Observado{
    private String nombreAccion;
    private long precioActual;
    private long precioAnterior;
    private List<IObserver> observers;

    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void deleteObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(IObserver aux: observers){
            aux.notificar(nombreAccion,precioActual);
        }
    }
}
