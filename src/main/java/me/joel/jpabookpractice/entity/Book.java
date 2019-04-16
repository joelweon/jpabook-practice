package me.joel.jpabookpractice.entity;

import lombok.Getter;
import lombok.Setter;
import me.joel.jpabookpractice.item.entity.Item;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

/**
 * Date : 19. 4. 8
 * author : joel
 */

@Entity
@DiscriminatorColumn(name = "B")
@Getter @Setter
public class Book extends Item {
    private String author; // 저자
    private String isbn;   // ISBN
}
