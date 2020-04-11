package datvtp.dtos;

import java.io.Serializable;

public class Tbl_CategoryDTO implements Serializable {

    private int categoryID;
    private String categoryName;

    public Tbl_CategoryDTO() {
    }

    public Tbl_CategoryDTO(int categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
