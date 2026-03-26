package com.labsync.lms.service;

import com.labsync.lms.dto.request.GaRunRequest;
import com.labsync.lms.dto.response.ScheduleResponse;
import com.labsync.lms.model.AlgorithmRun;

import java.util.List;
import java.util.Map;

/**
 * TimetableService — GA orchestration + timetable display contract.
 */
public interface TimetableService {

    /** Fetch full timetable for a batch, organised by day */
    Map<String, List<ScheduleResponse>> getTimetableByBatch(Long batchId);

    /** Fetch all sessions for a given day */
    List<ScheduleResponse> getTimetableByDay(Long dayId);

    /**
     * Triggers the Genetic Algorithm to generate a new optimised timetable.
     * If {@code request.isApplyResult()}, replaces the current active schedule.
     */
    AlgorithmRun runGeneticAlgorithm(GaRunRequest request, Long triggeredByUserId);

    /** Apply the best schedule from a completed GA run */
    void applyGaResult(Long algorithmRunId);

    /** List all GA runs (audit history) */
    List<AlgorithmRun> getAlgorithmRunHistory();
}
