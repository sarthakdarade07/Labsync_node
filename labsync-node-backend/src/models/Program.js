const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');
const programSchema = new mongoose.Schema({
    _id: Number,
    name: { type: String, required: true },
    programCode: { type: String, required: true },
    durationYears: { type: Number, required: true }
}, { timestamps: true });
programSchema.plugin(autoIncrement, { model: 'Program' });
module.exports = mongoose.model('Program', programSchema);