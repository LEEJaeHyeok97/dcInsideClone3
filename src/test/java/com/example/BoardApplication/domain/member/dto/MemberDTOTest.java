package com.example.BoardApplication.domain.member.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberDTOTest {

    @Test
    void toMemberDTO() {

    }

    @Test
    void getId() {

    }

    @Test
    void getMemberId() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberName("SON");
        memberDTO.setMemberId("1");

        MemberDTO memberDTO2 = new MemberDTO();
        memberDTO2.setMemberName("Park");
        memberDTO2.setMemberId("2");

        Assertions.assertThat(memberDTO.getMemberId()).isEqualTo(memberDTO2.getMemberId());
    }

    @Test
    void getMemberName() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberName("SON");
        memberDTO.setMemberId("1");

        MemberDTO memberDTO2 = new MemberDTO();
        memberDTO2.setMemberName("SON");
        memberDTO2.setMemberId("2");

        Assertions.assertThat(memberDTO.getMemberName()).isEqualTo(memberDTO2.getMemberName());
    }
}