package co.unicauca.segundoParcial.access;

import co.unicauca.segundoParcial.model.Accion;
import co.unicauca.segundoParcial.model.AccionUsuario;
import co.unicauca.segundoParcial.model.Notificacion;
import co.unicauca.segundoParcial.model.Usuario;

import java.util.List;

public interface IGestionAccionesRepository {
    boolean saveUser(Usuario user);
    boolean editUmbrales(int idUsuario, String nombreAccion, int USuperor, int UInferior);
    boolean saveActionUser(AccionUsuario accionUsuario);
    boolean deleteActionUser(int idUsuario, String nombreAccion);
    Accion findActionUser(int idUsuario, String nombreAccion);
    List<Accion> findAllActionsUser(int idUsuario);
    boolean saveNotification(int idUsuario, Notificacion notificacion);
    List<Notificacion> findAllNotifications(int idUsuario);
}
