package me.joel.jpabookpractice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


/**
 * Date : 19. 4. 2
 * author : joel
 */

@Entity
@Getter @Setter
public class Orders {

    @Id @GeneratedValue
    private Long orderId;

//    @Column(name="MEMBER_ID") 매핑 기본 전략을 사용해서 생략가능
    private Long memberId;

//     @Temporal(TemporalType.TIMESTAMP) 기본값이라 생략가능
    private Date orderDate; // 주문 날짜
//
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태
}
