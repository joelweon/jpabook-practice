package me.joel.jpabookpractice.order.service;

import me.joel.jpabookpractice.entity.Delivery;
import me.joel.jpabookpractice.entity.OrderItem;
import me.joel.jpabookpractice.item.entity.Item;
import me.joel.jpabookpractice.item.service.ItemService;
import me.joel.jpabookpractice.member.entity.Member;
import me.joel.jpabookpractice.member.repository.MemberRepository;
import me.joel.jpabookpractice.order.entity.OrderSearch;
import me.joel.jpabookpractice.order.entity.Orders;
import me.joel.jpabookpractice.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Date : 19. 4. 17
 * author : joel
 */

@Service
@Transactional
public class OrderService {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemService itemService;

    /**주문*/
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemService.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Orders orders = Orders.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(orders);

        return orders.getOrderId();
    }

    /**주문 취소*/
    public void cancelOrder(Long orderId) {
        Orders orders = orderRepository.findOne(orderId);
        orders.cancel();
    }

    /**주문 검색*/
    public List<Orders> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }


}
