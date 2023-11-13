package co.unicauca.segundoParcial.access;

import co.unicauca.segundoParcial.model.Accion;
import co.unicauca.segundoParcial.model.Notificacion;
import co.unicauca.segundoParcial.model.Usuario;

import java.util.List;

public interface IGestionAccionesRepository {
    boolean saveUser(Usuario user);
    boolean editUmbrales(long idUsuario, String nombreAccion, long USuperor, long UInferior);
    boolean saveActionUser(long idUsuario, String nombreAccion, long USuperior, long UInferior);
    boolean deleteActionUser(long idUsuario, String nombreAccion);
    Accion findActionUser(long idUsuario, String nombreAccion);
    List<Accion> findAllActionsUser(long idUsuario);
    boolean saveNotification(long idUsuario, Notificacion notificacion);
    List<Notificacion> findAllNotifications(long idUsuario);
}
