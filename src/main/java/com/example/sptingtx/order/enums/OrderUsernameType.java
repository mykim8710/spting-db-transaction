package com.example.sptingtx.order.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum OrderUsernameType {
    OK("정상"),
    NOT_ENOUGH_MONEY("잔고부족"),
    EXCEPTION("예외"),

    ;

    private String username;

    OrderUsernameType(String username) {
        this.username = username;
    }

    public static OrderUsernameType findOrderUsernameType(String username) {
        for(OrderUsernameType orderUsernameEnums : values()) {
            if(username.equals(orderUsernameEnums.getUsername())) {
                return orderUsernameEnums;
            }
        }
        return null;
    }
}
