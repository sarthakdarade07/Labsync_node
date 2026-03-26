package com.labsync.lms.config;

import com.labsync.lms.model.*;
import com.labsync.lms.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

/**
 * DataInitializer — seeds reference data on application startup.
 *
 * Idempotent: only inserts records that don't already exist.
 *
 * Seeds:
 *  1. Roles           — ROLE_ADMIN, ROLE_STAFF
 *  2. Days            — Monday … Saturday
 *  3. Time Slots      — 5 slots covering 08:00–18:00
 *  4. Default admin   — username: admin / password: admin123
 *  5. Sample program  — "B.Tech Computer Science"
 *  6. Sample academic year — "2024-25"
 *
 * Change the default admin password immediately after first login.
 */
@Component
public class DataInitializer implements CommandLineRunner {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DataInitializer.class);
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ProgramRepository programRepository;
    private final AcademicYearRepository academicYearRepository;
    private final PasswordEncoder passwordEncoder;
    private final DayRepository dayRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final LabRepository labRepository;
    private final StaffRepository staffRepository;
    private final SubjectRepository subjectRepository;
    private final BatchRepository batchRepository;

    @Override
    @Transactional
    public void run(String... args) {
        seedRoles();
        seedDays();
        seedTimeSlots();
        seedDefaultAdmin();
        seedDefaultStaff();
        seedSampleProgram();
        seedSampleAcademicYear();
        seedSampleLabs();
        seedSampleStaff();
        seedSampleSubjects();
        seedSampleBatches();
        log.info("=======================================================");
        log.info("  LabSync LMS started successfully");
        log.info("  Default admin login: admin / admin123");
        log.info("  Default staff login: staff / staff123");
        log.info("  ⚠  Change the default password after first login!");
        log.info("=======================================================");
    }

    // ── 1. Roles ──────────────────────────────────────────────────────────────
    private void seedRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(List.of(new Role(null, Role.ERole.ROLE_ADMIN), new Role(null, Role.ERole.ROLE_STAFF)));
            log.info("Seeded roles: ROLE_ADMIN, ROLE_STAFF");
        }
    }

    // ── 2. Days ───────────────────────────────────────────────────────────────
    private void seedDays() {
        if (dayRepository.count() == 0) {
            dayRepository.saveAll(List.of(Day.builder().dayName("Monday").dayOrder(1).active(true).build(), Day.builder().dayName("Tuesday").dayOrder(2).active(true).build(), Day.builder().dayName("Wednesday").dayOrder(3).active(true).build(), Day.builder().dayName("Thursday").dayOrder(4).active(true).build(), Day.builder().dayName("Friday").dayOrder(5).active(true).build(), Day.builder().dayName("Saturday").dayOrder(6).active(true).build()));
            log.info("Seeded 6 days (Monday–Saturday)");
        }
    }

    // ── 3. Time Slots ─────────────────────────────────────────────────────────
    private void seedTimeSlots() {
        if (timeSlotRepository.count() == 0) {
            timeSlotRepository.saveAll(List.of(TimeSlot.builder().slotLabel("Slot-1").startTime(LocalTime.of(8, 0)).endTime(LocalTime.of(10, 0)).active(true).build(), TimeSlot.builder().slotLabel("Slot-2").startTime(LocalTime.of(10, 0)).endTime(LocalTime.of(12, 0)).active(true).build(), TimeSlot.builder().slotLabel("Slot-3").startTime(LocalTime.of(13, 0)).endTime(LocalTime.of(15, 0)).active(true).build(), TimeSlot.builder().slotLabel("Slot-4").startTime(LocalTime.of(15, 0)).endTime(LocalTime.of(17, 0)).active(true).build(), TimeSlot.builder().slotLabel("Slot-5").startTime(LocalTime.of(17, 0)).endTime(LocalTime.of(19, 0)).active(true).build()));
            log.info("Seeded 5 time slots (08:00–19:00, 2 hrs each)");
        }
    }

    // ── 4. Default Admin User ─────────────────────────────────────────────────
    private void seedDefaultAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            Role adminRole = roleRepository.findByName(Role.ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));
            User admin = User.builder().username("admin").email("admin@labsync.com").password(passwordEncoder.encode("admin123")).fullName("System Administrator").roles(Set.of(adminRole)).enabled(true).build();
            userRepository.save(admin);
            log.info("Seeded default admin user (username=admin)");
        }
    }

    private void seedDefaultStaff() {
        if (!userRepository.existsByUsername("staff")) {
            Role staffRole = roleRepository.findByName(Role.ERole.ROLE_STAFF).orElseThrow(() -> new RuntimeException("ROLE_STAFF not found"));
            User staff = User.builder().username("staff").email("staff@labsync.com").password(passwordEncoder.encode("staff123")).fullName("Lab Staff Member").roles(Set.of(staffRole)).enabled(true).build();
            userRepository.save(staff);
            log.info("Seeded default staff user (username=staff)");
        }
    }

    // ── 5. Sample Program ─────────────────────────────────────────────────────
    private void seedSampleProgram() {
        if (!programRepository.existsByCode("BTECH-CS")) {
            programRepository.save(Program.builder().name("B.Tech Computer Science").code("BTECH-CS").description("Bachelor of Technology in Computer Science and Engineering").build());
            log.info("Seeded sample program: B.Tech Computer Science");
        }
        if (!programRepository.existsByCode("MCA")) {
            programRepository.save(Program.builder().name("Master of Computer Applications").code("MCA").description("Master\'s degree in Computer Applications").build());
            log.info("Seeded sample program: MCA");
        }
    }

    // ── 6. Sample Academic Year ───────────────────────────────────────────────
    private void seedSampleAcademicYear() {
        if (academicYearRepository.findByYearLabel("2024-25").isEmpty()) {
            academicYearRepository.save(AcademicYear.builder().yearLabel("2024-25").startYear(2024).endYear(2025).active(true).build());
            log.info("Seeded academic year: 2024-25");
        }
        if (academicYearRepository.findByYearLabel("2025-26").isEmpty()) {
            academicYearRepository.save(AcademicYear.builder().yearLabel("2025-26").startYear(2025).endYear(2026).active(true).build());
            log.info("Seeded academic year: 2025-26");
        }
    }

    // ── 7. Sample Labs ────────────────────────────────────────────────────────
    private void seedSampleLabs() {
        if (labRepository.count() == 0) {
            labRepository.saveAll(List.of(
                Lab.builder().labName("Lab A - Ground Floor").capacity(40).totalComputers(40).workingComputers(38).faultyComputers(2).build(), 
                Lab.builder().labName("Lab B - First Floor").capacity(35).totalComputers(35).workingComputers(30).faultyComputers(5).build(), 
                Lab.builder().labName("Lab C - Second Floor").capacity(45).totalComputers(45).workingComputers(45).faultyComputers(0).build()
            ));
            log.info("Seeded 3 sample labs");
        }
    }

    // ── 8. Sample Staff ────────────────────────────────────────────────────────
    private void seedSampleStaff() {
        if (staffRepository.count() == 0) {
            staffRepository.saveAll(List.of(
                Staff.builder().fullName("Prof. A. Sharma").designation("Assistant Professor").email("a.sharma@mitwpu.edu.in").employeeId("EMP101").build(),
                Staff.builder().fullName("Prof. B. Kulkarni").designation("Associate Professor").email("b.kulkarni@mitwpu.edu.in").employeeId("EMP102").build(),
                Staff.builder().fullName("Prof. C. Patil").designation("Assistant Professor").email("c.patil@mitwpu.edu.in").employeeId("EMP103").build(),
                Staff.builder().fullName("Prof. D. Mehta").designation("Professor").email("d.mehta@mitwpu.edu.in").employeeId("EMP104").build()
            ));
            log.info("Seeded 4 sample staff members (faculty)");
        }
    }

    // ── 9. Sample Subjects ────────────────────────────────────────────────────────
    private void seedSampleSubjects() {
        if (subjectRepository.count() == 0) {
            subjectRepository.saveAll(List.of(
                Subject.builder().subjectCode("CSL301").name("Data Structures Lab").hoursPerWeek(2).build(),
                Subject.builder().subjectCode("CSL302").name("DBMS Lab").hoursPerWeek(2).build(),
                Subject.builder().subjectCode("CSL401").name("Operating Systems Lab").hoursPerWeek(2).build(),
                Subject.builder().subjectCode("CSL402").name("Computer Networks Lab").hoursPerWeek(2).build(),
                Subject.builder().subjectCode("CSL501").name("Machine Learning Lab").hoursPerWeek(2).build(),
                Subject.builder().subjectCode("CSL502").name("Web Technologies Lab").hoursPerWeek(2).build()
            ));
            log.info("Seeded 6 sample subjects");
        }
    }

    // ── 10. Sample Batches ────────────────────────────────────────────────────────
    private void seedSampleBatches() {
        if (batchRepository.count() == 0) {
            Program program = programRepository.findByCode("BTECH-CS").orElseThrow();
            AcademicYear year = academicYearRepository.findByYearLabel("2024-25").orElseThrow();
            
            batchRepository.saveAll(List.of(
                Batch.builder().batchName("Batch A1").division("CSE-A").studentCount(30).semester("SEM-3").program(program).academicYear(year).build(),
                Batch.builder().batchName("Batch A2").division("CSE-A").studentCount(28).semester("SEM-3").program(program).academicYear(year).build(),
                Batch.builder().batchName("Batch B1").division("CSE-B").studentCount(32).semester("SEM-5").program(program).academicYear(year).build(),
                Batch.builder().batchName("Batch B2").division("CSE-B").studentCount(29).semester("SEM-5").program(program).academicYear(year).build(),
                Batch.builder().batchName("Batch C1").division("CSE-C").studentCount(25).semester("SEM-7").program(program).academicYear(year).build()
            ));
            log.info("Seeded 5 sample batches");
        }
    }

    public DataInitializer(final RoleRepository roleRepository, final UserRepository userRepository, final ProgramRepository programRepository, final AcademicYearRepository academicYearRepository, final PasswordEncoder passwordEncoder, final DayRepository dayRepository, final TimeSlotRepository timeSlotRepository, final LabRepository labRepository, final StaffRepository staffRepository, final SubjectRepository subjectRepository, final BatchRepository batchRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.programRepository = programRepository;
        this.academicYearRepository = academicYearRepository;
        this.passwordEncoder = passwordEncoder;
        this.dayRepository = dayRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.labRepository = labRepository;
        this.staffRepository = staffRepository;
        this.subjectRepository = subjectRepository;
        this.batchRepository = batchRepository;
    }
}
