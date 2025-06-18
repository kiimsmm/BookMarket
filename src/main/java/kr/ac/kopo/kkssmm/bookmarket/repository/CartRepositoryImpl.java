package kr.ac.kopo.kkssmm.bookmarket.repository;

import kr.ac.kopo.kkssmm.bookmarket.domain.Cart;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CartRepositoryImpl implements CartRepository {
    private Map<String, Cart> listOfCarts;

    public CartRepositoryImpl() {
        listOfCarts = new HashMap<String, Cart>();
    }

    @Override
    public Cart create(Cart cart) {
        if (listOfCarts.containsKey(cart.getCartId())){
            throw new IllegalArgumentException("Cart("+cart.getCartId()+") already exists");
        }
        listOfCarts.put(cart.getCartId(), cart);
        return cart;
    }

    @Override
    public Cart read(String cartId) {
        return listOfCarts.get(cartId);
    }

    @Override
    public void update(String cartId, Cart cart) {
        if (!listOfCarts.containsKey(cartId)){
            throw new IllegalArgumentException("Cart("+cartId+") does not exist");
        }
        listOfCarts.put(cartId, cart);
    }
}
