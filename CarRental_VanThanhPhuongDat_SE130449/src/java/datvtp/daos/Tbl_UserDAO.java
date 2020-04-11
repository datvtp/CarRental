package datvtp.daos;

import datvtp.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

public class Tbl_UserDAO implements Serializable {

    private String name;
    private int statusID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public int checkLogin(String email, String password) throws SQLException, NamingException {
        int role = 0;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT RoleID, Name, StatusID FROM tbl_User WHERE Email = ? AND Password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                role = rs.getInt("RoleID");
                this.name = rs.getString("Name");
                this.statusID = rs.getInt("StatusID");
            }
        } finally {
            closeConnection();
        }
        return role;
    }

    public String checkEmailExist(String emailCheck) throws SQLException, NamingException {
        String email = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT Email FROM tbl_User WHERE Email = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, emailCheck);
            rs = ps.executeQuery();

            if (rs.next()) {
                email = rs.getString("Email");
            }
        } finally {
            closeConnection();
        }
        return email;
    }

    public boolean insert(String email, String phone, String name, String address, String createTime, String password) throws SQLException, NamingException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            String sql = "INSERT INTO tbl_User (Email, Phone, Name, Address, CreateTime, Password, RoleID, StatusID) VALUES (?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, phone);
            ps.setString(3, name);
            ps.setString(4, address);
            ps.setString(5, createTime);
            ps.setString(6, password);
            ps.setInt(7, 2);
            ps.setInt(8, 1);

            result = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean activeUser(String email) throws SQLException, NamingException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            String sql = "UPDATE tbl_User SET StatusID = 2 WHERE Email = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            result = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }
}
