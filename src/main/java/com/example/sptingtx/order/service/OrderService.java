package com.example.sptingtx.order.service;

import com.example.sptingtx.order.domain.Order;
import com.example.sptingtx.order.enums.OrderPayStatusType;
import com.example.sptingtx.order.exception.NotEnoughMoneyException;
import com.example.sptingtx.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.sptingtx.order.enums.OrderUsernameType.findOrderUsernameType;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    // jpa는 트랜잭션 커밋 시점에 Order 데이터를 DB에 반영한다.
    @Transactional
    public void order(Order order) throws NotEnoughMoneyException {
        log.info("order 호출, order= {}", order);
        orderRepository.save(order);

        log.info("결제 프로세스 진입");

        switch(findOrderUsernameType(order.getUsername())) {
            case EXCEPTION:
                log.info("시스템 예외(unchecked Exception, rollback) 발생");
                throw new RuntimeException("시스템 예외");   // rollback;

            case NOT_ENOUGH_MONEY:
                log.info("잔고부족 비지니스 예외(checked exception, commit) 발생");
                order.setPayStatus(OrderPayStatusType.READY.getPayStatus());
                throw new NotEnoughMoneyException("잔고부족");  // commit;

            default:
                log.info("정상승인");
                order.setPayStatus(OrderPayStatusType.COMPLETE.getPayStatus());   // commit;
                break;
        }

        log.info("결제 프로세스 완료");

    }

}
