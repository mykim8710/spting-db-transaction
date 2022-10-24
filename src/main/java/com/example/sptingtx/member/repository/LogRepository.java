package com.example.sptingtx.member.repository;

import com.example.sptingtx.member.domain.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LogRepository {
    private final EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW) // jpa의 모든변경(insert, update, delete)는 트랜젝션안에서 이루어져야한다.
    public void save(Log logMessage) {
        log.info("log 저장");
        em.persist(logMessage);

        if(logMessage.getMessage().contains("로그예외")) {
            log.info("log 저장시 예외발생");
            throw new RuntimeException("예외 발생");
        }
    }

    public Optional<Log> findByMessage(String message) {
        return em.createQuery("select l from Log l where l.message = :message", Log.class)
                .setParameter("message", message)
                .getResultList().stream().findAny();
    }

}
