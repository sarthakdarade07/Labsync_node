package com.labsync.lms.service;

import com.labsync.lms.dto.request.StudentRequest;
import com.labsync.lms.model.Student;

import java.util.List;

/**
 * StudentService — CRUD contract for Student management.
 */
public interface StudentService {
    Student createStudent(StudentRequest request);
    Student updateStudent(Long id, StudentRequest request);
    Student getStudentById(Long id);
    Student getStudentByPrn(String prn);
    List<Student> getAllStudents();
    List<Student> getStudentsByBatch(Long batchId);
    void deleteStudent(Long id);
}
