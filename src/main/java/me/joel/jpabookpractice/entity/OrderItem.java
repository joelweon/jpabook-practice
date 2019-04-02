package me.joel.jpabookpractice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Date : 19. 4. 2
 * author : joel
 */

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    private Long orderItemId;

    private Long itemId;

    private Long orderId;

    private int orderPrice; // 주문가격
    private int count; // 주문수량


}
