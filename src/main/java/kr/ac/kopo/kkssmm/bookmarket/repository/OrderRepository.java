package kr.ac.kopo.kkssmm.bookmarket.repository;

import kr.ac.kopo.kkssmm.bookmarket.domain.Order;

public interface OrderRepository {
    // 주문목록
    Long saveOrder(Order order);
}
