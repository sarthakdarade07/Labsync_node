const Student = require('../models/Student');
const Staff = require('../models/Staff');
const Lab = require('../models/Lab');
const Batch = require('../models/Batch');
const Subject = require('../models/Subject');
const Schedule = require('../models/Schedule');
const Day = require('../models/Day');
const TimeSlot = require('../models/TimeSlot');
const AlgorithmRun = require('../models/AlgorithmRun');
const GeneticAlgorithm = require('../utils/geneticAlgorithm');

exports.getDashboardStats = async (req, res) => {
    try {
        const totalStudents = await Student.countDocuments();
        const totalStaff = await Staff.countDocuments();
        const totalLabs = await Lab.countDocuments();
        const totalBatches = await Batch.countDocuments();
        const activeSchedules = await Schedule.countDocuments({ active: true });

        const data = {
            totalStudents,
            totalStaff,
            totalLabs,
            totalBatches,
            activeSchedules,
            upcomingClasses: 0 // Mock for now
        };

        res.status(200).json({ success: true, message: 'Stats fetched successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.regenerateTimetable = async (req, res) => {
    const startTime = Date.now();
    try {
        const batches = await Batch.find().populate('subjects');
        const labs = await Lab.find({ available: true });
        const staff = await Staff.find();
        const days = await Day.find({ active: true });
        const timeSlots = await TimeSlot.find({ active: true });

        if (batches.length === 0 || labs.length === 0 || staff.length === 0 || days.length === 0 || timeSlots.length === 0) {
            return res.status(400).json({ success: false, message: 'Missing required data (Batches, Labs, Staff, Days, or TimeSlots) to run the algorithm.' });
        }

        const ga = new GeneticAlgorithm(batches, labs, staff, days, timeSlots);
        const bestSchedule = await ga.run();

        await Schedule.deleteMany({}); // Clear old schedules

        const newSchedules = [];
        for (const gene of bestSchedule.genes) {
            for (const labId of gene.labIds) {
                newSchedules.push({
                    batch: gene.batchId,
                    subject: gene.subjectId,
                    staff: gene.staffId,
                    lab: labId,
                    day: gene.dayId,
                    startTime: gene.startTime,
                    endTime: gene.endTime,
                    generatedByGA: true
                });
            }
        }

        for (const sched of newSchedules) {
            await Schedule.create(sched);
        }

        const executionTimeMs = Date.now() - startTime;
        
        await AlgorithmRun.create({
            executionTimeMs,
            bestFitnessScore: bestSchedule.fitness,
            status: 'SUCCESS',
            logs: `Generated ${newSchedules.length} schedule sessions with ${bestSchedule.conflicts} conflicts.`
        });

        res.status(200).json({ 
            success: true, 
            message: `Genetic Algorithm completed. ${newSchedules.length} sessions created with ${bestSchedule.conflicts} conflicts.`,
            data: {
                fitness: bestSchedule.fitness,
                conflicts: bestSchedule.conflicts,
                executionTimeMs
            }
        });
    } catch (error) {
        await AlgorithmRun.create({
            executionTimeMs: Date.now() - startTime,
            status: 'FAILED',
            logs: error.message
        });
        res.status(500).json({ success: false, message: error.message });
    }
};
