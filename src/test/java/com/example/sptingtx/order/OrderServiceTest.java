package com.example.sptingtx.order;

import com.example.sptingtx.order.domain.Order;
import com.example.sptingtx.order.enums.OrderPayStatusType;
import com.example.sptingtx.order.enums.OrderUsernameType;
import com.example.sptingtx.order.exception.NotEnoughMoneyException;
import com.example.sptingtx.order.repository.OrderRepository;
import com.example.sptingtx.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Slf4j
class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("정상승인에 대한 테스트")
    void completeTest() throws NotEnoughMoneyException {
        // given
        Order order = new Order();
        order.setUsername(OrderUsernameType.OK.getUsername());

        // when
        orderService.order(order);

        // then
        Order findOrder = orderRepository.findById(order.getId()).get();
        Assertions.assertThat(findOrder.getPayStatus()).isEqualTo(OrderPayStatusType.COMPLETE.getPayStatus());
    }


    @Test
    @DisplayName("Runtime(unchecked) Exception에 대한 테스트")
    void checkedExceptionTest() throws NotEnoughMoneyException {
        // given
        Order order = new Order();
        order.setUsername(OrderUsernameType.EXCEPTION.getUsername());

        // when, then
        Assertions.assertThatThrownBy(() -> orderService.order(order))
                .isInstanceOf(RuntimeException.class);

        // then: 롤백되었으므로 데이터가 없어야 한다.
        Optional<Order> orderOptional = orderRepository.findById(order.getId());
        Assertions.assertThat(orderOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("비지니스(checked) Exception에 대한 테스트")
    void bizExceptionTest() {
        //given
        Order order = new Order();
        order.setUsername(OrderUsernameType.NOT_ENOUGH_MONEY.getUsername());

        //when
        try {
            orderService.order(order);
            //fail("잔고 부족 예외가 발생해야 합니다.");
        } catch (NotEnoughMoneyException e) {
            log.info("고객에게 잔고 부족을 알리고 별도의 계좌로 입금하도록 안내");
        }

        // then
        Order findOrder = orderRepository.findById(order.getId()).get();
        Assertions.assertThat(findOrder.getPayStatus()).isEqualTo(OrderPayStatusType.READY.getPayStatus());
    }

}