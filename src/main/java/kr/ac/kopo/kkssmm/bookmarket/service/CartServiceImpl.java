package kr.ac.kopo.kkssmm.bookmarket.service;

import kr.ac.kopo.kkssmm.bookmarket.domain.Cart;
import kr.ac.kopo.kkssmm.bookmarket.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart create(Cart cart) {
        return cartRepository.create(cart);
    }

    @Override
    public Cart read(String cartId) {
        return cartRepository.read(cartId);
    }

    @Override
    public void update(String cartId, Cart cart) {
        cartRepository.update(cartId, cart);
    }

    @Override
    public void delete(String cartId) {
        cartRepository.delete(cartId);
    }

    @Override
    public Cart validateCart(String cartId) {
        if (cartId == null || cartId.trim().isEmpty()) {
            throw new IllegalArgumentException("Cart ID must not be null or empty");
        }

        Cart cart = cartRepository.read(cartId);
        if (cart == null) {
            throw new IllegalArgumentException("Cart with ID (" + cartId + ") does not exist");
        }

        return cart;
    }
}