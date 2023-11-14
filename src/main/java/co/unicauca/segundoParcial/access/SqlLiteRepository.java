package co.unicauca.segundoParcial.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlLiteRepository {
    private Connection conn;

    private final BolsaValoresRepository bolsaValoresRepository = new BolsaValoresRepository();

    public SqlLiteRepository(Connection conn){
        this.conn = conn;
    }

    public void initDatabase(){
        String sql = "CREATE TABLE IF NOT EXISTS usuario (\n"
                + "	idUsuario int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,\n"
                + "	correo varchar(50) NOT NULL,\n"
                + " contrasena varchar(50) NOT NULL\n"
                + ");"
                + "CREATE TABLE IF NOT EXISTS usuarioaccion (\n"
                + "	idUsuario int(11) NOT NULL,\n"
                + "	nombreAccion varchar(50) NOT NULL,\n"
                + " umbralSuperior int(11) NOT NULL,\n"
                + " umbralInferior int(11) NOT NULL,\n"
                + "	PRIMARY KEY (idUsuario, nombreAccion),\n"
                + "	FOREIGN KEY (idUsuario) REFERENCES usuario (idUsuario),\n"
                + "	FOREIGN KEY (nombreAccion) REFERENCES accion (nombreAccion)\n"
                + ");"
                + "CREATE TABLE IF NOT EXISTS notificacion (\n"
                + "	idNotificacion int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,\n"
                + "	idUsuario int(11) NOT NULL,\n"
                + " titulo varchar(50) NOT NULL,\n"
                + " descripcion varchar(50) NOT NULL,\n"
                + "	FOREIGN KEY (idUsuario) REFERENCES usuario (idUsuario),\n"
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
}
