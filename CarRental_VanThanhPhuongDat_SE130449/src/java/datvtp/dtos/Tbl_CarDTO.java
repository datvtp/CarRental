package datvtp.dtos;

import java.io.Serializable;

public class Tbl_CarDTO implements Serializable {

    private int carID;
    private String image;
    private String name;
    private String color;
    private int year;
    private int price;
    private int quantity;
    private int categoryID;
    private String categoryName;

    public Tbl_CarDTO() {
    }

    public Tbl_CarDTO(int carID, String image, String name, String color, int year, int price, int quantity, int categoryID) {
        this.carID = carID;
        this.image = image;
        this.name = name;
        this.color = color;
        this.year = year;
        this.price = price;
        this.quantity = quantity;
        this.categoryID = categoryID;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
