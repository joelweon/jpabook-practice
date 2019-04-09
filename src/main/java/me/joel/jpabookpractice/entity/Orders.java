package me.joel.jpabookpractice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Date : 19. 4. 2
 * author : joel
 */

@Entity
@Getter @Setter
public class Orders extends BaseEntity {

    @Id @GeneratedValue
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "memberId") //생략하면 member_member_id 로 컬럼 생성됨.
    private Member member; // 주문회원

    @OneToMany(mappedBy = "orders")
    private List<OrderItem> orderItemList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "deliveryId")  // 주문에서 배송으로 자주 접근할 예정이니 외래키는 주문키에 둔다.(Orders.delivery -> 주인)
    private Delivery delivery; // 배송정보

    //     @Temporal(TemporalType.TIMESTAMP) 기본값이라 생략가능
    private Date orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태



    /*
    연관관계 메서드
    회원을 수정하면 회원이 갖고있는 주문내역에서 삭제를하고
    회원을 수정한 뒤
    수정 된 회원에게 주문내역을 넣어준다.
    */
    public void setMember(Member member) {
        // 기존 관계 제거
        if (this.member != null) {
            this.member.getOrdersList().remove(this);
        }
        this.member = member;
        member.getOrdersList().add(this);
    }

    /*
    Orders는 OrderItem 과의 관계에서 연관관계의 주인이 아니기 때문에
    OrderItem 엔티티를 추가 할 수 없다.
    그래서 이 메서드를 통해 간접적으로 OrderItem 엔티티도 추가가 가능하게 했다.
    */
    public void addOrderItem(OrderItem orderItem) {
        orderItemList.add(orderItem);
        orderItem.setOrders(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrders(this);
    }
}
