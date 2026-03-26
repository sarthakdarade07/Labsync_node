package com.labsync.lms.repository;

import com.labsync.lms.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** StaffRepository — data access for Staff entity. */
@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findByEmployeeId(String employeeId);

    Optional<Staff> findByUserId(Long userId);

    boolean existsByEmployeeId(String employeeId);

    /** Find staff not scheduled on a given day + time */
    @Query("""
            SELECT st FROM Staff st WHERE st.id NOT IN (
                SELECT s.staff.id FROM Schedule s
                WHERE s.day.id = :dayId 
                  AND s.startTime < :endTime 
                  AND s.endTime > :startTime 
                  AND s.active = true
            )
            """)
    List<Staff> findFreeStaff(@Param("dayId") Long dayId, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
