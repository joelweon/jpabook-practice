package me.joel.jpabookpractice.order.controller;

import me.joel.jpabookpractice.item.entity.Item;
import me.joel.jpabookpractice.item.service.ItemService;
import me.joel.jpabookpractice.member.entity.Member;
import me.joel.jpabookpractice.member.service.MemberService;
import me.joel.jpabookpractice.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Date : 19. 4. 24
 * author : joel
 */

@Controller
public class OrderController {

    @Autowired
    MemberService memberService;

    @Autowired
    ItemService itemService;

    @Autowired
    OrderService orderService;

    @GetMapping("/order/new")
    public String createOrderForm(Model model) {

        List<Member> memberList = memberService.findMemberList();
        List<Item> itemList = itemService.findItemList();

        model.addAttribute("memberList", memberList);
        model.addAttribute("itemList", itemList);

        return "/order/createOrderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam Long memberId, @RequestParam Long itemId, @RequestParam int count) {

        orderService.order(memberId, itemId, count);

        return "redirect:/";
    }

}
