const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');
const subjectSchema = new mongoose.Schema({
    _id: Number,
    name: { type: String, required: true },
    subjectCode: { type: String, required: true },
    description: { type: String },
    hoursPerWeek: { type: Number, required: true },
    program: { type: Number, ref: 'Program' }
}, { timestamps: true });
subjectSchema.plugin(autoIncrement, { model: 'Subject' });
module.exports = mongoose.model('Subject', subjectSchema);