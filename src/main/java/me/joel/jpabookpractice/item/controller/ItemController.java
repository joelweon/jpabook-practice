package me.joel.jpabookpractice.item.controller;

import me.joel.jpabookpractice.entity.Book;
import me.joel.jpabookpractice.item.entity.Item;
import me.joel.jpabookpractice.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Date : 19. 4. 19
 * author : joel
 */

@Controller
@RequestMapping("/item")
public class ItemController implements WebMvcConfigurer {

    @Autowired
    ItemService itemService;

    @GetMapping("")
    public String list(Model model) {
        List<Item> itemList = itemService.findItemList();
        model.addAttribute("itemList", itemList);
        return "item/itemList";
    }

    @GetMapping("/new")
    public String createForm() {
        return "item/createItemForm";
    }

    @PostMapping("/new")
    public String create(Book item) {
        itemService.save(item);
        return "redirect:/item";
    }

}
