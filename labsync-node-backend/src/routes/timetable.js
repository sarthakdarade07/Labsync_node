const express = require('express');
const router = express.Router();
const controller = require('../controllers/timetableController');

router.get('/active', controller.getActive);
router.get('/', controller.getAll);
router.post('/', controller.create);

module.exports = router;
