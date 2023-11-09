package com.alllexe.kafkademo.controller;

import com.alllexe.kafkademo.dto.OperationDto;
import com.alllexe.kafkademo.model.OperationStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/operations")
    public List<OperationDto> getOperations(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to,
            @RequestParam OperationStatus operationStatus) {
        log.info("Get operations from {} to {} by status [{}]", from, to, operationStatus);
        return null;
    }

    @PostMapping("/operations/generate")
    public List<OperationDto> generateOperations() {
        log.info("Generate operations");
        return null;
    }

    @PostMapping("/operations/run")
    public void runOperations(
            @RequestParam LocalDateTime from,
            @RequestParam LocalDateTime to) {
        log.info("Run operations from {} to {}", from, to);
    }
}
