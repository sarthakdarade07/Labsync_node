const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');

const labSchema = new mongoose.Schema({
    _id: Number,
    labName: { type: String, required: true },
    capacity: { type: Number, required: true },
    totalComputers: { type: Number, required: true },
    workingComputers: { type: Number, required: true },
    faultyComputers: { type: Number, default: 0 },
    osType: { type: String },
    location: { type: String },
    available: { type: Boolean, default: true }
}, { timestamps: true });

labSchema.plugin(autoIncrement, { model: 'Lab' });
module.exports = mongoose.model('Lab', labSchema);