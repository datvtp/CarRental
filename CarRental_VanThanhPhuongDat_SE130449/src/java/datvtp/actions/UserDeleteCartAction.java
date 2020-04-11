/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datvtp.actions;

import datvtp.models.Cart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author vanth
 */
public class UserDeleteCartAction {

    private static final Logger logger = Logger.getLogger(UserDeleteCartAction.class);

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDeleteCartAction() {
    }

    public String execute() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            Cart shoppingCart = (Cart) session.getAttribute("CART");
            
            shoppingCart.removeCart(id);
            
            session.setAttribute("CART", shoppingCart);
        } catch (Exception e) {
            logger.error("ERROR at UserDeleteCartAction: " + e.getMessage());
        }
        return "success";
    }

}
