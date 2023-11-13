package co.unicauca.segundoParcial.model;

import lombok.Data;

@Data
public class Usuario {
    private int id;
    private String correo;
    private String contrasena;
}