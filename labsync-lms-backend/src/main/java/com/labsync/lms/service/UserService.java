package com.labsync.lms.service;

import com.labsync.lms.dto.response.UserResponse;
import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    void deleteUser(Long id);
    void changePassword(Long id, String newPassword);
}
