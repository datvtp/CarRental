package datvtp.daos;

import datvtp.dtos.Tbl_InvoiceDTO;
import datvtp.utils.DBUtils;
import datvtp.utils.DateTimeDataType;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

public class Tbl_InvoiceDAO implements Serializable {

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

    public boolean insert(int totalPrice, String email, int discountID) throws SQLException, NamingException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            String sql;
            if (discountID == 0) {
                sql = "INSERT INTO tbl_Invoice (CreateTime, TotalPrice, Email, StatusID) VALUES (?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, DateTimeDataType.getTimeNow());
                ps.setInt(2, totalPrice);
                ps.setString(3, email);
                ps.setInt(4, 2);
            } else {
                sql = "INSERT INTO tbl_Invoice (CreateTime, TotalPrice, Email, DiscountID, StatusID) VALUES (?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, DateTimeDataType.getTimeNow());
                ps.setInt(2, totalPrice);
                ps.setString(3, email);
                ps.setInt(4, discountID);
                ps.setInt(5, 2);
            }

            result = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getLastInvoiceId() throws SQLException, NamingException {
        int invoiceId = 1;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT MAX(InvoiceID) as InvoiceID FROM tbl_Invoice";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                invoiceId = rs.getInt("InvoiceID");
            }
        } finally {
            closeConnection();
        }
        return invoiceId;
    }

    public Tbl_InvoiceDTO findInvoiceByID(int invoiceID) throws SQLException, NamingException {
        Tbl_InvoiceDTO dto = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT InvoiceID, CreateTime, TotalPrice, Email, DiscountID, StatusID FROM tbl_Invoice WHERE InvoiceID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, invoiceID);
            rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("InvoiceID");
                String createTime = rs.getString("CreateTime");
                int totalPrice = rs.getInt("TotalPrice");
                String email = rs.getString("Email");
                int discountID = rs.getInt("DiscountID");
                int statusID = rs.getInt("StatusID");

                dto = new Tbl_InvoiceDTO(id, createTime, totalPrice, email, discountID, statusID);
            }

        } finally {
            closeConnection();
        }
        return dto;
    }
}
