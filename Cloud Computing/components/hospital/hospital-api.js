const express = require('express');
const router = express.Router();
const hospitalController = require('./hospital-controller');

router.get('/', hospitalController.getAllHospitals);

module.exports = router;