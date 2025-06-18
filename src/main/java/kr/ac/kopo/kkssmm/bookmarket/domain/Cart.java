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

    public void addCartItem(cartItem item){
        String bookId = item.getBook().getBookID();

        if(cartItems.containsKey(bookId)){
            cartItem cartItem = cartItems.get(bookId);
            cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
            cartItems.put(bookId, cartItem);
        } else {
            cartItems.put(bookId, item);
        }
        updateGrandTotal();
    }

    /* 주문 총액을 업데이트 */
    public void updateGrandTotal(){
        grandTotal = new BigDecimal(0);
        for(cartItem cartItem : cartItems.values()){
            grandTotal = grandTotal.add(cartItem.getTotalPrice());
        }
    }
}
