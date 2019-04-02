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
public class Item {

    @Id @GeneratedValue
    private Long itemId;

    private String name; // 이름
    private int price;   // 가격
    private int stockQuantity; // 재고수량

}
