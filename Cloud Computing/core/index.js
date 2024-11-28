const express = require("express");
const cors = require("cors");
const morgan = require("morgan");
const serverConfig = require("./config/server-config");
const db = require("./database/supabase");
const articleApi = require("../components/article/article-api");

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

        this.express.use("/api/articles", articleApi);

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