const Lab = require('../models/Lab');
const Schedule = require('../models/Schedule');

exports.getAll = async (req, res) => {
    try {
        const data = await Lab.find();
        res.status(200).json({ success: true, message: 'Fetched successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.searchAvailable = async (req, res) => {
    try {
        const { dayId, startTime, endTime, capacity, osType } = req.query;
        
        let query = {};
        if (capacity) {
            query.workingComputers = { $gte: Number(capacity) };
        }
        if (osType && osType.trim() !== '') {
            query.osType = osType;
        }
        
        const eligibleLabs = await Lab.find(query);
        
        if (!dayId || !startTime || !endTime) {
            return res.status(200).json({ success: true, data: eligibleLabs });
        }
        
        const conflictingSchedules = await Schedule.find({
            day: dayId,
            $or: [
                { startTime: { $lt: endTime }, endTime: { $gt: startTime } }
            ]
        });
        
        const conflictingLabIds = conflictingSchedules.map(s => s.lab.toString());
        const availableLabs = eligibleLabs.filter(lab => !conflictingLabIds.includes(lab._id.toString()));
        
        res.status(200).json({ success: true, message: 'Fetched successfully', data: availableLabs });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.getById = async (req, res) => {
    try {
        const data = await Lab.findById(req.params.id);
        if (!data) return res.status(404).json({ success: false, message: 'Not found' });
        res.status(200).json({ success: true, message: 'Fetched successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.create = async (req, res) => {
    try {
        const data = await Lab.create(req.body);
        res.status(201).json({ success: true, message: 'Created successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.update = async (req, res) => {
    try {
        const data = await Lab.findByIdAndUpdate(req.params.id, req.body, { new: true });
        if (!data) return res.status(404).json({ success: false, message: 'Not found' });
        res.status(200).json({ success: true, message: 'Updated successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.delete = async (req, res) => {
    try {
        const data = await Lab.findByIdAndDelete(req.params.id);
        if (!data) return res.status(404).json({ success: false, message: 'Not found' });
        res.status(200).json({ success: true, message: 'Deleted successfully', data: null });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};
