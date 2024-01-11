package com.alllexe.kafkademo.repository;

import com.alllexe.kafkademo.model.Operation;
import com.alllexe.kafkademo.model.OperationStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, UUID> {

    @Query("from Operation where status in (:statuses) and incomeDateTime >= :from and incomeDateTime <= :to")
    List<Operation> findAllByStatusAndIncomeDateTimeFromToPageable(@Param("from") LocalDateTime from,
                                                           @Param("to") LocalDateTime to,
                                                           @Param("statuses") List<OperationStatus> operationStatuses,
                                                           Pageable pageable);

    @Query("from Operation where status in (:statuses) and incomeDateTime >= :from and incomeDateTime <= :to and comment = :comment")
    List<Operation> findAllByStatusAndIncomeDateTimeAndCommentFromToPageable(@Param("from") LocalDateTime from,
                                                                   @Param("to") LocalDateTime to,
                                                                   @Param("statuses") List<OperationStatus> operationStatuses,
                                                                   @Param("comment") String comment,
                                                                   Pageable pageable);

    @Query("from Operation where status in (:statuses) and incomeDateTime >= :from and incomeDateTime <= :to")
    List<Operation> findAllByStatusAndIncomeDateTimeFromTo(@Param("from") LocalDateTime from,
                                                           @Param("to") LocalDateTime to,
                                                           @Param("statuses") List<OperationStatus> operationStatuses);
}
