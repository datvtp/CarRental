package datvtp.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import datvtp.daos.Tbl_UserDAO;
import datvtp.models.Cart;
import datvtp.utils.CaptchaUtils;
import datvtp.utils.MailUtils;
import datvtp.utils.RandomCode;
import java.io.IOException;
import java.net.ProtocolException;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class LoginAction extends ActionSupport {

    private static final Logger logger = Logger.getLogger(LoginAction.class);

    private static final String ADMIN = "admin";
    private static final String USER = "user";
    private static final String CONFIRM_EMAIL = "confirm_email";
    private static final String ERROR = "error";
    private static final String INVALID = "invalid";

    @Override
    public void validate() {
        if (email.isEmpty()) {
            addFieldError("email", "Email can't be blank.");
        }
        if (password.isEmpty()) {
            addFieldError("password", "Password can't be blank.");
        }
    }

    private String email, password;

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

    public LoginAction() {
    }

    public String execute() {
        String url = ERROR;
        try {
            Tbl_UserDAO userDAO = new Tbl_UserDAO();
            int roleID = userDAO.checkLogin(email, password);
            HttpServletRequest request = ServletActionContext.getRequest();
            String ggCode = request.getParameter("g-recaptcha-response");
            if (!CaptchaUtils.verifyCaptcha(ggCode)){
                request.setAttribute("INVALID", "Invalid reCaptcha.");
                return INVALID;
            }
            if (roleID == 0) {
                request.setAttribute("INVALID", "Invalid Email or Password.");
                url = INVALID;
            } else {
                Map session = ActionContext.getContext().getSession();
                int statusID = userDAO.getStatusID();
                session.put("EMAIL", email);
                session.put("NAME", userDAO.getName());
                if (roleID == 1 && statusID == 2) {
                    session.put("ROLEID", roleID);
                    url = ADMIN;
                }
                if (roleID == 2 && statusID == 2) {
                    session.put("ROLEID", roleID);
                    Cart shoppingCart = new Cart(userDAO.getName());
                    int count = 0;
                    session.put("CART", shoppingCart);
                    session.put("COUNT", count);
                    url = USER;
                }
                if (roleID == 2 && statusID == 1) {
                    String code = RandomCode.getRandomCode();
                    session.put("CODE", code);
                    MailUtils.sendCodeToMemberMail(code, email);
                    url = CONFIRM_EMAIL;
                }
            }
        } catch (SQLException e) {
            logger.error("ERROR SQL at LoginAction: " + e.getMessage());
        } catch (NamingException e) {
            logger.error("ERROR Naming at LoginAction: " + e.getMessage());
        } catch (ProtocolException e) {
            logger.error("ERROR Protocol at LoginAction: " + e.getMessage());
        } catch (IOException e) {
            logger.error("ERROR IO at LoginAction: " + e.getMessage());
        }
        return url;
    }

}
