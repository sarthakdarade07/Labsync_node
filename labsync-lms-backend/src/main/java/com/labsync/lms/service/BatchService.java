package com.labsync.lms.service;

import com.labsync.lms.dto.request.BatchRequest;
import com.labsync.lms.model.Batch;
import java.util.List;

/** BatchService — CRUD contract for Batch management. */
public interface BatchService {
    Batch createBatch(BatchRequest request);
    Batch updateBatch(Long id, BatchRequest request);
    Batch getBatchById(Long id);
    List<Batch> getAllBatches();
    List<Batch> getBatchesByProgram(Long programId);
    void deleteBatch(Long id);
}
