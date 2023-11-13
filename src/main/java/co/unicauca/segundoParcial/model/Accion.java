package co.unicauca.segundoParcial.model;

import lombok.Data;

@Data
public class Accion {
    private String nombreAccion;
    private long precioActual;
    private long precioAnterior;
}
