package controller;

import java.util.Vector;
import model.DatabaseModel;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        if (conn != null) {
            return true;
        } else {
            return false;
        }

    }

    public Vector<Vector<Object>> getUsers() {

        Vector<Vector<Object>> users = new Vector<>();
        ResultSet rs = dbModel.getUser(conn);

        try {
            while (rs.next()) {

                Vector<Object> user = new Vector<>();
                user.add(rs.getString("name"));
                user.add(rs.getString("email"));
                user.add(rs.getString("password"));
                user.add(rs.getString("id"));

                users.add(user);
            }
        } catch (SQLException ex) {
            
            ex.printStackTrace();
        }

        return users;
    }

    public boolean setUserData(UserModel model) {

        boolean success = dbModel.setUser(conn, model);

        return success;
    }

    public boolean deleteUserData(UserModel model) {

        boolean success = dbModel.deleteUser(conn, model);

        return success;
    }

    public boolean editUserData(UserModel model) {

        boolean success = dbModel.editUser(conn, model);

        return success;
    }
}
