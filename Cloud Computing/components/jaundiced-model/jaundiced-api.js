const express = require('express');
const multer = require('multer');
const JaundicedService = require('./jaundiced-service');
const JaundicedController = require('./jaundiced-controller');

const router = express.Router();
const upload = multer(); // For handling file uploads

// Initialize service and controller
const jaundicedService = new JaundicedService();
const jaundicedController = new JaundicedController(jaundicedService);

// Load the model at application startup
(async () => {
    try {
        await jaundicedService.loadModel();
    } catch (error) {
        console.error('Failed to load model:', error);
    }
})();

// Route to detect jaundice
router.post('/detect', upload.single('image'), (req, res) => jaundicedController.detectJaundice(req, res));

module.exports = router;
