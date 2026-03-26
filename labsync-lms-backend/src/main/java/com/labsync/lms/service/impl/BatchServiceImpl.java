package com.labsync.lms.service.impl;

import com.labsync.lms.dto.request.BatchRequest;
import com.labsync.lms.exception.DuplicateResourceException;
import com.labsync.lms.exception.ResourceNotFoundException;
import com.labsync.lms.model.*;
import com.labsync.lms.repository.*;
import com.labsync.lms.service.BatchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * BatchServiceImpl — manages Batch lifecycle.
 * Enforces uniqueness of batchName per program+year combination.
 */
@Service
public class BatchServiceImpl implements BatchService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BatchServiceImpl.class);
    private final BatchRepository batchRepository;
    private final ProgramRepository programRepository;
    private final AcademicYearRepository academicYearRepository;
    private final SubjectRepository subjectRepository;

    @Override
    @Transactional
    public Batch createBatch(BatchRequest req) {
        if (batchRepository.existsByBatchNameAndProgramIdAndAcademicYearId(req.getBatchName(), req.getProgramId(), req.getAcademicYearId())) {
            throw new DuplicateResourceException("Batch \'" + req.getBatchName() + "\' already exists for this program and year");
        }
        List<Subject> subjects = req.getSubjectIds() != null ? subjectRepository.findAllById(req.getSubjectIds()) : List.of();
        Batch batch = Batch.builder().batchName(req.getBatchName()).division(req.getDivision()).studentCount(req.getStudentCount()).semester(req.getSemester()).osRequirement(req.getOsRequirement()).labsPerWeek(req.getLabsPerWeek() != null ? req.getLabsPerWeek() : 1).totalHours(req.getTotalHours()).startTime(req.getStartTime()).endTime(req.getEndTime()).program(fetchProgram(req.getProgramId())).academicYear(fetchYear(req.getAcademicYearId())).subjects(subjects).build();
        Batch saved = batchRepository.save(batch);
        log.info("Created batch: {} (id={})", saved.getBatchName(), saved.getId());
        return saved;
    }

    @Override
    @Transactional
    public Batch updateBatch(Long id, BatchRequest req) {
        Batch existing = getBatchById(id);
        existing.setBatchName(req.getBatchName());
        existing.setDivision(req.getDivision());
        existing.setStudentCount(req.getStudentCount());
        existing.setSemester(req.getSemester());
        existing.setOsRequirement(req.getOsRequirement());
        existing.setLabsPerWeek(req.getLabsPerWeek() != null ? req.getLabsPerWeek() : 1);
        existing.setTotalHours(req.getTotalHours());
        existing.setStartTime(req.getStartTime());
        existing.setEndTime(req.getEndTime());
        existing.setProgram(fetchProgram(req.getProgramId()));
        existing.setAcademicYear(fetchYear(req.getAcademicYearId()));
        
        if (req.getSubjectIds() != null) {
            List<Subject> subjects = subjectRepository.findAllById(req.getSubjectIds());
            existing.setSubjects(subjects);
        } else {
            existing.setSubjects(List.of());
        }
        return batchRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Batch getBatchById(Long id) {
        return batchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Batch", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Batch> getBatchesByProgram(Long programId) {
        return batchRepository.findByProgramId(programId);
    }

    @Override
    @Transactional
    public void deleteBatch(Long id) {
        batchRepository.delete(getBatchById(id));
        log.info("Deleted batch id={}", id);
    }

    private Program fetchProgram(Long id) {
        return programRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Program", "id", id));
    }

    private AcademicYear fetchYear(Long id) {
        return academicYearRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AcademicYear", "id", id));
    }

    public BatchServiceImpl(final BatchRepository batchRepository, final ProgramRepository programRepository, final AcademicYearRepository academicYearRepository, final SubjectRepository subjectRepository) {
        this.batchRepository = batchRepository;
        this.programRepository = programRepository;
        this.academicYearRepository = academicYearRepository;
        this.subjectRepository = subjectRepository;
    }
}
