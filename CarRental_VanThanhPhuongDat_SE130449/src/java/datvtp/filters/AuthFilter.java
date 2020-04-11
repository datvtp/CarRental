/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datvtp.filters;

import datvtp.daos.Tbl_CategoryDAO;
import datvtp.dtos.Tbl_CategoryDTO;
import datvtp.utils.DateTimeDataType;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author vanth
 */
public class AuthFilter implements Filter {

    private static final Logger logger = Logger.getLogger(AuthFilter.class);

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    private final List<String> guest;
    private final List<String> admin;
    private final List<String> user;

    public AuthFilter() {
        guest = new ArrayList<>();
        guest.add("");
        guest.add("''");
        guest.add("home.jsp");
        guest.add("error.jsp");
        guest.add("login.jsp");
        guest.add("register.jsp");
        guest.add("LoginAction");
        guest.add("LoginAction.action");
        guest.add("RegisterAction");
        guest.add("RegisterAction.action");
        guest.add("GuestSearchAction");
        guest.add("GuestSearchAction.action");

        admin = new ArrayList<>();
        admin.add("admin.jsp");
        admin.add("LogoutAction");
        admin.add("LogoutAction.action");
        admin.add("AdminSearchAction");
        admin.add("AdminSearchAction.action");

        user = new ArrayList<>();
        user.add("user.jsp");
        user.add("confirmEmail.jsp");
        user.add("ConfirmEmailAction");
        user.add("ConfirmEmailAction.action");
        user.add("LogoutAction");
        user.add("LogoutAction.action");
        user.add("UserSearchAction");
        user.add("UserSearchAction.action");
        user.add("userViewCart.jsp");
        user.add("UserAddToCartAction");
        user.add("UserAddToCartAction.action");
        user.add("UserUpdateCartAction");
        user.add("UserUpdateCartAction.action");
        user.add("UserDeleteCartAction");
        user.add("UserDeleteCartAction.action");
        user.add("UserPayAction");
        user.add("UserPayAction.action");
        user.add("userViewInvoice.jsp");
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        try {
            int index = uri.lastIndexOf("/");
            String resource = uri.substring(index + 1);

            HttpSession session = req.getSession();
            Tbl_CategoryDAO categoryDAO = new Tbl_CategoryDAO();
            List<Tbl_CategoryDTO> list = categoryDAO.getAllCategory();
            session.setAttribute("CATEGORY", list);

            if (resource.contains(".jpg") || resource.contains(".png") || resource.contains(".jpeg")) {
                chain.doFilter(request, response);
            } else if (guest.contains(resource)) {
                if (resource.equals("home.jsp") || resource.equals("") || resource.equals("''")) {
                    String url = "GuestSearchAction?";
                    url += "rentalDate=" + DateTimeDataType.getToday();
                    url += "&returnDate=" + DateTimeDataType.getTomorrow();
                    url += "&name=" + "";
                    url += "&category=" + "0";
                    url += "&amount=" + "0";
                    url += "&pageNumber=" + "1";
                    req.getRequestDispatcher(url).forward(request, response);
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                if (resource.equals("confirmEmail.jsp") || resource.equals("ConfirmEmailAction") || resource.equals("ConfirmEmailAction.action")) {
                    if (session.getAttribute("CODE") != null) {
                        chain.doFilter(request, response);
                    } else {
                        res.sendRedirect("login.jsp");
                    }
                } else {
                    if (session.getAttribute("ROLEID") != null) {
                        int roleID = Integer.parseInt(session.getAttribute("ROLEID").toString());
                        if (roleID == 1 && admin.contains(resource)) {
                            if (resource.equals("admin.jsp")) {
                                String url = "AdminSearchAction?";
                                url += "rentalDate=" + DateTimeDataType.getToday();
                                url += "&returnDate=" + DateTimeDataType.getTomorrow();
                                url += "&name=" + "";
                                url += "&category=" + "0";
                                url += "&amount=" + "0";
                                url += "&pageNumber=" + "1";
                                req.getRequestDispatcher(url).forward(request, response);
                            } else {
                                chain.doFilter(request, response);
                            }
                        } else if (roleID == 2 && user.contains(resource)) {
                            if (resource.equals("user.jsp")) {
                                String url = "UserSearchAction?";
                                url += "rentalDate=" + DateTimeDataType.getToday();
                                url += "&returnDate=" + DateTimeDataType.getTomorrow();
                                url += "&name=" + "";
                                url += "&category=" + "0";
                                url += "&amount=" + "0";
                                url += "&pageNumber=" + "1";
                                req.getRequestDispatcher(url).forward(request, response);
                            } else {
                                chain.doFilter(request, response);
                            }
                        } else {
                            res.sendRedirect("login.jsp");
                        }
                    } else {
                        res.sendRedirect("login.jsp");
                    }

                }
            }
        } catch (IOException e) {
            logger.error("ERROR IO at AuthFilter: " + e.getMessage());
        } catch (NumberFormatException e) {
            logger.error("ERROR NumberFormat at AuthFilter: " + e.getMessage());
        } catch (ServletException e) {
            logger.error("ERROR Servlet at AuthFilter: " + e.getMessage());
        } catch (SQLException e) {
            logger.error("ERROR SQL at AuthFilter: " + e.getMessage());
        } catch (NamingException e) {
            logger.error("ERROR Naming at AuthFilter: " + e.getMessage());
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
