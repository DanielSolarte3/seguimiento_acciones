package co.unicauca.segundoParcial.access;

import co.unicauca.segundoParcial.model.Accion;
import co.unicauca.segundoParcial.model.AccionUsuario;
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
                + "	correo TEXT NOT NULL,\n"
                + " contrasena TEXT NOT NULL\n"
                + ");"
                + "CREATE TABLE IF NOT EXISTS usuarioAccion (\n"
                + "	idUsuario INTEGER NOT NULL,\n"
                + "	nombreAccion TEXT NOT NULL,\n"
                + " umbralSuperior INTEGER NOT NULL,\n"
                + " umbralInferior INTEGER NOT NULL,\n"
                + "	PRIMARY KEY (idUsuario, nombreAccion),\n"
                + "	FOREIGN KEY (idUsuario) REFERENCES usuario (idUsuario),\n"
                + "	FOREIGN KEY (nombreAccion) REFERENCES accion (nombreAccion)\n"
                + ");"
                + "CREATE TABLE IF NOT EXISTS notificacion (\n"
                + "	idNotificacion INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	idUsuario INTEGER NOT NULL,\n"
                + " titulo TEXT NOT NULL,\n"
                + " descripcion TEXT NOT NULL,\n"
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
        return false;
    }

    @Override
    public boolean editUmbrales(int idUsuario, String nombreAccion, int USuperor, int UInferior) {



        return false;
    }

    @Override
    public boolean saveActionUser(AccionUsuario accionUsuario) {
        try {
            if(accionUsuario == null || accionUsuario.getAccion().getNombreAccion().isBlank() || accionUsuario.getUsuario().getId() == 0) {
                return false;
            }

            String sql = "INSERT INTO usuarioAccion ( idUsuario, nombreAccion, umbralSuperior, umbralInferior) "
                    + "VALUES ( ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, accionUsuario.getUsuario().getId());
            pstmt.setString(2, accionUsuario.getAccion().getNombreAccion());
            pstmt.setLong(3, accionUsuario.getUmbralSuperior());
            pstmt.setLong(4, accionUsuario.getUmbralInferior());
            pstmt.executeUpdate();

            return true;
        } catch(SQLException ex) {
            Logger.getLogger(SqlLiteRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteActionUser(int idUsuario, String nombreAccion) {
        try {

            if (idUsuario == 0 || nombreAccion.isBlank()) {
                return false;
            }

            String sql = "DELETE FROM usuarioAccion "
                    + "WHERE idUsuario = ? AND nombreAccion = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idUsuario);
            pstmt.setString(2, nombreAccion);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Accion findActionUser(int idUsuario, String nombreAccion) {
        return null;
    }

    @Override
    public List<Accion> findAllActionsUser(int idUsuario) {
        return null;
    }

    @Override
    public boolean saveNotification(int idUsuario, Notificacion notificacion) {
        return false;
    }

    @Override
    public List<Notificacion> findAllNotifications(int idUsuario) {
        return null;
    }
}
