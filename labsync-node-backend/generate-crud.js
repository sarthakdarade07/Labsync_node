const fs = require('fs');
const path = require('path');

const resources = [
    { name: 'Batch', route: 'batches' },
    { name: 'Day', route: 'days' },
    { name: 'Lab', route: 'labs' },
    { name: 'Program', route: 'programs' },
    { name: 'Role', route: 'roles' },
    { name: 'Staff', route: 'staff' },
    { name: 'Student', route: 'students' },
    { name: 'Subject', route: 'subjects' },
    { name: 'SystemSetting', route: 'settings' },
    { name: 'TimeSlot', route: 'time-slots' },
    { name: 'User', route: 'users' }
];

const controllersDir = path.join(__dirname, 'src', 'controllers');
const routesDir = path.join(__dirname, 'src', 'routes');

resources.forEach(res => {
    const modelName = res.name;
    const routeName = res.route;
    
    const controllerCode = `const ${modelName} = require('../models/${modelName}');

exports.getAll = async (req, res) => {
    try {
        const data = await ${modelName}.find();
        res.status(200).json({ success: true, message: 'Fetched successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.getById = async (req, res) => {
    try {
        const data = await ${modelName}.findById(req.params.id);
        if (!data) return res.status(404).json({ success: false, message: 'Not found' });
        res.status(200).json({ success: true, message: 'Fetched successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.create = async (req, res) => {
    try {
        const data = await ${modelName}.create(req.body);
        res.status(201).json({ success: true, message: 'Created successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.update = async (req, res) => {
    try {
        const data = await ${modelName}.findByIdAndUpdate(req.params.id, req.body, { new: true });
        if (!data) return res.status(404).json({ success: false, message: 'Not found' });
        res.status(200).json({ success: true, message: 'Updated successfully', data });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

exports.delete = async (req, res) => {
    try {
        const data = await ${modelName}.findByIdAndDelete(req.params.id);
        if (!data) return res.status(404).json({ success: false, message: 'Not found' });
        res.status(200).json({ success: true, message: 'Deleted successfully', data: null });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};
`;

    const routeCode = `const express = require('express');
const router = express.Router();
const controller = require('../controllers/${modelName.toLowerCase()}Controller');

router.get('/', controller.getAll);
router.get('/:id', controller.getById);
router.post('/', controller.create);
router.put('/:id', controller.update);
router.delete('/:id', controller.delete);

module.exports = router;
`;

    fs.writeFileSync(path.join(controllersDir, modelName.toLowerCase() + 'Controller.js'), controllerCode);
    fs.writeFileSync(path.join(routesDir, routeName + '.js'), routeCode);
});

// Now generate the app.js includes
let appIncludes = resources.map(res => `const ${res.route.replace('-', '')}Routes = require('./routes/${res.route}');\napp.use('/api/${res.route}', ${res.route.replace('-', '')}Routes);`).join('\\n');

console.log("Routes generated. Please add these to your app.js:");
console.log(appIncludes);
