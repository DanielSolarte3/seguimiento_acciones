package co.unicauca.segundoParcial.model;

import java.util.List;

public abstract class Observado {

    public abstract void addObserver(IObserver observer);

    public abstract void deleteObserver(IObserver observer);

    public abstract void notifyObservers();
}
