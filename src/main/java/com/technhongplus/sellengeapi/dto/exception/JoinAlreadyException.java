package com.technhongplus.sellengeapi.dto.exception;

public class JoinAlreadyException extends RuntimeException {
    public JoinAlreadyException() {
        super("이미 참가한 챌린지입니다");
    }
}
