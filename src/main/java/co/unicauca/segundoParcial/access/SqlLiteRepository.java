package co.unicauca.segundoParcial.access;

import co.unicauca.segundoParcial.model.Accion;
import co.unicauca.segundoParcial.model.AccionUsuario;
import co.unicauca.segundoParcial.model.Notificacion;
import co.unicauca.segundoParcial.model.Usuario;
import lombok.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Data
public class SqlLiteRepository implements IGestionAccionesRepository{
    private Connection conn;

    private final BolsaValoresRepository bolsaValoresRepository = new BolsaValoresRepository();

    public SqlLiteRepository(){
        initDatabase();
    }

    public void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS usuario (\n"
                + "	idUsuario INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	correo VARCHAR(50) NOT NULL,\n"
                + " contrasena VARCHAR(50) NOT NULL\n"
                + ");"
                + "CREATE TABLE IF NOT EXISTS usuarioAccion (\n"
                + "	idUsuario INTEGER NOT NULL,\n"
                + "	nombreAccion VARCHAR(50) NOT NULL,\n"
                + " umbralSuperior INTEGER NOT NULL,\n"
                + " umbralInferior INTEGER NOT NULL,\n"
                + "	PRIMARY KEY (idUsuario, nombreAccion),\n"
                + "	FOREIGN KEY (idUsuario) REFERENCES usuario (idUsuario),\n"
                + "	FOREIGN KEY (nombreAccion) REFERENCES accion (nombreAccion)\n"
                + ");"
                + "CREATE TABLE IF NOT EXISTS notificacion (\n"
                + "	idNotificacion INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	idUsuario INTEGER NOT NULL,\n"
                + " titulo VARCHAR(50) NOT NULL,\n"
                + " descripcion VARCHAR(50) NOT NULL,\n"
                + "	FOREIGN KEY (idUsuario) REFERENCES usuario (idUsuario)\n"
                + ");";

        try {
            this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connect(){
        String url = "jdbc:sqlite::memory:";

        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconnect(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public boolean saveUser(Usuario user) {
        try {
            if (user == null ||  user.getContrasena().isBlank()) {
                return false;
            }

            String sql = "INSERT INTO usuario ( correo, contrasena) "
                    + "VALUES ( ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getCorreo());
            pstmt.setString(2, user.getContrasena());
            pstmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean editUmbrales(long idUsuario, String nombreAccion, long USuperor, long UInferior) {
        try {
            if (nombreAccion.isBlank()) {
                return false;
            }

            String sql = "UPDATE  usuarioAccion "
                    + "SET umbralSuperior = ?, umbralInferior = ? "
                    + "WHERE idUsuario = ? AND nombreAccion = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, USuperor);
            pstmt.setLong(2, UInferior);
            pstmt.setLong(3, idUsuario);
            pstmt.setString(4, nombreAccion);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean saveActionUser(long idUsuario, String nombreAccion, long USuperior, long UInferior) {
        try {

            if (nombreAccion.isBlank() || USuperior < UInferior) {
                return false;
            }

            String sql = "INSERT INTO usuarioAccion ( idUsuario, nombreAccion, umbralSuperior, umbralInferior) "
                    + "VALUES ( ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, idUsuario);
            pstmt.setString(2, nombreAccion);
            pstmt.setLong(3, USuperior);
            pstmt.setLong(4, UInferior);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteActionUser(long idUsuario, String nombreAccion) {
        try {
            if (nombreAccion.isBlank() || idUsuario <= 0) {
                return false;
            }

            String sql = "DELETE FROM usuarioAccion "
                    + "WHERE idUsuario = ? AND nombreAccion = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, idUsuario);
            pstmt.setString(2, nombreAccion);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public AccionUsuario findActionUser(long idUsuario, String nombreAccion) {
        try {
            if (nombreAccion.isBlank() || idUsuario <= 0) {
                return null;
            }

            String sql = "SELECT * FROM usuarioAccion "
                    + "WHERE nombreAccion = ? AND idUsuario = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombreAccion);
            pstmt.setLong(2, idUsuario);
            pstmt.executeUpdate();

            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                AccionUsuario action = new AccionUsuario();
                action.setUmbralSuperior(res.getLong("umbralSuperior"));
                action.setUmbralInferior(res.getLong("umbralInferior"));
                action.getUsuario().setId((int) res.getLong("idUsuario"));
                action.getAccion().setNombreAccion(res.getString("nombreAccion"));

                return action;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<AccionUsuario> findAllActionsUser(long idUsuario) {
        List<AccionUsuario> actions = new ArrayList<>();
        try {
            if (idUsuario <= 0) {
                return null;
            }

            String sql = "SELECT * FROM usuarioAccion "
                    + "WHERE idUsuario = ?";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                AccionUsuario action = new AccionUsuario();
                action.setUmbralSuperior(rs.getLong("umbralSuperior"));
                action.setUmbralInferior(rs.getLong("umbralInferior"));
                action.getUsuario().setId((int) rs.getLong("idUsuario"));
                action.getAccion().setNombreAccion(rs.getString("nombreAccion"));

                actions.add(action);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actions;
    }

    @Override
    public boolean saveNotification(long idUsuario, Notificacion notificacion) {
        try {
            if (notificacion == null || notificacion.getTitulo().isBlank() || notificacion.getDescripcion().isBlank()) {
                return false;
            }

            String sql = "INSERT INTO notificacion ( idUsuario, titulo, descripcion) "
                    + "VALUES ( ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, idUsuario);
            pstmt.setString(2, notificacion.getTitulo());
            pstmt.setString(3, notificacion.getDescripcion());
            pstmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Notificacion> findAllNotifications(long idUsuario) {
        List<Notificacion> notifications = new ArrayList<>();
        try {
            if (idUsuario <= 0) {
                return null;
            }

            String sql = "SELECT titulo, descripcion FROM notificacion "
                    + "WHERE idUsuario = ?";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Notificacion notification = new Notificacion();
                notification.setTitulo(rs.getString("titulo"));
                notification.setDescripcion(rs.getString("descripcion"));

                notifications.add(notification);

                return notifications;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
