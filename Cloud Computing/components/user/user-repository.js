const User = require("./user");

const createUser = async (userData) => {
  try {
    return await User.create(userData);
  } catch (error) {
    throw new Error(error.message);
  }
};

const findUserByEmail = async (email) => {
  try {
    return await User.findOne({ where: { email } });
  } catch (error) {
    throw new Error(error.message);
  }
};

const findUserById = async (id) => {
  try {
    return await User.findByPk(id);
  } catch (error) {
    throw new Error(error.message);
  }
};

module.exports = { createUser, findUserByEmail, findUserById };
