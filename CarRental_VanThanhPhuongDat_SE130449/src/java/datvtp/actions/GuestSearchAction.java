/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datvtp.actions;

import com.opensymphony.xwork2.ActionSupport;
import datvtp.daos.Tbl_CarDAO;
import datvtp.daos.Tbl_CategoryDAO;
import datvtp.dtos.Tbl_CarDTO;
import datvtp.utils.DateTimeDataType;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

/**
 *
 * @author vanth
 */
public class GuestSearchAction extends ActionSupport {

    private static final Logger logger = Logger.getLogger(GuestSearchAction.class);

    @Override
    public void validate() {
        try {
            if (!DateTimeDataType.checkAfterToday(rentalDate)) {
                addFieldError("returnDate", "Rental date can't be past.");
            } else if (!DateTimeDataType.checkDateInOut(rentalDate, returnDate)) {
                addFieldError("returnDate", "Rental date & return date must be valid.");
            }
        } catch (Exception e) {
            logger.error("ERROR at GuestSearchAction: " + e.getMessage());
        }
    }

    private String name;
    private int category;
    private String rentalDate;
    private String returnDate;
    private String amount;
    private int pageNumber;
    private List<Tbl_CarDTO> list;
    private int numberOfPage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<Tbl_CarDTO> getList() {
        return list;
    }

    public void setList(List<Tbl_CarDTO> list) {
        this.list = list;
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public GuestSearchAction() {
    }

    public String execute() {
        try {
            if (amount.isEmpty()) {
                amount = "0";
            }

            Tbl_CarDAO carDAO = new Tbl_CarDAO();
            Tbl_CategoryDAO categoryDAO = new Tbl_CategoryDAO();
            int numberRecord = 20;
            int totalRecord = carDAO.getNumberOfCar(name, category, rentalDate, returnDate, Integer.parseInt(amount));
            numberOfPage = totalRecord / numberRecord; //
            if (totalRecord > numberOfPage * numberRecord) { //
                numberOfPage += 1;
            }

            int offsetRecord = (pageNumber - 1) * numberRecord; //
            list = carDAO.getListCar(name, category, rentalDate, returnDate, Integer.parseInt(amount), offsetRecord, numberRecord); //

            for (Tbl_CarDTO dto : list) {
                if (dto.getQuantity() == 0) {
                    int quantity = carDAO.findQuantityByCarID(dto.getCarID());
                    dto.setQuantity(quantity);

                }
                String categoryName = categoryDAO.findCategoryNameByID(dto.getCategoryID());
                dto.setCategoryName(categoryName);
            }

        } catch (NumberFormatException e) {
            logger.error("ERROR NumberFormat at GuestSearchAction: " + e.getMessage());
        } catch (SQLException e) {
            logger.error("ERROR SQL at GuestSearchAction: " + e.getMessage());
        } catch (NamingException e) {
            logger.error("ERROR Naming at GuestSearchAction: " + e.getMessage());
        }
        return "success";
    }

}
