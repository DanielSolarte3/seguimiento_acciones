package co.unicauca.segundoParcial.access;

import co.unicauca.segundoParcial.model.Accion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BolsaValoresRepository implements IBolsaValoresRepository{

    private Connection conn;

    public BolsaValoresRepository(){
        initDatabase();
    }

        private void initDatabase() {
            String sql = "CREATE TABLE IF NOT EXISTS accion (\n"
                    + "	nombreAccion text PRIMARY KEY,\n"
                    + "	precioActual integer NOT NULL,\n"
                    + "	precioAnterior integer NULL\n"
                    + ");";

            try {
                this.connect();
                Statement stmt = conn.createStatement();
                stmt.execute(sql);

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
    public boolean editAction(String nombreAccion, long precioActual) {
        try {
            //Validate action
            if (nombreAccion.isBlank()) {
                return false;
            }

            String sql = "UPDATE  accion "
                    + "SET precioActual = ?"
                    + "WHERE nombreAccion = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, precioActual);
            pstmt.setString(2, nombreAccion);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Accion findAction(String nombreAccion) {
        try {

            String sql = "SELECT * FROM accion  "
                    + "WHERE nombreAccion = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombreAccion);

            ResultSet res = pstmt.executeQuery();

            if (res.next()) {
                Accion action = new Accion();
                action.setNombreAccion(res.getString("nombreAccion"));
                action.setPrecioActual(res.getLong("precioActual"));
                action.setPrecioAnterior(res.getLong("precioAnterior"));
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
    public List<Accion> findAllActions() {
        List<Accion> actions = new ArrayList<>();
        try {

            String sql = "SELECT * FROM accion";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Accion action = new Accion();
                action.setNombreAccion(rs.getString("nombreAccion"));
                action.setPrecioActual(rs.getLong("precioActual"));
                action.setPrecioAnterior(rs.getLong("precioAnterior"));

                actions.add(action);
            }

        } catch (SQLException ex) {
            Logger.getLogger(BolsaValoresRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return actions;
    }
}
