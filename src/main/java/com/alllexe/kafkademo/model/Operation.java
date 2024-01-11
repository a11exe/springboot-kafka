package com.alllexe.kafkademo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operation extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    private OperationStatus status;
    @Column(name = "income_datetime")
    private final LocalDateTime incomeDateTime = LocalDateTime.now();
    @Column(name = "complete_datetime")
    private LocalDateTime completeDateTime;
    private String comment;
}
