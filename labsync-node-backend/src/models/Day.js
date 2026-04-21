const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');
const daySchema = new mongoose.Schema({
    _id: Number,
    dayName: { type: String, required: true },
    dayOfWeek: { type: Number, required: true },
    active: { type: Boolean, default: true }
}, { timestamps: true });
daySchema.plugin(autoIncrement, { model: 'Day' });
module.exports = mongoose.model('Day', daySchema);