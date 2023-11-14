package co.unicauca.segundoParcial.model;

import lombok.Data;

@Data
public class AccionUsuario implements IObserver{
    private long UmbralInferior;
    private long UmbralSuperior;
    private Usuario usuario;
    private Accion accion;


    @Override
    public void notificar(String nombreAccion, long precioActual) {
        if(UmbralInferior>precioActual){
            Notificacion notificacion = new Notificacion();
            notificacion.setTitulo("Umbral Rebasado!!");
            notificacion.setDescripcion("La accion "+nombreAccion+" ha reabasado el umbral inferior");
            usuario.getListNotificaciones().add(notificacion);
        }else if(UmbralSuperior<precioActual){
            Notificacion notificacion = new Notificacion();
            notificacion.setTitulo("Umbral Rebasado!!");
            notificacion.setDescripcion("La accion "+nombreAccion+" ha reabasado el umbral superior");
            usuario.getListNotificaciones().add(notificacion);
        }
    }
}
