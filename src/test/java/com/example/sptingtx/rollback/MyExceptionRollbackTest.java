package com.example.sptingtx.rollback;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@SpringBootTest
@Slf4j
public class MyExceptionRollbackTest {
    @Autowired
    FooService fooService;

    @Test
    void doFooServiceMethodTest() {
        Assertions.assertThatThrownBy(() -> fooService.fooMethod())
                .isInstanceOf(NullPointerException.class);
    }

    @TestConfiguration
    static class RollbackTestConfig {
        @Bean
        FooService fooService() {
            return new FooService();
        }
    }

    static class FooService {
        @Transactional
        public void fooMethod() {
            log.info("do fooMethod1");
            printTxInfo();

            String a = getString(); // exception 발생
            fooMethod2(a);
        }

        @Transactional
        public void fooMethod2(String a) {
            log.info("do fooMethod2, a={}", a);
            printTxInfo();
        }

        private String getString() {
            throw new NullPointerException();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}",  txActive);
        }
    }
}
