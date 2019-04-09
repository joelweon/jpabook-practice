package me.joel.jpabookpractice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * Date : 19. 4. 9
 * author : joel
 */

@Embeddable
@Getter @Setter
public class Address {

    private String city;

    private String street;

    private String zipcode;
}
