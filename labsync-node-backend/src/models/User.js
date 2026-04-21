const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');

const userSchema = new mongoose.Schema({
    _id: Number,
    username: { type: String, required: true, unique: true },
    email: { type: String, required: true, unique: true },
    password: { type: String, required: true },
    fullName: { type: String, required: true },
    enabled: { type: Boolean, default: true },
    roles: [{ type: Number, ref: 'Role' }]
}, { timestamps: true });

userSchema.plugin(autoIncrement, { model: 'User' });
module.exports = mongoose.model('User', userSchema);