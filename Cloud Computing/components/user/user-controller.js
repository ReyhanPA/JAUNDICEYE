const { createUser, findUserByUsername } = require("./user-repository");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const { v4: uuidv4 } = require("uuid");

// SIGNUP
const signup = async(req, res) => {
    const { username, password } = req.body;

    // Validate input
    if (!username || !password) {
        return res.status(400).json({ success: false, message: "Username and password are required" });
    }

    // Check if user already exists
    const existingUser = await findUserByUsername(username);
    if (existingUser) {
        return res.status(409).json({ success: false, message: "Username already taken" });
    }

    try {
        // Generate UUID
        const userId = uuidv4();

        // Hash password
        const hashedPassword = await bcrypt.hashSync(password, 10);

        // Save user
        const newUser = await createUser({
            id: userId,
            username,
            password: hashedPassword
        });

        // Generate JWT token
        const token = jwt.sign({ id: newUser.id, username: newUser.username },
            process.env.JWT_SECRET, { expiresIn: "1h" }
        );

        res.status(201).json({
            success: true,
            message: "User registered successfully",
            user: { id: newUser.id, username: newUser.username },
            token
        });

    } catch (error) {
        console.log(error)
        res.status(500).json({ success: false, message: error });
    }
};


// LOGIN
const login = async(req, res) => {
    const { username, password } = req.body;

    // Validate input
    if (!username || !password) {
        return res.status(400).json({ success: false, message: "Username and password are required" });
    }

    try {
        // Find user by username
        const user = await findUserByUsername(username);
        if (!user) {
            return res.status(404).json({ success: false, message: "User not found" });
        }

        // Check password
        const isMatch = await bcrypt.compareSync(password, user.password);
        if (!isMatch) {
            return res.status(401).json({ success: false, message: "Invalid credentials" });
        }

        // Generate JWT token
        const token = jwt.sign({ id: user.id, username: user.username },
            process.env.JWT_SECRET, { expiresIn: "1h" }
        );

        res.status(200).json({
            success: true,
            message: "Login successful",
            token
        });
    } catch (error) {
        res.status(500).json({ success: false, message: "Internal server error" });
    }
};


module.exports = { signup, login };