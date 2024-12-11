const tf = require('@tensorflow/tfjs-node');
const sharp = require('sharp');
const path = require('path');

class JaundicedService {
    constructor() {
        this.model = null;
    }

    // Load the model
    async loadModel() {
        const modelPath = path.resolve(__dirname, './jaundiced/model.json');
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
            .resize(100, 100)
            .toBuffer();
        const tensorImage = tf.node.decodeImage(processedImage)
            .expandDims(0)
            .div(tf.scalar(255));

        // Create color_input (example: calculate average RGB)
        const colorTensor = this.calculateAverageRGB(imageBuffer);

        const prediction = this.model.predict([tensorImage, colorTensor]);
        const result = prediction.dataSync();
        return result[0] > 0.5 ? 'jaundiced' : 'not jaundiced';
    }

    calculateAverageRGB(imageBuffer) {
        const imageTensor = tf.node.decodeImage(imageBuffer);
        const [height, width, channels] = imageTensor.shape;

        // Calculate the average RGB values (mean across height and width)
        const meanRGB = tf.mean(imageTensor, [0, 1]).div(tf.scalar(255)); 

        return tf.reshape(meanRGB, [1, 3]);
    }
}

module.exports = JaundicedService;
