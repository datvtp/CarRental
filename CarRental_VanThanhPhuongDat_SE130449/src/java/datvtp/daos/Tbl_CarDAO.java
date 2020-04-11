package datvtp.daos;

import datvtp.dtos.Tbl_CarDTO;
import datvtp.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

public class Tbl_CarDAO implements Serializable {

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

    public int getNumberOfCar(String nameCar, int categoryCar, String rentalDate, String returnDate, int amount) throws SQLException, NamingException {
        int total = 0;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT COUNT(c.CarID) as Total FROM tbl_Car as c LEFT JOIN (SELECT CarID, SUM(Quantity) as Amount FROM tbl_InvoiceDetail\n"
                    + "	WHERE ((RentalDate <= ? AND ReturnDate >= ?) OR (RentalDate <= ? AND ReturnDate >= ?))\n"
                    + "	GROUP BY CarID) as t ON c.CarID = t.CarID\n"
                    + "	WHERE c.CarID NOT IN (SELECT c.CarID FROM tbl_Car as c, (SELECT CarID, SUM(Quantity) as Amount FROM tbl_InvoiceDetail\n"
                    + "	WHERE ((RentalDate <= ? AND ReturnDate >= ?) OR (RentalDate <= ? AND ReturnDate >= ?))\n"
                    + "	GROUP BY CarID) as t\n"
                    + "	WHERE c.CarID = t.CarID AND (c.Quantity - t.Amount) = 0)\n"
                    + "	AND c.Name LIKE ?\n"
                    + "	";
            if (categoryCar != 0) {
                sql += "AND CategoryID = " + categoryCar + "\n"
                        + "	";
            }
            if (amount != 0 && amount != 50) {
                sql += "AND (c.Quantity-t.Amount) = " + amount + "\n"
                        + "	";
            }
            sql += "AND c.StatusID = 2";
            ps = conn.prepareStatement(sql);
            ps.setString(1, rentalDate);
            ps.setString(2, rentalDate);
            ps.setString(3, returnDate);
            ps.setString(4, returnDate);
            ps.setString(5, rentalDate);
            ps.setString(6, rentalDate);
            ps.setString(7, returnDate);
            ps.setString(8, returnDate);
            ps.setString(9, "%" + nameCar + "%");

            rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("Total");
            }

        } finally {
            closeConnection();
        }
        return total;
    }

    public int getNumberOfCarForAdmin(String nameCar, int categoryCar, String rentalDate, String returnDate, int amount) throws SQLException, NamingException {
        int total = 0;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT COUNT(c.CarID) as Total FROM tbl_Car as c LEFT JOIN (SELECT CarID, SUM(Quantity) as Amount FROM tbl_InvoiceDetail\n"
                    + "	WHERE ((RentalDate <= ? AND ReturnDate >= ?) OR (RentalDate <= ? AND ReturnDate >= ?))\n"
                    + "	GROUP BY CarID) as t ON c.CarID = t.CarID\n"
                    + "	WHERE c.CarID NOT IN (SELECT c.CarID FROM tbl_Car as c, (SELECT CarID, SUM(Quantity) as Amount FROM tbl_InvoiceDetail\n"
                    + "	WHERE ((RentalDate <= ? AND ReturnDate >= ?) OR (RentalDate <= ? AND ReturnDate >= ?))\n"
                    + "	GROUP BY CarID) as t\n"
                    + "	WHERE c.CarID = t.CarID AND (c.Quantity - t.Amount) = 0)\n"
                    + "	AND c.Name LIKE ?\n"
                    + "	";
            if (categoryCar != 0) {
                sql += "AND CategoryID = " + categoryCar + "\n"
                        + "	";
            }
            if (amount != 0 && amount != 50) {
                sql += "AND (c.Quantity-t.Amount) = " + amount + "\n"
                        + "	";
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1, rentalDate);
            ps.setString(2, rentalDate);
            ps.setString(3, returnDate);
            ps.setString(4, returnDate);
            ps.setString(5, rentalDate);
            ps.setString(6, rentalDate);
            ps.setString(7, returnDate);
            ps.setString(8, returnDate);
            ps.setString(9, "%" + nameCar + "%");

            rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("Total");
            }

        } finally {
            closeConnection();
        }
        return total;
    }

    public List<Tbl_CarDTO> getListCar(String nameCar, int categoryCar, String rentalDate, String returnDate, int amount, int offsetRecord, int next) throws SQLException, NamingException {
        List<Tbl_CarDTO> list = new ArrayList<>();
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT c.CarID, c.Image, c.Name, c.Color, c.Year, c.Price, (c.Quantity-t.Amount) as QuantityCar, c.CategoryID FROM tbl_Car as c LEFT JOIN (SELECT CarID, SUM(Quantity) as Amount FROM tbl_InvoiceDetail\n"
                    + "	WHERE ((RentalDate <= ? AND ReturnDate >= ?) OR (RentalDate <= ? AND ReturnDate >= ?))\n"
                    + "	GROUP BY CarID) as t ON c.CarID = t.CarID\n"
                    + "	WHERE c.CarID NOT IN (SELECT c.CarID FROM tbl_Car as c, (SELECT CarID, SUM(Quantity) as Amount FROM tbl_InvoiceDetail\n"
                    + "	WHERE ((RentalDate <= ? AND ReturnDate >= ?) OR (RentalDate <= ? AND ReturnDate >= ?))\n"
                    + "	GROUP BY CarID) as t\n"
                    + "	WHERE c.CarID = t.CarID AND (c.Quantity - t.Amount) = 0)\n"
                    + "	AND c.Name LIKE ?\n"
                    + "	";
            if (categoryCar != 0) {
                sql += "AND CategoryID = " + categoryCar + "\n"
                        + "	";
            }
            if (amount != 0 && amount != 50) {
                sql += "AND (c.Quantity-t.Amount) = " + amount + "\n"
                        + "	";
            }
            sql += "AND c.StatusID = 2\n"
                    + "	ORDER BY c.Year DESC\n"
                    + "	OFFSET ? ROWS\n"
                    + "	FETCH NEXT ? ROWS ONLY";
            ps = conn.prepareStatement(sql);
            ps.setString(1, rentalDate);
            ps.setString(2, rentalDate);
            ps.setString(3, returnDate);
            ps.setString(4, returnDate);
            ps.setString(5, rentalDate);
            ps.setString(6, rentalDate);
            ps.setString(7, returnDate);
            ps.setString(8, returnDate);
            ps.setString(9, "%" + nameCar + "%");
            ps.setInt(10, offsetRecord);
            ps.setInt(11, next);

            rs = ps.executeQuery();

            while (rs.next()) {
                int carID = rs.getInt("CarID");
                String image = rs.getString("Image");
                String name = rs.getString("Name");
                String color = rs.getString("Color");
                int year = rs.getInt("Year");
                int price = rs.getInt("Price");
                int quantity = rs.getInt("QuantityCar");
                int categoryID = rs.getInt("CategoryID");

                list.add(new Tbl_CarDTO(carID, image, name, color, year, price, quantity, categoryID));
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public List<Tbl_CarDTO> getListCarForAdmin(String nameCar, int categoryCar, String rentalDate, String returnDate, int amount, int offsetRecord, int next) throws SQLException, NamingException {
        List<Tbl_CarDTO> list = new ArrayList<>();
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT c.CarID, c.Image, c.Name, c.Color, c.Year, c.Price, (c.Quantity-t.Amount) as QuantityCar, c.CategoryID FROM tbl_Car as c LEFT JOIN (SELECT CarID, SUM(Quantity) as Amount FROM tbl_InvoiceDetail\n"
                    + "	WHERE ((RentalDate <= ? AND ReturnDate >= ?) OR (RentalDate <= ? AND ReturnDate >= ?))\n"
                    + "	GROUP BY CarID) as t ON c.CarID = t.CarID\n"
                    + "	WHERE c.CarID NOT IN (SELECT c.CarID FROM tbl_Car as c, (SELECT CarID, SUM(Quantity) as Amount FROM tbl_InvoiceDetail\n"
                    + "	WHERE ((RentalDate <= ? AND ReturnDate >= ?) OR (RentalDate <= ? AND ReturnDate >= ?))\n"
                    + "	GROUP BY CarID) as t\n"
                    + "	WHERE c.CarID = t.CarID AND (c.Quantity - t.Amount) = 0)\n"
                    + "	AND c.Name LIKE ?\n"
                    + "	";
            if (categoryCar != 0) {
                sql += "AND CategoryID = " + categoryCar + "\n"
                        + "	";
            }
            if (amount != 0 && amount != 50) {
                sql += "AND (c.Quantity-t.Amount) = " + amount + "\n"
                        + "	";
            }
            sql += "	ORDER BY c.Year DESC\n"
                    + "	OFFSET ? ROWS\n"
                    + "	FETCH NEXT ? ROWS ONLY";
            ps = conn.prepareStatement(sql);
            ps.setString(1, rentalDate);
            ps.setString(2, rentalDate);
            ps.setString(3, returnDate);
            ps.setString(4, returnDate);
            ps.setString(5, rentalDate);
            ps.setString(6, rentalDate);
            ps.setString(7, returnDate);
            ps.setString(8, returnDate);
            ps.setString(9, "%" + nameCar + "%");
            ps.setInt(10, offsetRecord);
            ps.setInt(11, next);

            rs = ps.executeQuery();

            while (rs.next()) {
                int carID = rs.getInt("CarID");
                String image = rs.getString("Image");
                String name = rs.getString("Name");
                String color = rs.getString("Color");
                int year = rs.getInt("Year");
                int price = rs.getInt("Price");
                int quantity = rs.getInt("QuantityCar");
                int categoryID = rs.getInt("CategoryID");

                list.add(new Tbl_CarDTO(carID, image, name, color, year, price, quantity, categoryID));
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public int findQuantityByCarID(int carID) throws SQLException, NamingException {
        int quantity = 0;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT Quantity FROM tbl_Car WHERE CarID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, carID);
            rs = ps.executeQuery();

            if (rs.next()) {
                quantity = rs.getInt("Quantity");
            }
        } finally {
            closeConnection();
        }
        return quantity;
    }

}
