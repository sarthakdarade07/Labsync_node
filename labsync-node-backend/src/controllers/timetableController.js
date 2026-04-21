const Timetable = require('../models/Timetable');

exports.getActive = async (req, res) => {
    try {
        const data = await Timetable.findOne({ active: true });
        if (!data) return res.status(404).json({ success: false, message: 'No active timetable found' });
        res.status(200).json({ success: true, message: 'Fetched successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

// Generic CRUD implementations
exports.getAll = async (req, res) => {
    try {
        const data = await Timetable.find();
        res.status(200).json({ success: true, message: 'Fetched successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.create = async (req, res) => {
    try {
        const data = await Timetable.create(req.body);
        res.status(201).json({ success: true, message: 'Created successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};
