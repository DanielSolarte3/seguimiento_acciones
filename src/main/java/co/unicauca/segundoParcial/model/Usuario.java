package co.unicauca.segundoParcial.model;

import lombok.Data;

import java.util.List;

@Data
public class Usuario {
    private int id;
    private String correo;
    private String contrasena;
    private List<Accion> listAcciones;
    private List<Notificacion> listNotificaciones;
}