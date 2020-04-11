package datvtp.daos;

import datvtp.dtos.Tbl_InvoiceDetailDTO;
import datvtp.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import javax.naming.NamingException;

public class Tbl_InvoiceDetailDAO implements Serializable {

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

    public int checkNewCar(int carID, String rentalDate, String returnDate) throws SQLException, NamingException {
        int result = 0;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT DISTINCT CarID FROM tbl_InvoiceDetail \n"
                    + "	WHERE CarID = ? AND ((RentalDate <= ? AND ReturnDate >= ?) OR (RentalDate <= ? AND ReturnDate >= ?))";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, carID);
            ps.setString(2, rentalDate);
            ps.setString(3, rentalDate);
            ps.setString(4, returnDate);
            ps.setString(5, returnDate);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getInt("CarID");
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int checkRenting(int carID, String rentalDate, String returnDate, int quantity) throws SQLException, NamingException {
        int result = 0;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT (c.Quantity - (t.SumQuantity + ?)) as Result FROM tbl_Car as c,(SELECT SUM(Quantity) as SumQuantity FROM tbl_InvoiceDetail \n"
                    + "	WHERE CarID = ? AND ((RentalDate <= ? AND ReturnDate >= ?) OR (RentalDate <= ? AND ReturnDate >= ?))) as t WHERE c.CarID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, carID);
            ps.setString(3, rentalDate);
            ps.setString(4, rentalDate);
            ps.setString(5, returnDate);
            ps.setString(6, returnDate);
            ps.setInt(7, carID);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getInt("Result");
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int checkRentingForNewCar(int carID, int quantity) throws SQLException, NamingException {
        int result = 0;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT (Quantity - ?) as Result FROM tbl_Car WHERE CarID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, carID);
            rs = ps.executeQuery();

            if (rs.next()) {
                result = rs.getInt("Result");
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean insert(Tbl_InvoiceDetailDTO invoiceDetailDTO, int invoiceID) throws SQLException, NamingException, ParseException {
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            String sql = "INSERT INTO tbl_InvoiceDetail (CarID, RentalDate, ReturnDate, Quantity, TotalPrice, InvoiceID) VALUES (?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, invoiceDetailDTO.getCarID());
            ps.setString(2, invoiceDetailDTO.getRentalDate());
            ps.setString(3, invoiceDetailDTO.getReturnDate());
            ps.setInt(4, invoiceDetailDTO.getQuantity());
            ps.setInt(5, invoiceDetailDTO.calTotalPrice());
            ps.setInt(6, invoiceID);

            result = ps.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }
}
