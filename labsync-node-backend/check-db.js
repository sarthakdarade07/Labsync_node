const mongoose = require('mongoose');
const Schedule = require('./src/models/Schedule');
const Batch = require('./src/models/Batch');
const Subject = require('./src/models/Subject');
const Lab = require('./src/models/Lab');
const Day = require('./src/models/Day');

require('dotenv').config();

async function check() {
    await mongoose.connect(process.env.MONGODB_URI);
    
    const schedules = await Schedule.find()
        .populate('batch subject lab day')
        .lean();
        
    console.log(`Found ${schedules.length} schedules`);
    
    let doubleBooked = 0;
    
    let map = {};
    for (let s of schedules) {
        if (!s.day || !s.lab) continue;
        let key = s.day._id + '_' + s.startTime + '_' + s.lab._id;
        if (!map[key]) map[key] = [];
        map[key].push(s);
    }
    
    for (let key in map) {
        if (map[key].length > 1) {
            console.log(`DOUBLE BOOKING FOUND: ${key}`);
            for (let s of map[key]) {
                console.log(`  Batch: ${s.batch ? s.batch.batchName : 'none'}, Lab: ${s.lab.labName}`);
            }
            doubleBooked++;
        }
    }
    
    console.log(`Total double booked labs: ${doubleBooked}`);
    process.exit(0);
}

check();
