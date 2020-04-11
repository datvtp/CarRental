package datvtp.daos;

import datvtp.utils.DBUtils;
import datvtp.utils.DateTimeDataType;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

public class Tbl_DiscountDAO implements Serializable {

    private int discountID = 0;

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
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
    
    public int checkDiscountOfUser(String email) throws SQLException, NamingException {
        int result = 0;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT MAX(DiscountID) as DiscountID FROM tbl_Invoice WHERE Email = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getInt("DiscountID");
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getDiscountPercentByEmail(String discountCode, String email) throws SQLException, NamingException {
        int result = 0;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT DiscountPercent, DiscountID FROM tbl_Discount WHERE Code = ? AND ExpiryTime >= ? AND StatusID = 2\n"
                    + "	AND DiscountID NOT IN (SELECT DISTINCT DiscountID FROM tbl_Invoice WHERE Email = ? AND DiscountID IS NOT NULL)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, discountCode);
            ps.setString(2, DateTimeDataType.getTimeNow());
            ps.setString(3, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getInt("DiscountPercent");
                this.discountID = rs.getInt("DiscountID");
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public int getDiscountPercent(String discountCode) throws SQLException, NamingException {
        int result = 0;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT DiscountPercent, DiscountID FROM tbl_Discount WHERE Code = ? AND ExpiryTime >= ? AND StatusID = 2";
            ps = conn.prepareStatement(sql);
            ps.setString(1, discountCode);
            ps.setString(2, DateTimeDataType.getTimeNow());
            rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getInt("DiscountPercent");
                this.discountID = rs.getInt("DiscountID");
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
