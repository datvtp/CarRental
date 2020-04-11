/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datvtp.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author vanth
 */
public class LogoutAction {

    private static final Logger logger = Logger.getLogger(LogoutAction.class);

    public LogoutAction() {
    }

    public String execute() {
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

        } catch (Exception e) {
            logger.error("ERROR at LogoutAction: " + e.getMessage());
        }
        return "success";
    }

}
