package com.example.BoardApplication.domain.member.repository;

import com.example.BoardApplication.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // 아이디로 회원 정보 조회 (select * from member_table where member_email=?)
    Optional<MemberEntity> findByMemberId(String memberId);

}
