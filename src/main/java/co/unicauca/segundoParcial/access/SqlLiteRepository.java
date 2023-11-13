package co.unicauca.segundoParcial.access;

import java.sql.Connection;

public class SqlLiteRepository {
    private Connection conn;

    public SqlLiteRepository(Connection conn){
        this.conn = conn;
    }

    public void initDatabase(){
        //TODO
    }

    public void connect(){
        //TODO
    }

    public void disconnect(){
        //TODO
    }
}
