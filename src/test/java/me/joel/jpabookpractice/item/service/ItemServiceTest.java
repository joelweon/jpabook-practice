package me.joel.jpabookpractice.item.service;

import me.joel.jpabookpractice.item.entity.Item;
import me.joel.jpabookpractice.item.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Date : 19. 4. 16
 * author : joel
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 상품등록() {

        Item item = new Item();
        item.setName("item1");
        item.setPrice(1000);

        itemService.save(item);

        assertEquals(item, itemRepository.findOne(item.getItemId()));
    }

    @Test
    public void 상품수정() {
        Item item = new Item();
        item.setName("item1");
        item.setPrice(1000);

        itemService.save(item);

        Item item2 = new Item();
        item2.setItemId(item.getItemId());
        item2.setPrice(2000);
        itemService.save(item2);

        assertEquals(item.getPrice(),2000);
    }
}