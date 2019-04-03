package me.joel.jpabookpractice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Date : 19. 4. 2
 * author : joel
 */

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long memberId;

    private String name;

    private String city;

    private String street;

    private String zipcode;

    @OneToMany(mappedBy = "member")
    private List<Orders> ordersList = new ArrayList<>();
}
