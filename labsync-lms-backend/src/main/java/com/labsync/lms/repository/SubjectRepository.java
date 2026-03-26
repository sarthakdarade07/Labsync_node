package com.labsync.lms.repository;

import com.labsync.lms.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** SubjectRepository — data access for Subject entity. */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findBySubjectCode(String subjectCode);
    List<Subject> findByProgramId(Long programId);
    boolean existsBySubjectCode(String subjectCode);
}
