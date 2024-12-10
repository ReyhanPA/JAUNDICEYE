const tf = require('@tensorflow/tfjs-node');
const sharp = require('sharp');
const path = require('path');

class JaundicedService {
    constructor() {
        this.model = null;
    }

    // Load the model
    async loadModel() {
        const modelPath = path.resolve(__dirname, './final_model/final_model.json');
        this.model = await tf.loadLayersModel(`file://${modelPath}`);
        console.log('Model loaded successfully');
    }

    // Predict jaundice from an image
    async predictJaundice(imageBuffer) {
        if (!this.model) {
            throw new Error('Model not loaded');
        }

        // Preprocess the image
        const processedImage = await sharp(imageBuffer)
            .resize(224, 224) // Resize to model's expected input size
            .toBuffer();
        const tensor = tf.node.decodeImage(processedImage)
            .expandDims(0)
            .div(tf.scalar(255)); // Normalize

        // Run the prediction
        const prediction = this.model.predict(tensor);
        const result = prediction.dataSync();
        return result[0] > 0.5 ? 'jaundiced' : 'not jaundiced';
    }
}

module.exports = JaundicedService;
