const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');
const studentSchema = new mongoose.Schema({
    _id: Number,
    fullName: { type: String, required: true },
    prn: { type: String, required: true },
    email: { type: String },
    phone: { type: String },
    batch: { type: Number, ref: 'Batch' },
    program: { type: Number, ref: 'Program' },
    academicYear: { type: Number, ref: 'AcademicYear' }
}, { timestamps: true });
studentSchema.plugin(autoIncrement, { model: 'Student' });
module.exports = mongoose.model('Student', studentSchema);