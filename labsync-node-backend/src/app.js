const express = require('express');
const cors = require('cors');
const morgan = require('morgan');

const app = express();

// Middleware
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(morgan('dev'));

// Basic Route for testing
app.get('/api/health', (req, res) => {
    res.json({ status: 'ok', message: 'LabSync API is running on Node.js' });
});

// Import Routes
const authRoutes = require('./routes/auth');
const scheduleRoutes = require('./routes/schedule');
const batchesRoutes = require('./routes/batches');
const daysRoutes = require('./routes/days');
const labsRoutes = require('./routes/labs');
const programsRoutes = require('./routes/programs');
const rolesRoutes = require('./routes/roles');
const staffRoutes = require('./routes/staff');
const studentsRoutes = require('./routes/students');
const subjectsRoutes = require('./routes/subjects');
const settingsRoutes = require('./routes/settings');
const timeslotsRoutes = require('./routes/time-slots');
const usersRoutes = require('./routes/users');
const adminRoutes = require('./routes/admin');
const timetableRoutes = require('./routes/timetable');

app.use('/api/auth', authRoutes);
app.use('/api/schedules', scheduleRoutes);
app.use('/api/batches', batchesRoutes);
app.use('/api/days', daysRoutes);
app.use('/api/labs', labsRoutes);
app.use('/api/programs', programsRoutes);
app.use('/api/roles', rolesRoutes);
app.use('/api/staff', staffRoutes);
app.use('/api/students', studentsRoutes);
app.use('/api/subjects', subjectsRoutes);
app.use('/api/settings', settingsRoutes);
app.use('/api/time-slots', timeslotsRoutes);
app.use('/api/users', usersRoutes);
app.use('/api/admin', adminRoutes);
app.use('/api/timetables', timetableRoutes);

// Error Handling Middleware
app.use((err, req, res, next) => {
    console.error(err.stack);
    res.status(500).json({ success: false, message: 'Server Error', error: err.message });
});

module.exports = app;
