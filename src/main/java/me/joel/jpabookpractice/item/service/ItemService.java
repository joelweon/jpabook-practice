package me.joel.jpabookpractice.item.service;

import me.joel.jpabookpractice.item.entity.Item;
import me.joel.jpabookpractice.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Date : 19. 4. 16
 * author : joel
 */

@Service
@Transactional
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public void save(Item item) {
        itemRepository.save(item);
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public List<Item> findItemList() {
        return itemRepository.findAll();
    }
}
