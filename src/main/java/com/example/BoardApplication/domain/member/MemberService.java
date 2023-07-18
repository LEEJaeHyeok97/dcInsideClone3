package com.example.BoardApplication.domain.member;

import com.example.BoardApplication.domain.member.dto.MemberDTO;
import com.example.BoardApplication.domain.member.entity.MemberEntity;
import com.example.BoardApplication.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service //스프링 빈으로 등록
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        // repository의 save메서드 호출(JPA) 엔티티 객체를 넘겨줘야 한다.
        //1. dto -> entity
        //2. repository의 save 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
        // repository의 save 메서드 호출(엔티티르 넘겨줘야한다)


    }

    public MemberDTO login(MemberDTO memberDTO) {
        // 1. 회원이 입력한 이메일로 DB에서 조회를 함

        // 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단

        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberId(memberDTO.getMemberId());
        if (byMemberEmail.isPresent()) {
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassWord().equals(memberDTO.getMemberPassword())) {
                // 비밀번호 일치
                // entity -> dto 변환
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                // 비밀번호 불일치
                return null;
            }
        } else {
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }
    }
}
