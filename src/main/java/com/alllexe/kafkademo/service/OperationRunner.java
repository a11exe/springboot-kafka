package com.alllexe.kafkademo.service;

import com.alllexe.kafkademo.model.Operation;

import java.util.List;

public interface OperationRunner {

    OperationRunnerType getOperationRunnerType();

    void runOperations(List<Operation> operations);
}
