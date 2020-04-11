/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datvtp.actions;

import datvtp.dtos.Tbl_InvoiceDetailDTO;
import datvtp.models.Cart;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author vanth
 */
public class UserAddToCartAction {

    private static final Logger logger = Logger.getLogger(UserAddToCartAction.class);

    private int carID;
    private String carName;
    private String categoryName;
    private String rentalDate;
    private String returnDate;
    private int price;

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

    public UserAddToCartAction() {
    }

    public String execute() {
        try {
            if (carName != null && categoryName != null && rentalDate != null && returnDate != null) {
                HttpServletRequest request = ServletActionContext.getRequest();
                HttpSession session = request.getSession();
                Cart shoppingCart = (Cart) session.getAttribute("CART");
                int count = (int) session.getAttribute("COUNT");
                count++;
                boolean valid = true;

                Tbl_InvoiceDetailDTO dto = new Tbl_InvoiceDetailDTO(count, carID, carName, categoryName, rentalDate, returnDate, price, 1);
                for (Tbl_InvoiceDetailDTO dtoCart : shoppingCart.getShoppingCart().values()) {
                    if (dto.getCarID() == dtoCart.getCarID() && dto.getRentalDate().equals(dtoCart.getRentalDate()) && dto.getReturnDate().equals(dtoCart.getReturnDate())) {
                        valid = false;
                        shoppingCart.updateCart(dtoCart.getId(), dtoCart.getQuantity() + 1);
                    }
                }
                if (valid) {
                    shoppingCart.addToCart(dto);
                }

                session.setAttribute("CART", shoppingCart);
                session.setAttribute("COUNT", count);
            }
        } catch (ParseException e) {
            logger.error("ERROR Parse at UserAddToCartAction: " + e.getMessage());
        }
        return "success";
    }

}
