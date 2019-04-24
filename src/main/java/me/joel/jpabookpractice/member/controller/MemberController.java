package me.joel.jpabookpractice.member.controller;

import me.joel.jpabookpractice.entity.Address;
import me.joel.jpabookpractice.member.entity.Member;
import me.joel.jpabookpractice.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Date : 19. 4. 24
 * author : joel
 */

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/member")
    public String memberList(Model model) {
        List<Member> memberList = memberService.findMemberList();

        model.addAttribute("memberList", memberList);
        return "/member/memberList";
    }

    @GetMapping("/member/new")
    public String createMemberForm() {
        return "/member/createMemberForm";
    }

    @PostMapping("/member/new")
    public String createMember(@ModelAttribute Member member, String city, String street, String zipcode) {
        Address address = new Address(city, street, zipcode);
        member.setAddress(address);
        memberService.join(member);
        return "redirect:/member";
    }
}
