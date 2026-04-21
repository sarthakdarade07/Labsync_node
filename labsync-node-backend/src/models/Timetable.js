const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');

const timetableSchema = new mongoose.Schema({
    _id: Number,
    name: { type: String, required: true },
    academicTerm: { type: String },
    active: { type: Boolean, default: false }
}, { timestamps: true });

timetableSchema.plugin(autoIncrement, { model: 'Timetable' });
module.exports = mongoose.model('Timetable', timetableSchema);
