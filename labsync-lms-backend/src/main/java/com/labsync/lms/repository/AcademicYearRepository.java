package com.labsync.lms.repository;

import com.labsync.lms.model.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/** AcademicYearRepository — data access for AcademicYear entity. */
@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {
    Optional<AcademicYear> findByYearLabel(String yearLabel);
    Optional<AcademicYear> findByActiveTrue();
}
