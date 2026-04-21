const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');
const timeSlotSchema = new mongoose.Schema({
    _id: Number,
    slotLabel: { type: String, required: true },
    startTime: { type: String, required: true }, 
    endTime: { type: String, required: true },   
    active: { type: Boolean, default: true }
}, { timestamps: true });
timeSlotSchema.plugin(autoIncrement, { model: 'TimeSlot' });
module.exports = mongoose.model('TimeSlot', timeSlotSchema);