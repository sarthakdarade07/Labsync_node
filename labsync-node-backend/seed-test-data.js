const mongoose = require('mongoose');
const Subject = require('./src/models/Subject');
const Staff = require('./src/models/Staff');
const Batch = require('./src/models/Batch');

mongoose.connect('mongodb://localhost:27017/labsync').then(async () => {
    try {
        let subject = await Subject.findOne();
        if (!subject) {
            subject = await Subject.create({ name: 'Data Structures', subjectCode: 'CS101', hoursPerWeek: 4 });
        }
        
        let staff = await Staff.findOne();
        if (!staff) {
            staff = await Staff.create({ fullName: 'John Doe', employeeId: 'EMP01', email: 'john@example.com' });
        }
        
        let batch = await Batch.findOne();
        if (!batch) {
            batch = await Batch.create({
                batchName: 'B1',
                division: 'A',
                studentCount: 60,
                labsPerWeek: 2,
                requiredHours: 2,
                subjects: [subject._id],
                osRequirement: 'Any'
            });
        }
        console.log('Test data seeded');
    } catch(err) {
        console.error(err);
    }
    process.exit(0);
});
