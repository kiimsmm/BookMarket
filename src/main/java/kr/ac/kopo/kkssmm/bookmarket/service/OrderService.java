package kr.ac.kopo.kkssmm.bookmarket.service;

import kr.ac.kopo.kkssmm.bookmarket.domain.Order;

public interface OrderService {
    void confirmOrder(String bookId, long quantity); // 재고 수량
    Long saveOrder(Order order);
}
