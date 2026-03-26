package com.labsync.lms.dto.request;

import java.util.Map;

public class SettingRequest {
    private Map<String, String> settings;
    public Map<String, String> getSettings() { return settings; }
    public void setSettings(Map<String, String> settings) { this.settings = settings; }
}
