package com.alllexe.kafkademo.service;

import com.alllexe.kafkademo.dto.OperationDto;
import com.alllexe.kafkademo.exception.RunnerNotFoundException;
import com.alllexe.kafkademo.model.Operation;
import com.alllexe.kafkademo.model.OperationStatus;
import com.alllexe.kafkademo.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class OperationService {

    @Value("${operation.generation.count}")
    private Integer count;
    private final List<OperationRunner> operationRunners;
    private final OperationRepository operationRepository;

    public List<OperationDto> getOperations(LocalDateTime from,
                                            LocalDateTime to,
                                            OperationStatus operationStatus,
                                            String comment,
                                            Integer limit) {
        from = getFrom(from);
        to = getTo(from, to);
        List<OperationStatus> operationStatuses = getStatuses(operationStatus);
        limit = getLimit(limit);
        PageRequest page = PageRequest.of(0, limit);
        if (comment == null) {
            return operationRepository.findAllByStatusAndIncomeDateTimeFromToPageable(from, to, operationStatuses, page).stream()
                    .map(this::toOperationDto)
                    .collect(Collectors.toList());
        } else {
            return operationRepository.findAllByStatusAndIncomeDateTimeAndCommentFromToPageable(from, to, operationStatuses, comment, page).stream()
                    .map(this::toOperationDto)
                    .collect(Collectors.toList());
        }
    }

    public Integer generateOperations() {
        log.info("Start generation {} operations", count);
        List<Operation> operations = IntStream.range(0, count)
                .mapToObj(i -> Operation.builder().status(OperationStatus.NEW).build())
                .toList();
        return operationRepository.saveAll(operations).size();
    }

    public void runOperations(LocalDateTime from, LocalDateTime to, OperationRunnerType operationRunnerType) {
        from = getFrom(from);
        to = getTo(from, to);
        List<Operation> operations = operationRepository.findAllByStatusAndIncomeDateTimeFromTo(from, to, List.of(OperationStatus.NEW));
        OperationRunner operationRunner = operationRunners.stream()
                .filter(runner -> Objects.equals(runner.getOperationRunnerType(), operationRunnerType))
                .findFirst().orElseThrow(RunnerNotFoundException::new);
        operationRunner.runOperations(operations);
    }

    public void deleteOperations() {
        operationRepository.deleteAll();
    }

    private LocalDateTime getFrom(LocalDateTime from) {
        return from == null ? LocalDate.now().atStartOfDay() : from;
    }

    private LocalDateTime getTo(LocalDateTime from, LocalDateTime to) {
        return to == null ? from.with(LocalTime.MAX) : to;
    }

    private List<OperationStatus> getStatuses(OperationStatus operationStatus) {
        return operationStatus == null ? List.of(OperationStatus.NEW, OperationStatus.COMPLETED) : List.of(operationStatus);
    }

    private Integer getLimit(Integer limit) {
        return limit == null ? 100 : limit;
    }

    private OperationDto toOperationDto(Operation operation) {
        return OperationDto.builder()
                .id(operation.getId())
                .incomeDateTime(operation.getIncomeDateTime())
                .completeDateTime(operation.getCompleteDateTime())
                .operationStatus(operation.getStatus())
                .comment(operation.getComment())
                .build();
    }

}
