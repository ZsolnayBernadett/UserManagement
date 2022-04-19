
package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DatabaseModel {

    public DatabaseModel() {
    }

    private String[] getSql(String fileName) {

        String filePath = "sql/" + fileName + ".sql";
        String[] sql = null;
        try {

            Path path = Path.of(filePath);
            String content = Files.readString(path);
            sql = content.split(";");

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return sql;
    }

    public ResultSet getUser(Connection conn) {

        String[] sql = getSql("__GETUSERS__");
        Statement stmt = null;
        ResultSet rs = null;
        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql[0]);

        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        
        return rs;
    }

    public boolean setUser(Connection conn, UserModel user) {

        String[] sql = getSql("__SETUSERS__");
        PreparedStatement pstmt = null;

        try {

            pstmt = conn.prepareStatement(sql[0]);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.executeQuery();

            return true;

        } catch (SQLException ex) {

            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(Connection conn, UserModel user) {

        String[] sql = getSql("___DELETEUSERS__");
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(sql[0]);
            pstmt.setString(1, user.getEmail());
            pstmt.executeQuery();
            return true;

        } catch (SQLException ex) {

            ex.printStackTrace();
            return false;
        }

    }

    public boolean editUser(Connection conn, UserModel user) {

        String[] sql = getSql("__EDITUSERS__");
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql[0]);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getId());
            pstmt.executeQuery();
            return true;
        } catch (SQLException ex) {

            ex.printStackTrace();
            return false;
        }
    }
}
