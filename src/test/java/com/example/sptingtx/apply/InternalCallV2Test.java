package com.example.sptingtx.apply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV2Test {
    @Autowired
    CallService callService;

    @TestConfiguration
    static class InternalCallV2TestConfig {
        @Bean
        CallService callService() {
            return new CallService(interService());
        }
        @Bean
        InterService interService() {
            return new InterService();
        }
    }

    @RequiredArgsConstructor
    static class CallService {
        private final InterService interService;
        public void externalCall() {
            log.info("call external");
            printTxInfo();
            interService.internalCall();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}",  txActive);
        }
    }


    static class InterService {
        @Transactional
        public void internalCall() {
            log.info("call internal");
            printTxInfo();
        }

        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx active={}",  txActive);
        }
    }


    @Test
    void printProxy() {
        log.info("callService class={}", callService.getClass());
    }
    @Test
    void externalCallV2() {
        callService.externalCall();
    }
}
