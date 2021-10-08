package com.enigma.customer.service;

import com.enigma.customer.entity.Member;

public interface MemberService {
    Member addMember(Member member);
    Member getMemberById(String id);
}
