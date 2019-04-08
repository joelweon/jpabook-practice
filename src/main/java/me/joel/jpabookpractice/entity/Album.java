package me.joel.jpabookpractice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

/**
 * Date : 19. 4. 8
 * author : joel
 */

@Entity
@DiscriminatorColumn(name = "A")
@Getter @Setter
public class Album extends Item {
    private String artist;
    private String etc;
}
