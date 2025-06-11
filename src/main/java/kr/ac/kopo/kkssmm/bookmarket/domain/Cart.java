package kr.ac.kopo.kkssmm.bookmarket.domain;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@ToString
public class Cart {
    private String cartId;
    private Map<String, cartItem> cartItems;
    private BigDecimal grandTotal;

    public Cart(){
        cartItems = new HashMap<String, cartItem>();
        grandTotal = new BigDecimal(0); // BigDecimal.ZERO;
    }

    public Cart(String cartId) {
        this();
        this.cartId = cartId;
    }
}
