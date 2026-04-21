const mongoose = require('mongoose');
const TimeSlot = require('./src/models/TimeSlot');

mongoose.connect('mongodb://localhost:27017/labsync').then(async () => {
    try {
        const slots = [
            { slotLabel: 'Slot 1', startTime: '09:00', endTime: '11:00' },
            { slotLabel: 'Slot 2', startTime: '11:15', endTime: '13:15' },
            { slotLabel: 'Slot 3', startTime: '14:00', endTime: '16:00' },
            { slotLabel: 'Slot 4', startTime: '16:15', endTime: '18:15' }
        ];
        
        for (const s of slots) {
            const exists = await TimeSlot.findOne({ slotLabel: s.slotLabel });
            if (!exists) {
                await TimeSlot.create(s);
            }
        }
        console.log('TimeSlots seeded successfully');
    } catch(err) {
        console.error(err);
    }
    process.exit(0);
});
