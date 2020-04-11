package datvtp.daos;

import datvtp.dtos.Tbl_CategoryDTO;
import datvtp.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

public class Tbl_CategoryDAO implements Serializable {

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

    public List<Tbl_CategoryDTO> getAllCategory() throws SQLException, NamingException {
        List<Tbl_CategoryDTO> list = new ArrayList<>();
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT CategoryID, CategoryName FROM tbl_Category";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int categoryID = rs.getInt("CategoryID");
                String categoryName = rs.getString("CategoryName");

                list.add(new Tbl_CategoryDTO(categoryID, categoryName));
            }
        } finally {
            closeConnection();
        }
        return list;
    }
    
    public String findCategoryNameByID(int categoryID) throws SQLException, NamingException {
        String name = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT CategoryName FROM tbl_Category WHERE CategoryID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryID);
            rs = ps.executeQuery();

            if (rs.next()) {
                name = rs.getString("CategoryName");
            }
        } finally {
            closeConnection();
        }
        return name;
    }
}
