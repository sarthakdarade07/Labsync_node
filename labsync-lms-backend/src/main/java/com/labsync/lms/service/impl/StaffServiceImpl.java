package com.labsync.lms.service.impl;

import com.labsync.lms.dto.request.StaffRequest;
import com.labsync.lms.model.Staff;
import com.labsync.lms.repository.StaffRepository;
import com.labsync.lms.service.StaffService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public Staff createStaff(StaffRequest request) {
        Staff staff = Staff.builder()
            .fullName(request.getFullName())
            .employeeId(request.getEmployeeId())
            .email(request.getEmail())
            .phone(request.getPhone())
            .department(request.getDepartment())
            .designation(request.getDesignation())
            .build();
        return staffRepository.save(staff);
    }
}
