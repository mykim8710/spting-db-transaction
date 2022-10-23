package com.example.sptingtx.member.service;

import com.example.sptingtx.member.domain.Log;
import com.example.sptingtx.member.domain.Member;
import com.example.sptingtx.member.repository.LogRepository;
import com.example.sptingtx.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final LogRepository logRepository;


    public void joinV1(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("== memberRepository 호출 시작 ==");
        memberRepository.save(member);  // transaction
        log.info("== memberRepository 호출 종료 ==");

        log.info("== logRepository 호출 시작 ==");
        logRepository.save(logMessage); // transaction
        log.info("== logRepository 호출 종료 ==");
    }


    public void joinV2(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("== memberRepository 호출 시작 ==");
        memberRepository.save(member);  // transaction
        log.info("== memberRepository 호출 종료 ==");

        log.info("== logRepository 호출 시작 ==");

        try {
            logRepository.save(logMessage); // transaction
        }catch (RuntimeException e) {
            log.info("log 저장에 실했습니다. logMessage={}", logMessage.getMessage());
            log.info("정상흐름 반환");
        }

        log.info("== logRepository 호출 종료 ==");
    }

}
