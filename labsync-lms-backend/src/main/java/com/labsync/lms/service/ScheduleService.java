package com.labsync.lms.service;

import com.labsync.lms.dto.request.ScheduleRequest;
import com.labsync.lms.dto.response.ClashReport;
import com.labsync.lms.dto.response.ScheduleResponse;
import com.labsync.lms.model.Schedule;

import java.util.List;

/**
 * ScheduleService — manual scheduling + clash detection contract.
 */
public interface ScheduleService {
    Schedule createSchedule(ScheduleRequest request);
    Schedule updateSchedule(Long id, ScheduleRequest request);
    void deleteSchedule(Long id);
    Schedule getScheduleById(Long id);
    List<ScheduleResponse> getAllSchedules();
    List<ScheduleResponse> getSchedulesByBatch(Long batchId);
    List<ScheduleResponse> getSchedulesByDay(Long dayId);
    List<ScheduleResponse> getSchedulesByLab(Long labId);

    /** Validates a schedule request without persisting — returns all detected clashes. */
    ClashReport validateSchedule(ScheduleRequest request);
    ClashReport validateSchedule(ScheduleRequest request, Long excludeScheduleId);

    ScheduleResponse toResponse(Schedule schedule);
}
