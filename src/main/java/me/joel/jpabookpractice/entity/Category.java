package me.joel.jpabookpractice.entity;

import lombok.Getter;
import lombok.Setter;
import me.joel.jpabookpractice.item.entity.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Date : 19. 4. 4
 * author : joel
 */

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    private Long categoryId;

    private String name;

    @ManyToMany
    // @JoinTable 생략하면 기본 네이밍으로 생성 됨.
    // 테이블 : CATEGORY_ITEM_LIST
    // 컬럼 : CATEGORY_CATEGORY_ID, ITEM_LIST_ITEM_ID
    @JoinTable(name = "categoryItem",
        joinColumns = @JoinColumn(name = "categoryId"),
        inverseJoinColumns = @JoinColumn(name = "itemId"))
    private List<Item> itemList = new ArrayList<>();

    /*카테고리의 계층구조를 위한 필드들*/
    @ManyToOne
    @JoinColumn(name = "categoryParentId")
    private Category categoryParent;

    @OneToMany(mappedBy = "categoryParent")
    private List<Category> categoryChild = new ArrayList<>();

    /*연관관계 메소드*/
    public void addChildCategory(Category child) {
        this.categoryChild.add(child);
        child.setCategoryParent(this);
    }

    public void addItem(Item item) {
        itemList.add(item);
    }

}
