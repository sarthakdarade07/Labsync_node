const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');

const scheduleSchema = new mongoose.Schema({
    _id: Number,
    batch: { type: Number, ref: 'Batch', required: true },
    subject: { type: Number, ref: 'Subject', required: true },
    staff: { type: Number, ref: 'Staff' },
    lab: { type: Number, ref: 'Lab' },
    day: { type: Number, ref: 'Day', required: true },
    startTime: { type: String, required: true },
    endTime: { type: String, required: true },
    generatedByGA: { type: Boolean, default: false },
    algorithmRun: { type: Number, ref: 'AlgorithmRun' },
    active: { type: Boolean, default: true }
}, { timestamps: true });

scheduleSchema.plugin(autoIncrement, { model: 'Schedule' });
module.exports = mongoose.model('Schedule', scheduleSchema);