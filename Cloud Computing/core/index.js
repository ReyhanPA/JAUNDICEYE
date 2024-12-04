const express = require("express");
const cors = require("cors");
const morgan = require("morgan");
const serverConfig = require("./config/server-config");
const db = require("./database/supabase");
const articleApi = require("../components/article/article-api");
const hospitalApi = require("../components/hospital/hospital-api");
const userApi = require("../components/user/user-api");
const { authMiddleware } = require("../middlewares/auth-middleware");

class Application {
    constructor() {
        this.express = express();
        this.serverConfig = serverConfig;
        this.db = db;
    }

    setupServer() {
        this.express.use(cors());
        this.express.use(morgan("dev"));
        this.express.use(express.json());

        this.express.use("/api/users", userApi);
        this.express.use("/api/articles", authMiddleware, articleApi);
        this.express.use("/api/hospitals", authMiddleware, hospitalApi);

        this.express.get("/api", (req, res) => {
            res.send("Welcome to JAUNDICE Backend Web Services!");
        });
    }

    getDatabase() {
        return this.db;
    }

    run() {
        this.express.listen(this.serverConfig.port, () => {
            this.setupServer();
            console.log(
                `Server is running on http://localhost:${this.serverConfig.port}`
            );
        });
    }
}

module.exports = new Application();
