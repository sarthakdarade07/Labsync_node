const fs = require('fs');
const path = require('path');

const modelsDir = path.join(__dirname, 'src', 'models');
if (!fs.existsSync(modelsDir)) fs.mkdirSync(modelsDir, { recursive: true });

const schemas = {
  'User.js': `const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
    username: { type: String, required: true, unique: true },
    email: { type: String, required: true, unique: true },
    password: { type: String, required: true },
    fullName: { type: String, required: true },
    enabled: { type: Boolean, default: true },
    roles: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Role' }]
}, { timestamps: true });

module.exports = mongoose.model('User', userSchema);`,

  'Role.js': `const mongoose = require('mongoose');

const roleSchema = new mongoose.Schema({
    name: { type: String, required: true, unique: true },
    description: { type: String }
}, { timestamps: true });

module.exports = mongoose.model('Role', roleSchema);`,

  'Lab.js': `const mongoose = require('mongoose');

const labSchema = new mongoose.Schema({
    name: { type: String, required: true },
    capacity: { type: Number, required: true },
    workingComputers: { type: Number, required: true },
    osType: { type: String },
    isAvailable: { type: Boolean, default: true }
}, { timestamps: true });

module.exports = mongoose.model('Lab', labSchema);`,

  'Program.js': `const mongoose = require('mongoose');

const programSchema = new mongoose.Schema({
    name: { type: String, required: true },
    code: { type: String, required: true, unique: true },
    description: { type: String }
}, { timestamps: true });

module.exports = mongoose.model('Program', programSchema);`,

  'Subject.js': `const mongoose = require('mongoose');

const subjectSchema = new mongoose.Schema({
    name: { type: String, required: true },
    subjectCode: { type: String, required: true, unique: true },
    description: { type: String },
    hoursPerWeek: { type: Number },
    program: { type: mongoose.Schema.Types.ObjectId, ref: 'Program' }
}, { timestamps: true });

module.exports = mongoose.model('Subject', subjectSchema);`,

  'Batch.js': `const mongoose = require('mongoose');

const batchSchema = new mongoose.Schema({
    name: { type: String, required: true },
    batchCode: { type: String, required: true, unique: true },
    studentCount: { type: Number, required: true },
    labsPerWeek: { type: Number },
    requiredHours: { type: Number },
    osRequirement: { type: String },
    startTime: { type: String },
    endTime: { type: String },
    program: { type: mongoose.Schema.Types.ObjectId, ref: 'Program' },
    academicYear: { type: mongoose.Schema.Types.ObjectId, ref: 'AcademicYear' },
    subjects: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Subject' }]
}, { timestamps: true });

module.exports = mongoose.model('Batch', batchSchema);`,

  'Staff.js': `const mongoose = require('mongoose');

const staffSchema = new mongoose.Schema({
    fullName: { type: String, required: true },
    employeeId: { type: String, required: true, unique: true },
    email: { type: String, required: true },
    phone: { type: String },
    department: { type: String },
    designation: { type: String },
    user: { type: mongoose.Schema.Types.ObjectId, ref: 'User' }
}, { timestamps: true });

module.exports = mongoose.model('Staff', staffSchema);`,

  'Student.js': `const mongoose = require('mongoose');

const studentSchema = new mongoose.Schema({
    fullName: { type: String, required: true },
    prn: { type: String, required: true, unique: true },
    email: { type: String, required: true },
    phone: { type: String },
    batch: { type: mongoose.Schema.Types.ObjectId, ref: 'Batch' },
    program: { type: mongoose.Schema.Types.ObjectId, ref: 'Program' },
    academicYear: { type: mongoose.Schema.Types.ObjectId, ref: 'AcademicYear' }
}, { timestamps: true });

module.exports = mongoose.model('Student', studentSchema);`,

  'Day.js': `const mongoose = require('mongoose');

const daySchema = new mongoose.Schema({
    name: { type: String, required: true },
    startTime: { type: String, required: true },
    endTime: { type: String, required: true },
    isWorkingDay: { type: Boolean, default: true }
}, { timestamps: true });

module.exports = mongoose.model('Day', daySchema);`,

  'TimeSlot.js': `const mongoose = require('mongoose');

const timeSlotSchema = new mongoose.Schema({
    slotLabel: { type: String, required: true },
    startTime: { type: String, required: true },
    endTime: { type: String, required: true },
    active: { type: Boolean, default: true }
}, { timestamps: true });

module.exports = mongoose.model('TimeSlot', timeSlotSchema);`,

  'Schedule.js': `const mongoose = require('mongoose');

const scheduleSchema = new mongoose.Schema({
    batch: { type: mongoose.Schema.Types.ObjectId, ref: 'Batch', required: true },
    subject: { type: mongoose.Schema.Types.ObjectId, ref: 'Subject', required: true },
    staff: { type: mongoose.Schema.Types.ObjectId, ref: 'Staff' },
    lab: { type: mongoose.Schema.Types.ObjectId, ref: 'Lab', required: true },
    day: { type: mongoose.Schema.Types.ObjectId, ref: 'Day', required: true },
    startTime: { type: String, required: true },
    endTime: { type: String, required: true },
    generatedByGA: { type: Boolean, default: false },
    algorithmRun: { type: mongoose.Schema.Types.ObjectId, ref: 'AlgorithmRun' },
    active: { type: Boolean, default: true }
}, { timestamps: true });

module.exports = mongoose.model('Schedule', scheduleSchema);`,

  'AlgorithmRun.js': `const mongoose = require('mongoose');

const algorithmRunSchema = new mongoose.Schema({
    executionTimeMs: { type: Number },
    bestFitnessScore: { type: Number },
    status: { type: String, enum: ['SUCCESS', 'FAILED', 'RUNNING'], default: 'SUCCESS' },
    parameters: { type: mongoose.Schema.Types.Mixed },
    logs: { type: String }
}, { timestamps: true });

module.exports = mongoose.model('AlgorithmRun', algorithmRunSchema);`,

  'SystemSetting.js': `const mongoose = require('mongoose');

const systemSettingSchema = new mongoose.Schema({
    settingKey: { type: String, required: true, unique: true },
    settingValue: { type: String }
}, { timestamps: true });

module.exports = mongoose.model('SystemSetting', systemSettingSchema);`,

  'AcademicYear.js': `const mongoose = require('mongoose');

const academicYearSchema = new mongoose.Schema({
    year: { type: String, required: true, unique: true },
    isActive: { type: Boolean, default: false }
}, { timestamps: true });

module.exports = mongoose.model('AcademicYear', academicYearSchema);`
};

for (const [filename, content] of Object.entries(schemas)) {
    fs.writeFileSync(path.join(modelsDir, filename), content);
    console.log('Created ' + filename);
}
