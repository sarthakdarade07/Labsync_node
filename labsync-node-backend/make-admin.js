const mongoose = require('mongoose');
const Role = require('./src/models/Role');
const User = require('./src/models/User');

mongoose.connect('mongodb://localhost:27017/labsync').then(async () => {
    try {
        let adminRole = await Role.findOne({name: 'ROLE_ADMIN'});
        if (!adminRole) adminRole = await Role.create({name: 'ROLE_ADMIN'});
        await User.updateOne({username: 'admin'}, { $push: { roles: adminRole._id } });
        console.log('Admin role added to admin user');
    } catch(err) {
        console.error(err);
    }
    process.exit(0);
});
