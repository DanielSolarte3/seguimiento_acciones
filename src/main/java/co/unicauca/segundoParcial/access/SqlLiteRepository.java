package co.unicauca.segundoParcial.access;

import co.unicauca.segundoParcial.model.Accion;
import co.unicauca.segundoParcial.model.Notificacion;
import co.unicauca.segundoParcial.model.Usuario;
import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
        return false;
    }

    @Override
    public boolean editUmbrales(long idUsuario, String nombreAccion, long USuperor, long UInferior) {
        return false;
    }

    @Override
    public boolean saveActionUser(long idUsuario, String nombreAccion, long USuperior, long UInferior) {
        return false;
    }

    @Override
    public boolean deleteActionUser(long idUsuario, String nombreAccion) {
        return false;
    }

    @Override
    public Accion findActionUser(long idUsuario, String nombreAccion) {
        return null;
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
