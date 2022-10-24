package com.example.sptingtx.member.service;

import com.example.sptingtx.member.repository.LogRepository;
import com.example.sptingtx.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LogRepository logRepository;


    /**
     * MemberService    @Transactional:OFF
     * MemberRepository @Transactional:ON
     * LogRepository    @Transactional:ON
     */
    @Test
    @DisplayName("외부트랜잭션이 없이 성공에 대한 케이스")
    void outerTxOffSuccessTest() {
        // given
        String username = "outerTxOffSuccessTest";

        // when
        memberService.joinV1(username);

        // when : 모든 데이터가 정상 저장된다.
        // then
        Assertions.assertTrue(memberRepository.findByName(username).isPresent()); // junit : Assertions
        Assertions.assertTrue(logRepository.findByMessage(username).isPresent());
    }


    /**
     * MemberService    @Transactional:OFF
     * MemberRepository @Transactional:ON
     * LogRepository    @Transactional:ON -> Exception(RuntimeException)
     */
    @Test
    @DisplayName("외부트랜잭션이 없이 실패에 대한 케이스")
    void outerTxOffFailTest() {
        // given
        String username = "로그예외_outerTxOffFailTest";

        // when
        //memberService.joinV1(username);
        assertThatThrownBy(() -> memberService.joinV1(username))
                                           .isInstanceOf(RuntimeException.class);

        // then :완전히 롤백되지 않고, member 데이터가 남아서 저장된다.
        Assertions.assertTrue(memberRepository.findByName(username).isPresent()); // junit : Assertions
        Assertions.assertTrue(logRepository.findByMessage(username).isEmpty());
    }


    /**
     * MemberService    @Transactional:ON
     * MemberRepository @Transactional:OFF
     * LogRepository    @Transactional:OFF
     */
    @Test
    @DisplayName("서비스 계층에서 트랜잭션을 사용한 케이스")
    void singleTxTest() {
        // given
        String username = "singleTxTest";

        // when
        memberService.joinV1(username);

        //then: 모든 데이터가 정상 저장된다.
        Assertions.assertTrue(memberRepository.findByName(username).isPresent()); // junit : Assertions
        Assertions.assertTrue(logRepository.findByMessage(username).isPresent());
    }


    /**
     * MemberService    @Transactional:ON
     * MemberRepository @Transactional:ON
     * LogRepository    @Transactional:ON
     */
    @Test
    @DisplayName("모든 계층에서 트랜잭션을 사용하여 성공한 케이스")
    void outerTxOnSuccessTest() {
        // given
        String username = "outerTxOnSuccessTest";

        // when
        memberService.joinV1(username);

        // when : 모든 데이터가 정상 저장된다.
        // then
        Assertions.assertTrue(memberRepository.findByName(username).isPresent()); // junit : Assertions
        Assertions.assertTrue(logRepository.findByMessage(username).isPresent());
    }

    /**
     * MemberService    @Transactional:ON
     * MemberRepository @Transactional:ON
     * LogRepository    @Transactional:ON Exception
     */
    @Test
    @DisplayName("모든 계층에서 트랜잭션을 사용하여 실패한 케이스")
    void outerTxOnFailTest() {
        // given
        String username = "로그예외_outerTxOnFailTest";

        // when
        assertThatThrownBy(() -> memberService.joinV1(username))
                .isInstanceOf(RuntimeException.class);

        //then: 모든 데이터가 롤백된다.
        Assertions.assertTrue(memberRepository.findByName(username).isEmpty());
        Assertions.assertTrue(logRepository.findByMessage(username).isEmpty());
    }

    /**
     * MemberService    @Transactional:ON
     * MemberRepository @Transactional:ON
     * LogRepository    @Transactional:ON Exception
     */
    @Test
    @DisplayName("모든 계층에서 트랜잭션을 사용하여 실패(Service에서 Exception을 잡음)한 케이스")
    void recoverExceptionFailTest() {
        // given
        String username = "로그예외_recoverExceptionFailTest";

        // when
        assertThatThrownBy(() -> memberService.joinV2(username))
                .isInstanceOf(UnexpectedRollbackException.class);

        //then: 모든 데이터가 롤백된다.
        Assertions.assertTrue(memberRepository.findByName(username).isEmpty()); // rollback
        Assertions.assertTrue(logRepository.findByMessage(username).isEmpty()); // rollback
    }

    /**
     * MemberService    @Transactional:ON
     * MemberRepository @Transactional:ON
     * LogRepository    @Transactional:ON REQUIRES_NEW
     */
    @Test
    @DisplayName("모든 계층에서 트랜잭션을 사용, 트랜잭션 분리(REQUIRES_NEW)")
    void recoverExceptionSuccessTest() {
        // given
        String username = "로그예외_recoverExceptionSuccessTest";

        // when
        memberService.joinV2(username);

        // then : member 저장, log 롤백
        Assertions.assertTrue(memberRepository.findByName(username).isPresent()); // commit
        Assertions.assertTrue(logRepository.findByMessage(username).isEmpty()); // rollback
    }
}