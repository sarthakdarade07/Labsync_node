package com.labsync.lms.repository;

import com.labsync.lms.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BatchRepository — data access for Batch entity.
 */
@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    List<Batch> findByProgramId(Long programId);

    List<Batch> findByAcademicYearId(Long academicYearId);

    List<Batch> findByProgramIdAndAcademicYearId(Long programId, Long academicYearId);

    boolean existsByBatchNameAndProgramIdAndAcademicYearId(
            String batchName, Long programId, Long academicYearId);
}
