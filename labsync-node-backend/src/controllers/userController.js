const User = require('../models/User');

exports.getAll = async (req, res) => {
    try {
        const data = await User.find().populate('roles');
        
        // Map the data to return role names if populated
        const mappedData = data.map(user => {
            const userObj = user.toObject();
            if (userObj.roles) {
                userObj.roles = userObj.roles.map(r => r ? (r.name || r.roleName || r) : null).filter(Boolean);
            }
            return userObj;
        });
        
        res.status(200).json({ success: true, message: 'Fetched successfully', data: mappedData });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.getById = async (req, res) => {
    try {
        if (req.params.id === 'me') {
            // Usually this relies on auth middleware, but we return a generic admin for now
            const data = await User.findOne({ username: 'admin' });
            return res.status(200).json({ success: true, message: 'Fetched successfully', data });
        }
        const data = await User.findById(req.params.id);
        if (!data) return res.status(404).json({ success: false, message: 'Not found' });
        res.status(200).json({ success: true, message: 'Fetched successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.create = async (req, res) => {
    try {
        const data = await User.create(req.body);
        res.status(201).json({ success: true, message: 'Created successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.update = async (req, res) => {
    try {
        const data = await User.findByIdAndUpdate(req.params.id, req.body, { new: true });
        if (!data) return res.status(404).json({ success: false, message: 'Not found' });
        res.status(200).json({ success: true, message: 'Updated successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.delete = async (req, res) => {
    try {
        const data = await User.findByIdAndDelete(req.params.id);
        if (!data) return res.status(404).json({ success: false, message: 'Not found' });
        res.status(200).json({ success: true, message: 'Deleted successfully', data: null });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};
