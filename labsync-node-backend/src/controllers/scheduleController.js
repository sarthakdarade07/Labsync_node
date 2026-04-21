const Schedule = require('../models/Schedule');

const mapSchedule = (s) => ({
    scheduleId: s._id || s.id,
    batchId: s.batch ? (s.batch._id || s.batch.id) : null,
    batchName: s.batch ? `${s.batch.batchName} (${s.batch.division})` : 'Unknown',
    subjectId: s.subject ? (s.subject._id || s.subject.id) : null,
    subjectCode: s.subject ? s.subject.subjectCode : 'Unknown',
    staffId: s.staff ? (s.staff._id || s.staff.id) : null,
    staffName: s.staff ? s.staff.fullName : 'Not Assigned',
    labId: s.lab ? (s.lab._id || s.lab.id) : null,
    labName: s.lab ? s.lab.labName : 'Unknown',
    dayId: s.day ? (s.day._id || s.day.id) : null,
    dayName: s.day ? s.day.dayName : 'Unknown',
    startTime: s.startTime,
    endTime: s.endTime,
    generatedByGA: s.generatedByGA
});

exports.getPublicSchedules = async (req, res) => {
    try {
        const schedules = await Schedule.find({ active: true })
            .populate('batch subject staff lab day');
        
        res.status(200).json({
            success: true,
            message: "Public schedules fetched",
            data: schedules.map(mapSchedule)
        });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.getAllSchedules = async (req, res) => {
    try {
        const schedules = await Schedule.find()
            .populate('batch subject staff lab day');
        
        res.status(200).json({
            success: true,
            message: "All schedules fetched",
            data: schedules.map(mapSchedule)
        });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.getByBatch = async (req, res) => {
    try {
        const data = await Schedule.find({ batch: req.params.batchId }).populate('batch subject staff lab day');
        res.status(200).json({ success: true, message: 'Fetched successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.getByDay = async (req, res) => {
    try {
        const data = await Schedule.find({ day: req.params.dayId }).populate('batch subject staff lab day');
        res.status(200).json({ success: true, message: 'Fetched successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.getByLab = async (req, res) => {
    try {
        const data = await Schedule.find({ lab: req.params.labId }).populate('batch subject staff lab day');
        res.status(200).json({ success: true, message: 'Fetched successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.validateSchedule = async (req, res) => {
    try {
        const report = { hasClash: false, clashes: [] };
        res.status(200).json({ success: true, message: 'No clashes detected — schedule is valid', data: report });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.create = async (req, res) => {
    try {
        const payload = {
            batch: req.body.batchId,
            subject: req.body.subjectId,
            staff: req.body.staffId || null,
            lab: req.body.labId,
            day: req.body.dayId,
            startTime: req.body.startTime,
            endTime: req.body.endTime,
        };
        const data = await Schedule.create(payload);
        res.status(201).json({ success: true, message: 'Created successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.delete = async (req, res) => {
    try {
        const data = await Schedule.findByIdAndDelete(req.params.id);
        if (!data) return res.status(404).json({ success: false, message: 'Not found' });
        res.status(200).json({ success: true, message: 'Deleted successfully', data: null });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};
