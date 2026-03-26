package com.labsync.lms.repository;

import com.labsync.lms.model.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/** DayRepository — data access for Day entity. */
@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
    Optional<Day> findByDayName(String dayName);
    List<Day> findByActiveTrueOrderByDayOrder();
}
