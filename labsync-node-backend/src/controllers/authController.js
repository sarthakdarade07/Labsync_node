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
        const user = await User.findOne({ username }).populate('roles', '-__v');
        if (!user) return res.status(404).json({ success: false, message: 'User Not found.' });

        const passwordIsValid = await bcrypt.compare(password, user.password);
        if (!passwordIsValid) return res.status(401).json({ success: false, message: 'Invalid Password!' });

        const authorities = user.roles.map(role => role.name);

        const token = jwt.sign(
            { id: user._id, roles: authorities },
            process.env.JWT_SECRET,
            { expiresIn: process.env.JWT_EXPIRES_IN || '24h' }
        );

        res.status(200).json({
            success: true,
            message: 'Login successful',
            data: {
                token: token,
                type: 'Bearer',
                id: user._id,
                username: user.username,
                email: user.email,
                roles: authorities
            }
        });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};
