const articleRepository = require('./article-repository');

const getAllArticles = async (req, res) => {
  try {
    const articles = await articleRepository.getAll();
    res.json(articles);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Something went wrong!' });
  }
};

const getArticleById = async (req, res) => {
  const { id } = req.params;
  try {
    const article = await articleRepository.getById(id);
    if (!article) {
      return res.status(404).json({ error: 'Article not found' });
    }
    res.json(article);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Something went wrong!' });
  }
};

const createArticle = async (req, res) => {
  const { title, description, link } = req.body;

  // Validasi input
  if (!title || !description || !link) {
    return res.status(400).json({ error: 'Title, description, and link are required' });
  }

  try {
    const newArticle = await articleRepository.create({ title, description, link });
    res.status(201).json(newArticle);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Something went wrong!' });
  }
};

const updateArticle = async (req, res) => {
  const { id } = req.params;
  const { title, description, link } = req.body;

  // Validasi input
  if (!title || !description || !link) {
    return res.status(400).json({ error: 'Title, description, and link are required' });
  }

  try {
    const updatedArticle = await articleRepository.update(id, { title, description, link });
    if (!updatedArticle) {
      return res.status(404).json({ error: 'Article not found' });
    }
    res.json(updatedArticle);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Something went wrong!' });
  }
};

const deleteArticle = async (req, res) => {
  const { id } = req.params;
  try {
    const deleted = await articleRepository.deleteArticle(id);
    if (!deleted) {
      return res.status(404).json({ error: 'Article not found' });
    }
    res.status(204).end();
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Something went wrong!' });
  }
};

module.exports = {
  getAllArticles,
  getArticleById,
  createArticle,
  updateArticle,
  deleteArticle
};