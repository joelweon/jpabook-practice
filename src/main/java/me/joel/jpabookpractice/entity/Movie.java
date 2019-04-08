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
@DiscriminatorColumn(name = "M")
@Getter @Setter
public class Movie extends Item {

    private String director;
    private String actor;

}
