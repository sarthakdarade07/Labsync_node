package com.labsync.lms.service.impl;

import com.labsync.lms.dto.request.StudentRequest;
import com.labsync.lms.exception.DuplicateResourceException;
import com.labsync.lms.exception.ResourceNotFoundException;
import com.labsync.lms.model.*;
import com.labsync.lms.repository.*;
import com.labsync.lms.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * StudentServiceImpl — CRUD operations for Student entity.
 * Validates PRN uniqueness and enforces FK constraints.
 */
@Service
public class StudentServiceImpl implements StudentService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;
    private final BatchRepository batchRepository;
    private final ProgramRepository programRepository;
    private final AcademicYearRepository academicYearRepository;

    @Override
    @Transactional
    public Student createStudent(StudentRequest req) {
        if (studentRepository.existsByPrn(req.getPrn())) {
            throw new DuplicateResourceException("Student with PRN \'" + req.getPrn() + "\' already exists");
        }
        Student student = Student.builder().fullName(req.getFullName()).prn(req.getPrn()).email(req.getEmail()).phone(req.getPhone()).batch(fetchBatch(req.getBatchId())).program(fetchProgram(req.getProgramId())).academicYear(fetchAcademicYear(req.getAcademicYearId())).build();
        Student saved = studentRepository.save(student);
        log.info("Created student: {} (PRN: {})", saved.getFullName(), saved.getPrn());
        return saved;
    }

    @Override
    @Transactional
    public Student updateStudent(Long id, StudentRequest req) {
        Student existing = getStudentById(id);
        // Allow same PRN only if it belongs to this student
        if (!existing.getPrn().equals(req.getPrn()) && studentRepository.existsByPrn(req.getPrn())) {
            throw new DuplicateResourceException("PRN \'" + req.getPrn() + "\' is already assigned to another student");
        }
        existing.setFullName(req.getFullName());
        existing.setPrn(req.getPrn());
        existing.setEmail(req.getEmail());
        existing.setPhone(req.getPhone());
        existing.setBatch(fetchBatch(req.getBatchId()));
        existing.setProgram(fetchProgram(req.getProgramId()));
        existing.setAcademicYear(fetchAcademicYear(req.getAcademicYearId()));
        return studentRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentByPrn(String prn) {
        return studentRepository.findByPrn(prn).orElseThrow(() -> new ResourceNotFoundException("Student", "PRN", prn));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudentsByBatch(Long batchId) {
        fetchBatch(batchId); // validate batch exists
        return studentRepository.findByBatchId(batchId);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
        log.info("Deleted student id={}", id);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────
    private Batch fetchBatch(Long id) {
        return batchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Batch", "id", id));
    }

    private Program fetchProgram(Long id) {
        return programRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Program", "id", id));
    }

    private AcademicYear fetchAcademicYear(Long id) {
        return academicYearRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AcademicYear", "id", id));
    }

    public StudentServiceImpl(final StudentRepository studentRepository, final BatchRepository batchRepository, final ProgramRepository programRepository, final AcademicYearRepository academicYearRepository) {
        this.studentRepository = studentRepository;
        this.batchRepository = batchRepository;
        this.programRepository = programRepository;
        this.academicYearRepository = academicYearRepository;
    }
}
