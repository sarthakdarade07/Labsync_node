package com.labsync.lms.controller;

import com.labsync.lms.dto.response.ApiResponse;
import com.labsync.lms.model.TimeSlot;
import com.labsync.lms.repository.TimeSlotRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {
    private final TimeSlotRepository timeSlotRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TimeSlot>>> getAllTimeSlots() {
        return ResponseEntity.ok(ApiResponse.success(timeSlotRepository.findAll()));
    }

    public TimeSlotController(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }
}
