package datvtp.models;

import datvtp.dtos.Tbl_InvoiceDetailDTO;
import java.io.Serializable;
import java.text.ParseException;
import java.util.HashMap;

public class Cart implements Serializable {

    private String username;
    private HashMap<Integer, Tbl_InvoiceDetailDTO> shoppingCart;

    public Cart(String username) {
        this.username = username;
        this.shoppingCart = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HashMap<Integer, Tbl_InvoiceDetailDTO> getShoppingCart() {
        return shoppingCart;
    }

    public void addToCart(Tbl_InvoiceDetailDTO dto) {
        this.shoppingCart.put(dto.getId(), dto);
    }

    public void removeCart(int id) {
        if (this.shoppingCart.containsKey(id)) {
            this.shoppingCart.remove(id);
        }
    }

    public void updateCart(int id, int quantity) {
        if (this.shoppingCart.containsKey(id)) {
            this.shoppingCart.get(id).setQuantity(quantity);
        }
    }

    public int getTotal() throws ParseException {
        int result = 0;
        for (Tbl_InvoiceDetailDTO dto : this.shoppingCart.values()) {
            result += dto.calTotalPrice();
        }
        return result;
    }
}
