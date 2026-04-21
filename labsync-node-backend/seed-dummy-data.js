const mongoose = require('mongoose');
require('dotenv').config();

const Staff = require('./src/models/Staff');
const Lab = require('./src/models/Lab');
const Batch = require('./src/models/Batch');
const Subject = require('./src/models/Subject');
const Program = require('./src/models/Program');
const AcademicYear = require('./src/models/AcademicYear');

const seedDummyData = async () => {
    try {
        await mongoose.connect(process.env.MONGODB_URI);
        console.log('MongoDB Connected for Seeding Dummy Data...');

        // Drop entire database to reset all auto-increment counters and collections
        await mongoose.connection.db.dropDatabase();
        console.log('Dropped database to reset all auto-increment counters.');

        // 1. Generate Programs
        const p1 = await new Program({ name: 'Computer Engineering', programCode: 'CE', durationYears: 4 }).save();
        const p2 = await new Program({ name: 'Information Technology', programCode: 'IT', durationYears: 4 }).save();
        console.log('Inserted Programs');

        // 2. Generate Academic Years
        const a1 = await new AcademicYear({ year: '2023-2024', active: false }).save();
        const a2 = await new AcademicYear({ year: '2024-2025', active: true }).save();
        console.log('Inserted Academic Years');

        // 3. Generate Subjects
        const subjects = [];
        for (let i = 1; i <= 5; i++) {
            subjects.push(await new Subject({
                name: 'Core Subject ' + i,
                subjectCode: 'CS' + (100 + i),
                description: 'Description for subject ' + i,
                hoursPerWeek: 4,
                program: p1._id
            }).save());
        }
        console.log('Inserted Subjects');

        // 4. Generate 10 Staff Members
        for (let i = 1; i <= 10; i++) {
            await new Staff({
                fullName: "Dr. Staff Member " + i,
                employeeId: "EMP" + (1000 + i),
                email: "staff" + i + "@labsync.com",
                phone: "123-456-78" + i.toString().padStart(2, '0'),
                department: i % 2 === 0 ? 'Computer Science' : 'Information Technology',
                designation: 'Assistant Professor'
            }).save();
        }
        console.log('Inserted 10 Staff members');

        // 5. Generate 20 Labs
        const osTypes = ['Windows 11', 'Ubuntu Linux', 'macOS', 'Any'];
        for (let i = 1; i <= 20; i++) {
            const capacity = 30 + Math.floor(Math.random() * 30);
            await new Lab({
                labName: "Lab-" + (100 + i),
                location: "Building A, Room " + i,
                capacity: capacity,
                totalComputers: capacity,
                workingComputers: capacity - Math.floor(Math.random() * 5),
                faultyComputers: Math.floor(Math.random() * 5),
                osType: osTypes[Math.floor(Math.random() * osTypes.length)],
                available: true
            }).save();
        }
        console.log('Inserted 20 Labs');

        // 6. Generate 10 Batches
        const divisions = ['A', 'B', 'C', 'D'];
        for (let i = 1; i <= 10; i++) {
            await new Batch({
                batchName: "Batch 2024-" + String.fromCharCode(64 + Math.ceil(i/4)) + " " + (i % 4 + 1),
                division: divisions[i % 4],
                studentCount: 20 + Math.floor(Math.random() * 40),
                semester: 'SEM-' + (Math.floor(Math.random() * 8) + 1),
                osRequirement: 'Any',
                labsPerWeek: 2 + Math.floor(Math.random() * 3),
                requiredHours: Math.floor(Math.random() * 2) + 1,
                totalHours: 40,
                program: i % 2 === 0 ? p1._id : p2._id,
                academicYear: a2._id,
                subjects: [subjects[0]._id, subjects[1]._id]
            }).save();
        }
        console.log('Inserted 10 Batches');

        console.log('Dummy data seeding completed successfully!');
        process.exit(0);
    } catch (error) {
        console.error('Error seeding data:', error);
        process.exit(1);
    }
};

seedDummyData();
