const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');

const roleSchema = new mongoose.Schema({
    _id: Number,
    name: { type: String, required: true, unique: true },
    description: { type: String }
}, { timestamps: true });

roleSchema.plugin(autoIncrement, { model: 'Role' });
module.exports = mongoose.model('Role', roleSchema);