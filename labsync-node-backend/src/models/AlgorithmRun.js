const mongoose = require('mongoose');
const autoIncrement = require('./autoIncrement');

const algorithmRunSchema = new mongoose.Schema({
    _id: Number,
    executionTimeMs: { type: Number },
    bestFitnessScore: { type: Number },
    status: { type: String, enum: ['SUCCESS', 'FAILED', 'RUNNING'], default: 'SUCCESS' },
    parameters: { type: mongoose.Schema.Types.Mixed },
    logs: { type: String }
}, { timestamps: true });

algorithmRunSchema.plugin(autoIncrement, { model: 'AlgorithmRun' });
module.exports = mongoose.model('AlgorithmRun', algorithmRunSchema);