package com.alllexe.kafkademo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Operation extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    private OperationStatus status;
    private String comment;
}
