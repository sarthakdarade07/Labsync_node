package com.labsync.lms.controller;

import com.labsync.lms.dto.request.SettingRequest;
import com.labsync.lms.dto.response.ApiResponse;
import com.labsync.lms.model.SystemSetting;
import com.labsync.lms.repository.SystemSettingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/settings")
public class SettingController {
    private final SystemSettingRepository settingRepository;

    public SettingController(SystemSettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, String>>> getSettings() {
        Map<String, String> map = settingRepository.findAll().stream()
            .collect(Collectors.toMap(SystemSetting::getSettingKey, SystemSetting::getSettingValue));
        return ResponseEntity.ok(ApiResponse.success("Settings retrieved", map));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> saveSettings(@RequestBody SettingRequest request) {
        if (request.getSettings() != null) {
            request.getSettings().forEach((key, value) -> {
                settingRepository.save(new SystemSetting(key, value));
            });
        }
        return ResponseEntity.ok(ApiResponse.success("Settings saved successfully", null));
    }
}
