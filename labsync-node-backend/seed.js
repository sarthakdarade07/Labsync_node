const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
require('dotenv').config();

const User = require('./src/models/User');
const Role = require('./src/models/Role');

const seedDB = async () => {
    try {
        await mongoose.connect(process.env.MONGODB_URI);
        console.log('MongoDB Connected');

        // Create roles
        const adminRole = await Role.findOneAndUpdate({ name: 'ROLE_ADMIN' }, { name: 'ROLE_ADMIN' }, { upsert: true, new: true });
        const userRole = await Role.findOneAndUpdate({ name: 'ROLE_USER' }, { name: 'ROLE_USER' }, { upsert: true, new: true });

        // Create Admin user
        const adminExists = await User.findOne({ username: 'admin' });
        if (!adminExists) {
            const salt = await bcrypt.genSalt(10);
            const hashedPassword = await bcrypt.hash('admin123', salt);
            
            await User.create({
                username: 'admin',
                email: 'admin@labsync.com',
                password: hashedPassword,
                fullName: 'System Administrator',
                roles: [adminRole._id]
            });
            console.log('Admin user created (username: admin, password: admin123)');
        } else {
            console.log('Admin user already exists');
        }

        console.log('Database seeded successfully');
        process.exit(0);
    } catch (error) {
        console.error('Error seeding database:', error);
        process.exit(1);
    }
};

seedDB();
