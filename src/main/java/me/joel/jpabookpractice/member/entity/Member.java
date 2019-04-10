package me.joel.jpabookpractice.member.entity;

import lombok.Getter;
import lombok.Setter;
import me.joel.jpabookpractice.entity.Address;
import me.joel.jpabookpractice.entity.BaseEntity;
import me.joel.jpabookpractice.entity.Orders;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Date : 19. 4. 2
 * author : joel
 */

@Entity
@Getter @Setter
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long memberId;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Orders> ordersList = new ArrayList<>();
}
