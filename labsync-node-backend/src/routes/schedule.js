const express = require('express');
const router = express.Router();
const scheduleController = require('../controllers/scheduleController');

router.get('/public', scheduleController.getPublicSchedules);
router.get('/', scheduleController.getAllSchedules);
router.get('/batch/:batchId', scheduleController.getByBatch);
router.get('/day/:dayId', scheduleController.getByDay);
router.get('/lab/:labId', scheduleController.getByLab);
router.post('/validate', scheduleController.validateSchedule);
router.post('/', scheduleController.create);
router.delete('/:id', scheduleController.delete);
module.exports = router;
