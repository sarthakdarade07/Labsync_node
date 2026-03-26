package com.labsync.lms.repository;

import com.labsync.lms.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/** TimeSlotRepository — data access for TimeSlot entity. */
@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByActiveTrueOrderByStartTime();
}
