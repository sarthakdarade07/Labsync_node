const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');
const batchSchema = new mongoose.Schema({
    _id: Number,
    batchName: { type: String, required: true },
    division: { type: String, required: true },
    studentCount: { type: Number, required: true },
    semester: { type: String },
    osRequirement: { type: String },
    labsPerWeek: { type: Number, required: true },
    requiredHours: { type: Number, required: true },
    totalHours: { type: Number },
    startTime: { type: String },
    endTime: { type: String },
    program: { type: Number, ref: 'Program' },
    academicYear: { type: Number, ref: 'AcademicYear' },
    subjects: [{ type: Number, ref: 'Subject' }]
}, { timestamps: true });
batchSchema.plugin(autoIncrement, { model: 'Batch' });
module.exports = mongoose.model('Batch', batchSchema);