const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');
const systemSettingSchema = new mongoose.Schema({
    _id: Number,
    settingKey: { type: String, required: true, unique: true },
    settingValue: { type: String, required: true }
}, { timestamps: true });
systemSettingSchema.plugin(autoIncrement, { model: 'SystemSetting' });
module.exports = mongoose.model('SystemSetting', systemSettingSchema);