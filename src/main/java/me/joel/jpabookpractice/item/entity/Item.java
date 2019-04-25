package me.joel.jpabookpractice.item.entity;

import lombok.Getter;
import lombok.Setter;
import me.joel.jpabookpractice.entity.BaseEntity;
import me.joel.jpabookpractice.entity.Category;
import me.joel.jpabookpractice.exception.NotEnoughStockException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Date : 19. 4. 2
 * author : joel
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "itemType")
@Getter @Setter
public class Item extends BaseEntity {

    @Id @GeneratedValue
    private Long itemId;

    @ManyToMany(mappedBy = "itemList")
    private List<Category> categoryList = new ArrayList<>();

    private String name; // 이름
    private int price;   // 가격
    private int stockQuantity; // 재고수량


    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int resetStock = this.stockQuantity - quantity;

        if (resetStock < 0) {
            throw new NotEnoughStockException("need more stock");
        } else {
            this.stockQuantity = resetStock;
        }
    }
}
