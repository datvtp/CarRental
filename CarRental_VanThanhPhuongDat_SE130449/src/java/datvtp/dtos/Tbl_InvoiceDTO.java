package datvtp.dtos;

import java.io.Serializable;

public class Tbl_InvoiceDTO implements Serializable {

    private int invoiceID;
    private String createTime;
    private int totalPrice;
    private String email;
    private int discountID;
    private int statusID;

    public Tbl_InvoiceDTO() {
    }

    public Tbl_InvoiceDTO(int invoiceID, String createTime, int totalPrice, String email, int discountID, int statusID) {
        this.invoiceID = invoiceID;
        this.createTime = createTime;
        this.totalPrice = totalPrice;
        this.email = email;
        this.discountID = discountID;
        this.statusID = statusID;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

}
