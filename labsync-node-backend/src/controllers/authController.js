const User = require('../models/User');
const Role = require('../models/Role');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

exports.register = async (req, res) => {
    try {
        const { username, email, password, fullName } = req.body;
        
        // Hash password
        const salt = await bcrypt.genSalt(10);
        const hashedPassword = await bcrypt.hash(password, salt);
        
        // Find default role
        let userRole = await Role.findOne({ name: 'ROLE_USER' });
        if (!userRole) {
             userRole = await Role.create({ name: 'ROLE_USER' });
        }

        const newUser = await User.create({
            username,
            email,
            password: hashedPassword,
            fullName,
            roles: [userRole._id]
        });

        res.status(201).json({ success: true, message: 'User registered successfully!', data: newUser });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.login = async (req, res) => {
    try {
        const { username, password } = req.body;
        
        // Hardcoded admin login
        if (username === 'admin' && password === 'admin123') {
            const token = jwt.sign(
                { id: 1, roles: ['ROLE_ADMIN'] },
                process.env.JWT_SECRET || 'default_secret_key',
                { expiresIn: process.env.JWT_EXPIRES_IN || '24h' }
            );
            return res.status(200).json({
                success: true,
                message: 'Login successful',
                data: {
                    token: token,
                    type: 'Bearer',
                    id: 1,
                    username: 'admin',
                    email: 'admin@labsync.local',
                    roles: ['ROLE_ADMIN']
                }
            });
        }
        
        // Hardcoded staff login
        if (username === 'staff' && password === 'staff123') {
            const token = jwt.sign(
                { id: 2, roles: ['ROLE_STAFF'] },
                process.env.JWT_SECRET || 'default_secret_key',
                { expiresIn: process.env.JWT_EXPIRES_IN || '24h' }
            );
            return res.status(200).json({
                success: true,
                message: 'Login successful',
                data: {
                    token: token,
                    type: 'Bearer',
                    id: 2,
                    username: 'staff',
                    email: 'staff@labsync.local',
                    roles: ['ROLE_STAFF']
                }
            });
        }

        // If not matching the strictly requested credentials, reject.
        return res.status(401).json({ success: false, message: 'Invalid Username or Password!' });
        
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};
