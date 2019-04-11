package me.joel.jpabookpractice.member.service;

import me.joel.jpabookpractice.member.entity.Member;
import me.joel.jpabookpractice.member.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * User: joel
 * Date: 2019-04-11
 * Time: 오후 8:36
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() {

        // Given
        Member member = new Member();
        member.setName("joel");

        // When
        Long saveId = memberService.join(member);

        // Then
        assertEquals(member, memberRepository.findOne(saveId));
        /*@Transactional 선언 안해주면 같지 않다고 나옴.
        * @Transactional을 선언하면 영속성 컨텍스트 내의 1차캐시에 저장된다.
        * 그래서 findOne(saveId)을 해도 하이버네이트 1 레벨 캐시에 있는 member가 조회 되기 때문에(DB조회X) 같다.
        * 그 반대의 경우는 메서드마다(join, findOne) 각각의 트랜잭션으로 처리된다.
        * findOne(saveId)을 하면 DB에서 쿼리를 가져와서 둘은 다르다고 나온다.
        * 참고 : https://stackoverflow.com/questions/26597440/how-do-you-test-spring-transactional-without-just-hitting-hibernate-level-1-cac
        * */
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() {

        // Given
        Member member1 = new Member();
        member1.setName("joel");

        Member member2 = new Member();
        member2.setName("joel");

        // When
        memberService.join(member1);
        memberService.join(member2);

        // Then
        fail("예외가 발생해야 한다.");
    }
}