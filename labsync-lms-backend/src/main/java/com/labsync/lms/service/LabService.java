package com.labsync.lms.service;

import com.labsync.lms.dto.request.ComputerStatusRequest;
import com.labsync.lms.dto.request.LabRequest;
import com.labsync.lms.dto.response.LabAvailabilityResponse;
import com.labsync.lms.model.Lab;

import java.util.List;

/**
 * LabService — CRUD + availability operations for Lab management.
 */
public interface LabService {
    Lab createLab(LabRequest request);
    Lab updateLab(Long id, LabRequest request);
    Lab getLabById(Long id);
    List<Lab> getAllLabs();
    List<LabAvailabilityResponse> getAllLabAvailability();
    LabAvailabilityResponse getLabAvailability(Long id);
    Lab updateComputerStatus(Long id, ComputerStatusRequest request);
    List<Lab> searchAvailableLabs(Long dayId, String startTime, String endTime, Integer requiredComputers, String osType);
    void deleteLab(Long id);
}
