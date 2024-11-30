const hospitalRepository = require("./hospital-repository")

const getAllHospitals = async (req, res) => {
    try {
        const hospitals = await hospitalRepository.getAll();
        res.json(hospitals);
    } catch (error) {
        res.status(500).json({ error: 'Something went wrong' })
    }
}
