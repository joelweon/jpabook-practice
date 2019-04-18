package me.joel.jpabookpractice.order.entity;

import lombok.Getter;
import lombok.Setter;
import me.joel.jpabookpractice.entity.OrderStatus;

/**
 * Date : 19. 4. 17
 * author : joel
 */

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
