package me.joel.jpabookpractice.item.repository;

import me.joel.jpabookpractice.item.entity.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Date : 19. 4. 16
 * author : joel
 */

@Repository
public class ItemRepository {

    @PersistenceContext
    EntityManager em;

    // insert , update 기능
    public void save(Item item) {
        // 만일 @GeneratedValue 를 선언 안했으면 식별자 값을 따로 세팅해야한다.
        if (item.getItemId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long itemId) {
        return em.find(Item.class, itemId);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
