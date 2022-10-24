package com.example.sptingtx.member.repository;

import com.example.sptingtx.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    @Transactional // jpa의 모든변경(insert, update, delete)는 트랜젝션안에서 이루어져야한다.
    public void save(Member member) {
        log.info("member 저장");
        em.persist(member);
    }

    public Optional<Member> findByName(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList().stream().findAny();
    }

}
