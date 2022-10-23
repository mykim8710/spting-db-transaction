package com.example.sptingtx.member.service;

import com.example.sptingtx.member.repository.LogRepository;
import com.example.sptingtx.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    @DisplayName("외부트랜젝션이 없이 성공에 대한 케이스")
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


}