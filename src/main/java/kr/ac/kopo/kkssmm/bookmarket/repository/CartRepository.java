package kr.ac.kopo.kkssmm.bookmarket.repository;

import kr.ac.kopo.kkssmm.bookmarket.domain.Cart;

public interface CartRepository {
    Cart create(Cart cart);
    Cart read(String cartId);
    void update(String cartId, Cart cart);
}
