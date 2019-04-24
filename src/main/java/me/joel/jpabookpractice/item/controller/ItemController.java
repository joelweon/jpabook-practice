package me.joel.jpabookpractice.item.controller;

import me.joel.jpabookpractice.entity.Book;
import me.joel.jpabookpractice.item.entity.Item;
import me.joel.jpabookpractice.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Date : 19. 4. 19
 * author : joel
 */

@Controller
public class ItemController implements WebMvcConfigurer {

    @Autowired
    ItemService itemService;

    @GetMapping("/item")
    public String itemList(Model model) {
        List<Item> itemList = itemService.findItemList();
        model.addAttribute("itemList", itemList);
        return "item/itemList";
    }

    @GetMapping("/item/new")
    public String createItemForm() {
        return "item/createItemForm";
    }

    @PostMapping("/item/new")
    public String createItem(@ModelAttribute Book item) {
        itemService.save(item);
        return "redirect:/item";
    }

    @GetMapping("/item/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model) {
        Item item = itemService.findOne(itemId);
        model.addAttribute("item", item);

        return "item/updateItemForm";
    }

    @PostMapping("/item/{itemId}/edit")
    public String updateItem(@ModelAttribute("item") Book item) {
        itemService.save(item);
        return "redirect:/item";
    }

}
