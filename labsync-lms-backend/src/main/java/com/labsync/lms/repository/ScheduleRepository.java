package com.labsync.lms.repository;

import com.labsync.lms.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ScheduleRepository — data access + clash-detection queries for Schedule entity.
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByBatchIdAndActiveTrue(Long batchId);

    List<Schedule> findByDayIdAndActiveTrue(Long dayId);

    List<Schedule> findByLabIdAndActiveTrue(Long labId);

    List<Schedule> findByStaffIdAndActiveTrue(Long staffId);

    // ── Clash detection queries ──────────────────────────────────────────────

    @Query("SELECT COUNT(s) > 0 FROM Schedule s WHERE s.lab.id = :labId AND s.day.id = :dayId AND s.startTime < :endTime AND s.endTime > :startTime AND s.active = true AND (:excludeId IS NULL OR s.id != :excludeId)")
    boolean hasLabClash(@Param("labId") Long labId, @Param("dayId") Long dayId, @Param("startTime") java.time.LocalTime startTime, @Param("endTime") java.time.LocalTime endTime, @Param("excludeId") Long excludeId);

    @Query("SELECT COUNT(s) > 0 FROM Schedule s WHERE s.staff.id = :staffId AND s.day.id = :dayId AND s.startTime < :endTime AND s.endTime > :startTime AND s.active = true AND (:excludeId IS NULL OR s.id != :excludeId)")
    boolean hasStaffClash(@Param("staffId") Long staffId, @Param("dayId") Long dayId, @Param("startTime") java.time.LocalTime startTime, @Param("endTime") java.time.LocalTime endTime, @Param("excludeId") Long excludeId);

    @Query("SELECT COUNT(s) > 0 FROM Schedule s WHERE s.batch.id = :batchId AND s.day.id = :dayId AND s.startTime < :endTime AND s.endTime > :startTime AND s.active = true AND (:excludeId IS NULL OR s.id != :excludeId)")
    boolean hasBatchClash(@Param("batchId") Long batchId, @Param("dayId") Long dayId, @Param("startTime") java.time.LocalTime startTime, @Param("endTime") java.time.LocalTime endTime, @Param("excludeId") Long excludeId);

    // ── Timetable fetch queries ──────────────────────────────────────────────

    @Query("""
            SELECT s FROM Schedule s
            JOIN FETCH s.batch JOIN FETCH s.subject
            JOIN FETCH s.staff JOIN FETCH s.lab
            JOIN FETCH s.day
            WHERE s.batch.id = :batchId AND s.active = true
            ORDER BY s.day.dayOrder, s.startTime
            """)
    List<Schedule> findTimetableByBatch(@Param("batchId") Long batchId);

    @Query("""
            SELECT s FROM Schedule s
            JOIN FETCH s.batch JOIN FETCH s.subject
            JOIN FETCH s.staff JOIN FETCH s.lab
            JOIN FETCH s.day
            WHERE s.day.id = :dayId AND s.active = true
            ORDER BY s.startTime
            """)
    List<Schedule> findTimetableByDay(@Param("dayId") Long dayId);

    @Query("""
            SELECT s FROM Schedule s
            WHERE s.lab.id = :labId AND s.day.id = :dayId AND s.active = true
            ORDER BY s.startTime
            """)
    List<Schedule> findByLabAndDay(@Param("labId") Long labId, @Param("dayId") Long dayId);

    /** Deactivate all current schedules before applying a new GA-generated timetable */
    @Modifying
    @Query("UPDATE Schedule s SET s.active = false WHERE s.active = true")
    void deactivateAllSchedules();

    /** Count active schedules per batch (used to verify completeness) */
    @Query("SELECT COUNT(s) FROM Schedule s WHERE s.batch.id = :batchId AND s.active = true")
    long countActiveByBatch(@Param("batchId") Long batchId);
}
