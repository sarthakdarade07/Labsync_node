package com.labsync.lms.service.impl;

import com.labsync.lms.dto.request.GaRunRequest;
import com.labsync.lms.dto.response.ScheduleResponse;
import com.labsync.lms.exception.ResourceNotFoundException;
import com.labsync.lms.model.*;
import com.labsync.lms.repository.*;
import com.labsync.lms.service.ScheduleService;
import com.labsync.lms.service.TimetableService;
import com.labsync.lms.util.Chromosome;
import com.labsync.lms.util.GeneticAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TimetableServiceImpl — orchestrates the Genetic Algorithm and manages
 * timetable display, GA run history, and result application.
 */
@Service
public class TimetableServiceImpl implements TimetableService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TimetableServiceImpl.class);
    private final ScheduleRepository scheduleRepository;
    private final AlgorithmRunRepository algorithmRunRepository;
    private final BatchRepository batchRepository;
    private final SubjectRepository subjectRepository;
    private final StaffRepository staffRepository;
    private final LabRepository labRepository;
    private final DayRepository dayRepository;
    private final UserRepository userRepository;
    private final ScheduleService scheduleService;

    // ── Timetable Display ─────────────────────────────────────────────────────
    /**
     * Returns the timetable for a batch, keyed by day name.
     * e.g., { "Monday": [...sessions...], "Tuesday": [...] }
     */
    @Override
    @Transactional(readOnly = true)
    public Map<String, List<ScheduleResponse>> getTimetableByBatch(Long batchId) {
        List<ScheduleResponse> sessions = scheduleRepository.findTimetableByBatch(batchId).stream().map(scheduleService::toResponse).collect(Collectors.toList());
        return sessions.stream().collect(Collectors.groupingBy(ScheduleResponse::getDayName, LinkedHashMap::new, Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponse> getTimetableByDay(Long dayId) {
        return scheduleRepository.findTimetableByDay(dayId).stream().map(scheduleService::toResponse).collect(Collectors.toList());
    }

    // ── GA Execution ──────────────────────────────────────────────────────────
    /**
     * Main GA entry point:
     *  1. Load all problem data from DB
     *  2. Construct and run the GeneticAlgorithm
     *  3. Save an AlgorithmRun audit record
     *  4. If applyResult=true, persist the best chromosome as Schedules
     */
    @Override
    @Transactional
    public AlgorithmRun runGeneticAlgorithm(GaRunRequest req, Long triggeredByUserId) {
        log.info("Starting Genetic Algorithm run | pop={} gen={} cr={} mr={}", req.getPopulationSize(), req.getMaxGenerations(), req.getCrossoverRate(), req.getMutationRate());
        // Load problem data
        List<Batch> batches = batchRepository.findAll();
        List<Subject> subjects = subjectRepository.findAll();
        List<Staff> staffList = staffRepository.findAll();
        List<Lab> labs = labRepository.findByAvailableTrue();
        List<Day> days = dayRepository.findByActiveTrueOrderByDayOrder();
        validateGaInputs(batches, subjects, staffList, labs, days);
        // Create audit record
        AlgorithmRun run = AlgorithmRun.builder().generations(req.getMaxGenerations()).populationSize(req.getPopulationSize()).crossoverRate(req.getCrossoverRate()).mutationRate(req.getMutationRate()).bestFitnessScore(0.0).status("RUNNING").scheduleApplied(false).triggeredBy(triggeredByUserId != null ? userRepository.findById(triggeredByUserId).orElse(null) : null).build();
        run = algorithmRunRepository.save(run);
        try {
            // Build and run GA
            GeneticAlgorithm ga = new GeneticAlgorithm(
            req.getPopulationSize(),
            req.getMaxGenerations(),
            req.getCrossoverRate(),
            req.getMutationRate(),
            batches,
            staffList,
            labs,
            days
        );
        Chromosome best = ga.run();
            // Update audit record
            run.setBestFitnessScore(best.getFitnessScore());
            run.setStatus("COMPLETED");
            run.setCompletedAt(LocalDateTime.now());
            run.setNotes(String.format("Fitness: %.4f | Genes: %d", best.getFitnessScore(), best.getGenes().size()));
            if (req.isApplyResult()) {
                persistBestChromosome(best, run);
                run.setScheduleApplied(true);
                log.info("GA result applied to schedule.");
            }
        } catch (Exception e) {
            run.setStatus("FAILED");
            run.setNotes("Error: " + e.getMessage());
            run.setCompletedAt(LocalDateTime.now());
            log.error("GA run failed: ", e);
        }
        return algorithmRunRepository.save(run);
    }

    /**
     * Applies the best chromosome from a previously completed GA run.
     * Deactivates the current schedule first.
     */
    @Override
    @Transactional
    public void applyGaResult(Long algorithmRunId) {
        AlgorithmRun run = algorithmRunRepository.findById(algorithmRunId).orElseThrow(() -> new ResourceNotFoundException("AlgorithmRun", "id", algorithmRunId));
        if (!"COMPLETED".equals(run.getStatus())) {
            throw new IllegalStateException("Cannot apply a GA run that is not COMPLETED");
        }
        if (run.isScheduleApplied()) {
            throw new IllegalStateException("This GA run has already been applied");
        }
        // Re-run GA with same parameters to get the best chromosome
        // (In production, you'd persist the chromosome; here we re-run for simplicity)
        GaRunRequest rerunReq = new GaRunRequest();
        rerunReq.setPopulationSize(run.getPopulationSize());
        rerunReq.setMaxGenerations(run.getGenerations());
        rerunReq.setCrossoverRate(run.getCrossoverRate());
        rerunReq.setMutationRate(run.getMutationRate());
        rerunReq.setApplyResult(true);
        runGeneticAlgorithm(rerunReq, run.getTriggeredBy() != null ? run.getTriggeredBy().getId() : null);
        run.setScheduleApplied(true);
        algorithmRunRepository.save(run);
        log.info("Applied GA run id={} to schedule", algorithmRunId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlgorithmRun> getAlgorithmRunHistory() {
        return algorithmRunRepository.findAllByOrderByStartedAtDesc();
    }

    // ── Internal: persist chromosome genes as Schedule rows ──────────────────
    /**
     * Deactivates all current schedules and saves the best GA chromosome
     * as a new set of active Schedule records.
     */
    private void persistBestChromosome(Chromosome best, AlgorithmRun run) {
        // Deactivate current timetable
        scheduleRepository.deactivateAllSchedules();
        log.info("Deactivated existing schedules. Saving {} GA-generated entries...", best.getGenes().size());
        List<Schedule> newSchedules = new ArrayList<>();
        for (Chromosome.Gene gene : best.getGenes()) {
            Schedule schedule = Schedule.builder().batch(gene.getBatch()).subject(gene.getSubject()).staff(gene.getStaff()).lab(gene.getLab()).day(gene.getDay()).startTime(gene.getStartTime()).endTime(gene.getEndTime()).generatedByGA(true).algorithmRun(run).active(true).build();
            newSchedules.add(schedule);
        }
        scheduleRepository.saveAll(newSchedules);
        log.info("Saved {} schedules from GA run id={}", newSchedules.size(), run.getId());
    }

    // ── Validation ────────────────────────────────────────────────────────────
    private void validateGaInputs(List<Batch> batches, List<Subject> subjects, List<Staff> staff, List<Lab> labs, List<Day> days) {
        List<String> missing = new ArrayList<>();
        if (batches.isEmpty()) missing.add("batches");
        if (subjects.isEmpty()) missing.add("subjects");
        if (staff.isEmpty()) missing.add("staff");
        if (labs.isEmpty()) missing.add("labs");
        if (days.isEmpty()) missing.add("days");
        if (!missing.isEmpty()) {
            throw new IllegalStateException("GA cannot run — missing data: " + String.join(", ", missing) + ". Please seed the database first.");
        }
    }

    public TimetableServiceImpl(final ScheduleRepository scheduleRepository, final AlgorithmRunRepository algorithmRunRepository, final BatchRepository batchRepository, final SubjectRepository subjectRepository, final StaffRepository staffRepository, final LabRepository labRepository, final DayRepository dayRepository, final UserRepository userRepository, final ScheduleService scheduleService) {
        this.scheduleRepository = scheduleRepository;
        this.algorithmRunRepository = algorithmRunRepository;
        this.batchRepository = batchRepository;
        this.subjectRepository = subjectRepository;
        this.staffRepository = staffRepository;
        this.labRepository = labRepository;
        this.dayRepository = dayRepository;
        this.userRepository = userRepository;
        this.scheduleService = scheduleService;
    }
}
