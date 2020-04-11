/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datvtp.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import datvtp.daos.Tbl_UserDAO;
import datvtp.models.Cart;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

/**
 *
 * @author vanth
 */
public class ConfirmEmailAction extends ActionSupport {

    private static final Logger logger = Logger.getLogger(ConfirmEmailAction.class);

    private static final String SUCCESS = "success";

    @Override
    public void validate() {
        Map session = ActionContext.getContext().getSession();
        String codeSend = (String) session.get("CODE");
        if (code.isEmpty()) {
            addFieldError("code", "Verify code can't be blank.");
        } else if (!code.equals(codeSend)) {
            addFieldError("code", "Verify code is invalid.");
        }
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ConfirmEmailAction() {
    }

    public String execute() {
        String url = SUCCESS;
        try {
            Tbl_UserDAO userDAO = new Tbl_UserDAO();
            Map session = ActionContext.getContext().getSession();
            String email = (String) session.get("EMAIL");
            userDAO.activeUser(email);
            session.put("ROLEID", 2);
            Cart shoppingCart = new Cart(userDAO.getName());
            int count = 0;
            session.put("CART", shoppingCart);
            session.put("COUNT", count);
        } catch (SQLException e) {
            logger.error("ERROR SQL at ConfirmEmailAction: " + e.getMessage());
        } catch (NamingException e) {
            logger.error("ERROR Naming at ConfirmEmailAction: " + e.getMessage());
        }

        return url;
    }

}
