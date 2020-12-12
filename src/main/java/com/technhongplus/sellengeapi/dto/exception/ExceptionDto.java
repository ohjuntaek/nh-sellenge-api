package com.technhongplus.sellengeapi.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionDto {
    private String errorCode;
    private String errorMessage;
}
