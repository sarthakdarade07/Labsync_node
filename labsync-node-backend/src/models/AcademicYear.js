const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');

const academicYearSchema = new mongoose.Schema({
    _id: Number,
    year: { type: String, required: true, unique: true },
    isActive: { type: Boolean, default: false }
}, { timestamps: true });

academicYearSchema.plugin(autoIncrement, { model: 'AcademicYear' });
module.exports = mongoose.model('AcademicYear', academicYearSchema);