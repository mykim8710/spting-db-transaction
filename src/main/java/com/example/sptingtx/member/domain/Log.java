package com.example.sptingtx.member.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Log {
    @Id
    @GeneratedValue
    private Long id;
    private String message;

    // jpa는 기본생성자가 필요
    public Log() {
    }

    public Log(String message) {
        this.message = message;
    }
}
