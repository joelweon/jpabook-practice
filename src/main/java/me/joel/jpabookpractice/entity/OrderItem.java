package me.joel.jpabookpractice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Date : 19. 4. 2
 * author : joel
 */

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item; // 주문 상품

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders orders; // 주문

    private int orderPrice; // 주문가격
    private int count; // 주문수량


}
