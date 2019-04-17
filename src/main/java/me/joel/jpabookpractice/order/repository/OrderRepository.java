package me.joel.jpabookpractice.order.repository;

import me.joel.jpabookpractice.order.entity.OrderSearch;
import me.joel.jpabookpractice.order.entity.Orders;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Date : 19. 4. 17
 * author : joel
 */

@Repository
public class OrderRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Orders orders) {
        em.persist(orders);
    }

    public Orders findOne(Long orderId) {
        return em.find(Orders.class, orderId);
    }

    public List<Orders> findAll(OrderSearch orderSearch) {
        // 다음에 버전에서 개발
        return null;
    }
}
