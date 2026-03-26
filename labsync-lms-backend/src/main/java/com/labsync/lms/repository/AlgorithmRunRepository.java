package com.labsync.lms.repository;

import com.labsync.lms.model.AlgorithmRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/** AlgorithmRunRepository — data access for AlgorithmRun entity. */
@Repository
public interface AlgorithmRunRepository extends JpaRepository<AlgorithmRun, Long> {
    List<AlgorithmRun> findAllByOrderByStartedAtDesc();
    Optional<AlgorithmRun> findTopByStatusOrderByStartedAtDesc(String status);
}
