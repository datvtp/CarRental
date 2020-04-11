package datvtp.dtos;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tbl_InvoiceDetailDTO implements Serializable {

    private int id;
    private int carID;
    private String carName;
    private String categoryName;
    private String rentalDate;
    private String returnDate;
    private int price;
    private int quantity;

    public Tbl_InvoiceDetailDTO() {
    }

    public Tbl_InvoiceDetailDTO(int id, int carID, String carName, String categoryName, String rentalDate, String returnDate, int price, int quantity) throws ParseException {
        this.id = id;
        this.carID = carID;
        this.carName = carName;
        this.categoryName = categoryName;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
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

    public int calTotalPrice() throws ParseException {
        int result;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date checkInDate = sdf.parse(this.rentalDate);
        Date checkOutDate = sdf.parse(this.returnDate);
        long timeByMiSecond = checkOutDate.getTime() - checkInDate.getTime();
        int numOfDay = Math.round(timeByMiSecond / (24 * 3600 * 1000));
        result = this.price * this.quantity * numOfDay;
        return result;
    }

}
