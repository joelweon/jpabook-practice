package me.joel.jpabookpractice.entity;

import lombok.Getter;
import lombok.Setter;
import me.joel.jpabookpractice.order.entity.Orders;

import javax.persistence.*;

/**
 * Date : 19. 4. 4
 * author : joel
 */

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    private Long deliveryId;

    @OneToOne(mappedBy = "delivery") // 주문에서 배송으로 자주 접근할 예정이니 외래키는 주문키에 둔다.(mappedBy로 주인 아님 표시)
    private Orders orders;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public Delivery() {}

    public Delivery(Address address) {
        this.address = address;
    }

}
