package com.example.sptingtx.order.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum OrderPayStatusType {
    READY("대기"),
    COMPLETE("완료")
    ;

    private String payStatus;

    OrderPayStatusType(String payStatus) {
        this.payStatus = payStatus;
    }
}
