const mongoose = require('mongoose');
const Schedule = require('./src/models/Schedule');
require('./src/models/Batch');
require('./src/models/Subject');
require('./src/models/Staff');
require('./src/models/Lab');
require('./src/models/Day');
const fs = require('fs');

mongoose.connect('mongodb://localhost:27017/labsync').then(async () => {
    try {
        const schedules = await Schedule.find({}).populate('batch subject staff lab day').lean();
        
        let markdown = `# Scheduled Batches\n\n`;
        markdown += `| Day | Time | Batch | Subject | Lab | Faculty | GA Generated |\n`;
        markdown += `|---|---|---|---|---|---|---|\n`;
        
        // Sort by Day, then Start Time
        schedules.sort((a, b) => {
            if (a.day?.dayOfWeek !== b.day?.dayOfWeek) return (a.day?.dayOfWeek || 0) - (b.day?.dayOfWeek || 0);
            return a.startTime.localeCompare(b.startTime);
        });

        for (const s of schedules) {
            const dayName = s.day ? s.day.dayName : 'Unknown';
            const time = `${s.startTime} - ${s.endTime}`;
            const batchName = s.batch ? `${s.batch.batchName} (${s.batch.division})` : 'Unknown';
            const subject = s.subject ? s.subject.subjectCode : 'Unknown';
            const lab = s.lab ? s.lab.labName : 'Unknown';
            const staff = s.staff ? s.staff.fullName : 'None';
            const ga = s.generatedByGA ? '✅' : '❌';
            
            markdown += `| ${dayName} | ${time} | **${batchName}** | ${subject} | ${lab} | ${staff} | ${ga} |\n`;
        }
        
        fs.writeFileSync('C:\\Users\\Sarthak Darade\\.gemini\\antigravity\\brain\\520c161c-679d-4dce-9966-d15ab38402b1\\scheduled_batches.md', markdown);
        console.log('Artifact created successfully!');
    } catch(err) {
        console.error(err);
    }
    process.exit(0);
});
