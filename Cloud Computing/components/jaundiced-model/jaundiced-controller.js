class JaundicedController {
    constructor(jaundicedService) {
        this.jaundicedService = jaundicedService;
    }

    // Endpoint to detect jaundice
    async detectJaundice(req, res) {
        try {
            const image = req.file; // Expecting a single image upload via multer
            if (!image) {
                return res.status(400).json({ error: 'No image provided' });
            }

            const result = await this.jaundicedService.predictJaundice(image.buffer);
            res.json({ result });
        } catch (error) {
            console.error(error);
            res.status(500).json({ error: 'Failed to process the image' });
        }
    }
}

module.exports = JaundicedController;

