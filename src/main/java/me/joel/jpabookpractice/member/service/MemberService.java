package me.joel.jpabookpractice.member.service;

import me.joel.jpabookpractice.member.entity.Member;
import me.joel.jpabookpractice.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Date : 19. 4. 10
 * author : joel
 */

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        // 중복회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getMemberId();

    }

    /**
     * 중복회원 검증
     */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMemberList() {
        return memberRepository.findAll();
    }

    /**
     * 회원 상세 조회
     */
    public Member findOne(Member member) {
        return memberRepository.findOne(member.getMemberId());
    }

}
