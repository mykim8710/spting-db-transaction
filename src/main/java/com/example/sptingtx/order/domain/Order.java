package com.example.sptingtx.order.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders") // 데이터베이스의 Orders라는 이름의 테이블과 맵핑할것이다.
@Getter
@Setter
@ToString
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private String username;    // 정상, 예외, 잔고부족
    private String payStatus;   // 대기, 완료
}
