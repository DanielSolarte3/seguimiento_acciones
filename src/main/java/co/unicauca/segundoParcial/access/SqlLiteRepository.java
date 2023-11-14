package co.unicauca.segundoParcial.access;

import co.unicauca.segundoParcial.model.Accion;
import co.unicauca.segundoParcial.model.Notificacion;
import co.unicauca.segundoParcial.model.Usuario;
import lombok.Data;

import java.sql.*;
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
                + "CREATE TABLE IF NOT EXISTS usuarioaccion (\n"
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

            String sql = "UPDATE  usuarioaccion "
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

            String sql = "INSERT INTO usuarioaccion ( idUsuario, nombreAccion, umbralSuperior, umbralInferior) "
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

            String sql = "DELETE FROM usuarioaccion "
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
    public Accion findActionUser(long idUsuario, String nombreAccion) {

        try {

            if (action == null || action.getNombreAccion().isBlank()) {
                return false;
            }

            String sql = "INSERT INTO accion ( nombreAccion, precioActual, precioAnterior) "
                    + "VALUES ( ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, action.getNombreAccion());
            pstmt.setLong(2, action.getPrecioActual());
            pstmt.setLong(3, action.getPrecioAnterior());
            pstmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Accion> findAllActionsUser(long idUsuario) {
        return null;
    }

    @Override
    public boolean saveNotification(long idUsuario, Notificacion notificacion) {
        return false;
    }

    @Override
    public List<Notificacion> findAllNotifications(long idUsuario) {
        return null;
    }
}
