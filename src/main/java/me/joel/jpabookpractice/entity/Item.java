package me.joel.jpabookpractice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Date : 19. 4. 2
 * author : joel
 */

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    private Long itemId;

    @ManyToMany(mappedBy = "itemList")
    private List<Category> categoryList = new ArrayList<>();

    private String name; // 이름
    private int price;   // 가격
    private int stockQuantity; // 재고수량

}
