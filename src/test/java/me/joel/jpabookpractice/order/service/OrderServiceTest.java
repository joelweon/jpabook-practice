package me.joel.jpabookpractice.order.service;

import me.joel.jpabookpractice.entity.Address;
import me.joel.jpabookpractice.entity.Book;
import me.joel.jpabookpractice.entity.OrderStatus;
import me.joel.jpabookpractice.exception.NotEnoughStockException;
import me.joel.jpabookpractice.item.entity.Item;
import me.joel.jpabookpractice.member.entity.Member;
import me.joel.jpabookpractice.order.entity.Orders;
import me.joel.jpabookpractice.order.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Date : 19. 4. 18
 * author : joel
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;


    @Test
    public void 상품주문() {

        //Given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); // 이름, 가격, 재고수량
        int orderCount = 2;

        //When
        Long orderId = orderService.order(member.getMemberId(), item.getItemId(), orderCount);

        //Then
        Orders getOrder = orderRepository.findOne(orderId);
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItemList().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * 2, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, item.getStockQuantity());

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }


    @Test(expected = NotEnoughStockException.class)
    public void 재고수량초과() {

        //Given
        Member member = createMember();
        Item item = createBook("어린왕자", 10000, 10);

        //When
        orderService.order(member.getMemberId(), item.getItemId(), 11);

        //Then
        fail("");

    }

    @Test
    public void 주문취소() {

        //Given
        Member member = createMember();
        Item item = createBook("어린왕자", 10000, 10);

        //When
        Long orderId = orderService.order(member.getMemberId(), item.getItemId(), 5);
        orderService.cancelOrder(orderId);

        //Then
        Orders getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 상태는 CANCEL이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuantity());
    }

}