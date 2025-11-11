package kr.ac.kopo.kkssmm.bookmarket.repository;

import kr.ac.kopo.kkssmm.bookmarket.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProRepository extends JpaRepository<Order, Long> {

}