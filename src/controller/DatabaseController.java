package controller;

import java.util.Vector;
import model.DatabaseModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import model.UserModel;
import model.DatabaseConnection;

public class DatabaseController {
    
    private DatabaseModel dbModel;
    private Connection conn;
    
    public DatabaseController() {
        
        dbModel = new DatabaseModel();
    }
    
    protected boolean setDatabase() {
        
        DatabaseConnection dbConn = new DatabaseConnection();
        dbConn.setConnection();
        conn = dbConn.getConnection();
        
        if( conn != null ) {
           return true; 
        }else {
            return false;
        }
    }
    
    public Vector<Vector<Object>> getUsers() {
        
        Vector<Vector<Object>> users = new Vector<>();
        ResultSet rs = dbModel.getUser( conn, "__GETUSERS__" );
        
        try {
            while( rs.next() ) {
                
                Vector<Object> user = new Vector<>();
                user.add( rs.getString( "name" ));
                user.add( rs.getString( "email" ));
                user.add( rs.getString( "password" ));
                user.add( rs.getString( "status"));
                
                users.add( user );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return users;
    }
    
    public boolean setUserData( UserModel model ) {
        
        boolean success = dbModel.setUser( conn,  model );
        
        return success;
    }
}
