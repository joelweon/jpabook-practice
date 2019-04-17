package me.joel.jpabookpractice.entity;

import lombok.Getter;
import lombok.Setter;
import me.joel.jpabookpractice.item.entity.Item;
import me.joel.jpabookpractice.order.entity.Orders;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item; // 주문 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Orders orders; // 주문

    private int orderPrice; // 주문가격
    private int count; // 주문수량

    /**생성 메서드*/
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);

        return orderItem;
    }

    /*비즈니스 로직*/
    /**주문취소*/
    public void cancel() {
        getItem().addStock(count);
    }

    /**주문상품 전체 가격 조회*/
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
