/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datvtp.actions;

import com.opensymphony.xwork2.ActionSupport;
import datvtp.models.Cart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author vanth
 */
public class UserUpdateCartAction extends ActionSupport {

    private static final Logger logger = Logger.getLogger(UserUpdateCartAction.class);

    @Override
    public void validate() {
        if (quantity.isEmpty()) {
            addFieldError("quantity", "Quantity can't be blank.");
        }
    }

    private int id;
    private String quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public UserUpdateCartAction() {
    }

    public String execute() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            Cart shoppingCart = (Cart) session.getAttribute("CART");

            shoppingCart.updateCart(id, Integer.parseInt(quantity));

            session.setAttribute("CART", shoppingCart);
        } catch (NumberFormatException e) {
            logger.error("ERROR NumberFormat at UserUpdateCartAction: " + e.getMessage());
        }
        return "success";
    }

}
