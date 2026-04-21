const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');
const staffSchema = new mongoose.Schema({
    _id: Number,
    fullName: { type: String, required: true },
    employeeId: { type: String, required: true },
    email: { type: String, required: true },
    phone: { type: String },
    department: { type: String },
    designation: { type: String },
    user: { type: Number, ref: 'User' }
}, { timestamps: true });
staffSchema.plugin(autoIncrement, { model: 'Staff' });
module.exports = mongoose.model('Staff', staffSchema);