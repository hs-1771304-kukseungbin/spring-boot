package jpacoffee.jpacafe.service;

import jakarta.persistence.EntityManager;
import jpacoffee.jpacafe.domain.Member;
import jpacoffee.jpacafe.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
//    @Autowired EntityManager entityManager;
    @Test
//    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kuk");

        //when
        Long savedId = memberService.join(member);


        //then
//        entityManager.flush();
        assertEquals(member, memberRepository.findOne(savedId));
        }
        
        
    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kuk");

        Member member2 = new Member();
        member2.setName("kuk");
        //when
        memberService.join(member1);
        try {
            memberService.join(member2);
        } catch (IllegalStateException e){
            return;
        }
        //then
        fail("예외가 발생해야 한다.");
        } 
}