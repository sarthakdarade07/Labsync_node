const Batch = require('../models/Batch');
require('../models/Program');
require('../models/AcademicYear');
require('../models/Subject');

exports.getAll = async (req, res) => {
    try {
        const data = await Batch.find().populate('program academicYear subjects');
        res.status(200).json({ success: true, message: 'Fetched successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.getById = async (req, res) => {
    try {
        const data = await Batch.findById(req.params.id).populate('program academicYear subjects');
        if (!data) return res.status(404).json({ success: false, message: 'Not found' });
        res.status(200).json({ success: true, message: 'Fetched successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.create = async (req, res) => {
    try {
        const data = await Batch.create(req.body);
        res.status(201).json({ success: true, message: 'Created successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.update = async (req, res) => {
    try {
        const data = await Batch.findByIdAndUpdate(req.params.id, req.body, { new: true });
        if (!data) return res.status(404).json({ success: false, message: 'Not found' });
        res.status(200).json({ success: true, message: 'Updated successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.delete = async (req, res) => {
    try {
        const data = await Batch.findByIdAndDelete(req.params.id);
        if (!data) return res.status(404).json({ success: false, message: 'Not found' });
        res.status(200).json({ success: true, message: 'Deleted successfully', data: null });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};
