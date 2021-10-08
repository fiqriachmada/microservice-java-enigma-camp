package com.enigma.customer.service;

import com.enigma.customer.entity.Member;
import com.enigma.customer.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member getMemberById(String id) {
        return memberRepository.findById(id).get();
    }
}
