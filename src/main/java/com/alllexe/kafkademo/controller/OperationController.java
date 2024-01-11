package com.alllexe.kafkademo.controller;

import com.alllexe.kafkademo.dto.OperationDto;
import com.alllexe.kafkademo.model.OperationStatus;
import com.alllexe.kafkademo.service.OperationRunnerType;
import com.alllexe.kafkademo.service.OperationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "operations", description = "Operations")
public class OperationController extends ApiController {

    private final OperationService operationService;

    @GetMapping("/operations")
    public List<OperationDto> getOperations(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to,
            @RequestParam(required = false) OperationStatus operationStatus,
            @RequestParam(required = false) String comment,
            @RequestParam(required = false) Integer limit) {
        log.info("Get operations from {} to {} by: status [{}], comment [{}], limit [{}]",
                from, to, operationStatus, comment, limit);
        return operationService.getOperations(from, to, operationStatus, comment, limit);
    }

    @PostMapping("/operations/generate")
    public Integer generateOperations() {
        log.info("Generate operations");
        return operationService.generateOperations();
    }

    @PostMapping("/operations/run")
    public void runOperations(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to) {
        log.info("Run operations from [{}] to [{}]", from, to);
        operationService.runOperations(from, to, OperationRunnerType.REGULAR);
    }

    @PostMapping("/operations/run-through-kafka")
    public void runOperationsThroughKafka(
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to) {
        log.info("Run operations with kafka from [{}] to [{}]", from, to);
        operationService.runOperations(from, to, OperationRunnerType.KAFKA);
    }

    @DeleteMapping("/operations")
    public void deleteOperations() {
        log.info("Delete operations");
        operationService.deleteOperations();
    }
}
