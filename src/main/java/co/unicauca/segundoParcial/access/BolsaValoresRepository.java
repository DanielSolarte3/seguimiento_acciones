package co.unicauca.segundoParcial.access;

import co.unicauca.segundoParcial.model.Accion;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BolsaValoresRepository implements IBolsaValoresRepository{

    private Connection conn;

    public BolsaValoresRepository(){
        initDatabase();
    }

    private void initDatabase() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS accion (\n"
                + "	nombreAccion text PRIMARY KEY,\n"
                + "	precioActual integer NOT NULL,\n"
                + "	precioAnterior integer NULL\n"
                + ");";

        try {
            this.connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            //this.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connect() {
        String url = "jdbc:sqlite::memory:";

        try {
            conn = DriverManager.getConnection(url);

        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public boolean saveAction(Accion action) {
        try {
            //Validate product
            if (action == null || action.getNombreAccion().isBlank()) {
                return false;
            }
            //this.connect();

            String sql = "INSERT INTO accion ( nombreAccion, precioActual, precioAnterior) "
                    + "VALUES ( ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, action.getNombreAccion());
            pstmt.setLong(2, action.getPrecioActual());
            pstmt.setLong(3, action.getPrecioAnterior());
            pstmt.executeUpdate();
            //this.disconnect();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean editAction(String nombreAccion, long precioActual) {
        return false;
    }

    @Override
    public Accion findAction(String nombreAccion) {
        return null;
    }

    @Override
    public List<Accion> findAllActions() {
        return null;
    }
}
