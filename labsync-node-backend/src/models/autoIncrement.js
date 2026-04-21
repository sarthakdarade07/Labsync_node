const mongoose = require('mongoose');

const counterSchema = new mongoose.Schema({
    _id: { type: String, required: true },
    seq: { type: Number, default: 0 }
});
const Counter = mongoose.model('Counter', counterSchema);

module.exports = function autoIncrement(schema, options) {
    const modelName = options.model;
    
    schema.pre('save', async function() {
        if (!this.isNew) {
            return;
        }
        const counter = await Counter.findByIdAndUpdate(
            modelName,
            { $inc: { seq: 1 } },
            { new: true, upsert: true, returnDocument: 'after' }
        );
        this._id = counter.seq;
    });
};
