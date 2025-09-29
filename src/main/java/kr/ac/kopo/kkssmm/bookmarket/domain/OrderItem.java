package kr.ac.kopo.kkssmm.bookmarket.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;
    private String bookId;
    private String quantity;
    private BigDecimal totalPrice;
}
