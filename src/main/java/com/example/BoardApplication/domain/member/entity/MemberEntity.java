package com.example.BoardApplication.domain.member.entity;

import com.example.BoardApplication.domain.member.dto.MemberDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "member_table")
public class MemberEntity {
    @Id //pk 정의
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement
    private Long id;

    @Column(unique = true) // unique 제약 조건 추가
    private String memberId;

    @Column
    private String memberPassWord;

    @Column
    private String memberName;



    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(memberDTO.getMemberId());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPassWord(memberDTO.getMemberPassword());
        return memberEntity;
    }

}
