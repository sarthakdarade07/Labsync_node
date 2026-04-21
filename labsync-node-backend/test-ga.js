const mongoose = require('mongoose');
const Batch = require('./src/models/Batch');
const Lab = require('./src/models/Lab');
const Staff = require('./src/models/Staff');
const Day = require('./src/models/Day');
const TimeSlot = require('./src/models/TimeSlot');
const Subject = require('./src/models/Subject');
const GeneticAlgorithm = require('./src/utils/geneticAlgorithm');

require('dotenv').config();

async function runGA() {
    try {
        await mongoose.connect(process.env.MONGODB_URI);
        console.log("Connected to DB");

        const batches = await Batch.find().populate('subjects');
        const labs = await Lab.find({ available: true });
        const staff = await Staff.find();
        const days = await Day.find({ active: true });
        const timeSlots = await TimeSlot.find({ active: true });

        console.log(`Batches: ${batches.length}, Labs: ${labs.length}, Staff: ${staff.length}, Days: ${days.length}, TimeSlots: ${timeSlots.length}`);

        if (batches.length === 0 || labs.length === 0 || staff.length === 0 || days.length === 0 || timeSlots.length === 0) {
            console.log('Missing required data');
            process.exit(1);
        }

        const ga = new GeneticAlgorithm(batches, labs, staff, days, timeSlots);
        ga.MAX_GENERATIONS = 50;
        console.time("GA Run");
        const bestSchedule = await ga.run();
        console.timeEnd("GA Run");

        console.log(`Best Fitness: ${bestSchedule.fitness}, Conflicts: ${bestSchedule.conflicts}`);
        console.log(`Number of genes scheduled: ${bestSchedule.genes.length}`);

        let timeSlotGroups = {};
        for (let gene of bestSchedule.genes) {
            let key = gene.dayId + '_' + gene.startTime;
            if (!timeSlotGroups[key]) timeSlotGroups[key] = [];
            timeSlotGroups[key].push(gene);
        }

        let doubleBookings = 0;
        for (let key in timeSlotGroups) {
            let genes = timeSlotGroups[key];
            let labSet = new Set();
            for (let g of genes) {
                for (let labId of g.labIds) {
                    if (labSet.has(labId)) {
                        console.log(`DOUBLE BOOKING in GA result! Lab ${labId} at ${key}`);
                        doubleBookings++;
                    }
                    labSet.add(labId);
                }
            }
        }
        console.log(`Total GA double bookings: ${doubleBookings}`);

        process.exit(0);
    } catch (e) {
        console.error(e);
        process.exit(1);
    }
}

runGA();