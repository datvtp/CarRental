/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datvtp.actions;

import datvtp.daos.Tbl_DiscountDAO;
import datvtp.daos.Tbl_InvoiceDAO;
import datvtp.daos.Tbl_InvoiceDetailDAO;
import datvtp.dtos.Tbl_InvoiceDTO;
import datvtp.dtos.Tbl_InvoiceDetailDTO;
import datvtp.models.Cart;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author vanth
 */
public class UserPayAction {

    private static final Logger logger = Logger.getLogger(UserPayAction.class);

    private static final String SUCCESS = "success";
    private static final String INVALID = "invalid";

    public UserPayAction() {
    }

    public String execute() {
        String url = INVALID;
        try {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            Cart shoppingCart = (Cart) session.getAttribute("CART");
            String email = (String) session.getAttribute("EMAIL");
            String name = (String) session.getAttribute("NAME");

            if (!shoppingCart.getShoppingCart().isEmpty()) {
                Tbl_InvoiceDAO invoiceDAO = new Tbl_InvoiceDAO();
                Tbl_InvoiceDetailDAO invoiceDetailDAO = new Tbl_InvoiceDetailDAO();
                Tbl_DiscountDAO discountDAO = new Tbl_DiscountDAO();
                boolean valid = true;

                String discountCode = request.getParameter("discount");
                int discountPercent = 0;
                if (!discountCode.isEmpty()) {
                    if (discountDAO.checkDiscountOfUser(email) == 0) {
                        discountPercent = discountDAO.getDiscountPercent(discountCode);
                    } else {
                        discountPercent = discountDAO.getDiscountPercentByEmail(discountCode, email);
                    }

                    if (discountPercent == 0) {
                        valid = false;
                        request.setAttribute("INVALID_DISCOUNT", "Invalid discount code.");
                        url = INVALID;
                    }
                }

                List<Tbl_InvoiceDetailDTO> listCarOutStock = new ArrayList<>();
                for (Tbl_InvoiceDetailDTO dto : shoppingCart.getShoppingCart().values()) {
                    if (invoiceDetailDAO.checkNewCar(dto.getCarID(), dto.getRentalDate(), dto.getReturnDate()) == 0) {
                        if (invoiceDetailDAO.checkRentingForNewCar(dto.getCarID(), dto.getQuantity()) < 0) {
                            valid = false;
                            listCarOutStock.add(dto);
                            url = INVALID;
                        }
                    } else {
                        if (invoiceDetailDAO.checkRenting(dto.getCarID(), dto.getRentalDate(), dto.getReturnDate(), dto.getQuantity()) < 0) {
                            valid = false;
                            listCarOutStock.add(dto);
                            url = INVALID;
                        }
                    }
                }

                if (valid) {
                    if (discountCode.isEmpty()) {
                        if (invoiceDAO.insert(shoppingCart.getTotal(), email, discountDAO.getDiscountID())) {
                            int invoiceID = invoiceDAO.getLastInvoiceId();
                            for (Tbl_InvoiceDetailDTO dto : shoppingCart.getShoppingCart().values()) {
                                invoiceDetailDAO.insert(dto, invoiceID);
                            }
                            Tbl_InvoiceDTO invoiceDTO = invoiceDAO.findInvoiceByID(invoiceID);
                            request.setAttribute("INVOICE_DTO", invoiceDTO);
                        }
                    } else {
                        int totalPrice = shoppingCart.getTotal() * (100 - discountPercent) / 100;
                        if (invoiceDAO.insert(totalPrice, email, discountDAO.getDiscountID())) {
                            int invoiceID = invoiceDAO.getLastInvoiceId();
                            for (Tbl_InvoiceDetailDTO dto : shoppingCart.getShoppingCart().values()) {
                                invoiceDetailDAO.insert(dto, invoiceID);
                            }
                            Tbl_InvoiceDTO invoiceDTO = invoiceDAO.findInvoiceByID(invoiceID);
                            request.setAttribute("INVOICE_DTO", invoiceDTO);
                        }
                    }
                    Cart newCart = new Cart(name);
                    session.setAttribute("CART", newCart);
                    session.setAttribute("COUNT", 0);
                    url = SUCCESS;
                } else {
                    request.setAttribute("LIST_CAR_OUT_STOCK", listCarOutStock);
                }
            }
        } catch (SQLException e) {
            logger.error("ERROR SQL at UserPayAction: " + e.getMessage());
        } catch (NamingException e) {
            logger.error("ERROR Naming at UserPayAction: " + e.getMessage());
        } catch (ParseException e) {
            logger.error("ERROR Parse at UserPayAction: " + e.getMessage());
        }
        return url;
    }

}
