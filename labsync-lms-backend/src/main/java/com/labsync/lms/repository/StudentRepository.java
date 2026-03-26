package com.labsync.lms.repository;

import com.labsync.lms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * StudentRepository — data access for Student entity.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByPrn(String prn);

    boolean existsByPrn(String prn);

    List<Student> findByBatchId(Long batchId);

    List<Student> findByProgramId(Long programId);

    List<Student> findByAcademicYearId(Long academicYearId);

    @Query("SELECT s FROM Student s WHERE s.batch.id = :batchId AND s.academicYear.id = :yearId")
    List<Student> findByBatchAndYear(@Param("batchId") Long batchId, @Param("yearId") Long yearId);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.batch.id = :batchId")
    long countByBatchId(@Param("batchId") Long batchId);
}
