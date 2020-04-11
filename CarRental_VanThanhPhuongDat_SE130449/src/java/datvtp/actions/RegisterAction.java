/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datvtp.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import datvtp.daos.Tbl_UserDAO;
import datvtp.utils.DateTimeDataType;
import datvtp.utils.MailUtils;
import datvtp.utils.RandomCode;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author vanth
 */
public class RegisterAction extends ActionSupport {

    private static final Logger logger = Logger.getLogger(RegisterAction.class);

    private static final String CONFIRM_EMAIL = "confirm_email";
    private static final String ERROR = "error";

    @Override
    public void validate() {
        // check email
        Tbl_UserDAO userDAO = new Tbl_UserDAO();
        try {
            if (email.isEmpty()) {
                addFieldError("email", "Email can't be blank.");
            } else if (!email.matches("\\w+@\\w+[.]\\w+([.]\\w+)?") || email.length() > 50) {
                addFieldError("email", "Email must be valid and <= 50 chars.");
            } else if (userDAO.checkEmailExist(email) != null) {
                addFieldError("email", "Email is existed.");
            }
        } catch (SQLException e) {
            logger.error("ERROR SQL at RegisterAction: " + e.getMessage());
        } catch (NamingException e) {
            logger.error("ERROR Naming at RegisterAction: " + e.getMessage());
        }

        // check password
        if (password.isEmpty()) {
            addFieldError("password", "Password can't be blank.");
        } else if (!password.matches("^[a-zA-Z0-9]{5,20}$")) {
            addFieldError("password", "Password must be 5-20 characters and mustn't have space or special chars.");
        }

        // check confirm
        if (!confirm.matches(password)) {
            addFieldError("confirm", "Confirm must match password.");
        }

        // check name
        if (name.isEmpty()) {
            addFieldError("name", "Name can't be blank.");
        } else if (name.length() > 50) {
            addFieldError("name", "Name must be <= 50 chars.");
        }

        // check phone
        if (phone.isEmpty()) {
            addFieldError("phone", "Phone can't be blank.");
        } else if (!phone.matches("^\\d+$") || phone.length() != 10) {
            addFieldError("phone", "Phone must be number with 10 digits.");
        }

        // check address
        if (address.isEmpty()) {
            addFieldError("address", "Address can't be blank.");
        } else if (address.length() > 50) {
            addFieldError("address", "Address must be <= 50 chars.");
        }
    }

    private String email, password, confirm, name, phone, address;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RegisterAction() {
    }

    public String execute() {
        String url = ERROR;
        try {
            Tbl_UserDAO userDAO = new Tbl_UserDAO();
            HttpServletRequest request = ServletActionContext.getRequest();
            if (userDAO.insert(email, phone, name, address, DateTimeDataType.getTimeNow(), password)) {
                String code = RandomCode.getRandomCode();
                Map session = ActionContext.getContext().getSession();
                session.put("EMAIL", email);
                session.put("NAME", name);
                session.put("CODE", code);
                MailUtils.sendCodeToMemberMail(code, email);
                url = CONFIRM_EMAIL;
            } else {
                request.setAttribute("ERROR", "Create account failed.");
            }
        } catch (SQLException e) {
            logger.error("ERROR SQL at RegisterAction: " + e.getMessage());
        } catch (NamingException e) {
            logger.error("ERROR Naming at RegisterAction: " + e.getMessage());
        }

        return url;
    }

}
