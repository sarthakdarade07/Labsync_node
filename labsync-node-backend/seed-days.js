const mongoose = require('mongoose');
const Day = require('./src/models/Day');

mongoose.connect('mongodb://localhost:27017/labsync').then(async () => {
    try {
        const days = [
            { dayName: 'Monday', dayOfWeek: 1 },
            { dayName: 'Tuesday', dayOfWeek: 2 },
            { dayName: 'Wednesday', dayOfWeek: 3 },
            { dayName: 'Thursday', dayOfWeek: 4 },
            { dayName: 'Friday', dayOfWeek: 5 },
            { dayName: 'Saturday', dayOfWeek: 6 }
        ];
        
        for (const d of days) {
            const exists = await Day.findOne({ dayName: d.dayName });
            if (!exists) {
                await Day.create(d);
            }
        }
        console.log('Days seeded successfully');
    } catch(err) {
        console.error(err);
    }
    process.exit(0);
});
