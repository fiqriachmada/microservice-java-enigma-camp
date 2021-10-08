package com.enigma.customer.controller;

import com.enigma.customer.entity.Member;
import com.enigma.customer.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/members")
    Member createMember(@RequestBody Member member){

        return memberService.addMember(member);
    }

    @GetMapping("/members/{id}")
    Member getMemberById(@PathVariable String id){
        return memberService.getMemberById(id);
    }
}
