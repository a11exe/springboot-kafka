package com.alllexe.kafkademo.dto;

import com.alllexe.kafkademo.model.OperationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class OperationDto {
    private UUID id;
    private OperationStatus operationStatus;
    private LocalDateTime incomeDateTime;
    private LocalDateTime completeDateTime;
    private String comment;
}
