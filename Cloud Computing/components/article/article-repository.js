const { createClient } = require('@supabase/supabase-js');
const dotenv = require('dotenv');
dotenv.config();

// Initialize Supabase client
const supabase = createClient(process.env.SUPABASE_URL, process.env.SUPABASE_ANON_KEY);

// Get all articles
const getAll = async () => {
  const { data, error } = await supabase
    .from('article')
    .select('id, title, description, link'); // Memastikan kita hanya mengambil kolom yang relevan
  if (error) throw error;
  return data;
};

// Get article by ID
const getById = async (id) => {
  const { data, error } = await supabase
    .from('article')
    .select('id, title, description, link') // Memastikan kita hanya mengambil kolom yang relevan
    .eq('id', id)
    .single();  // Mengambil hanya satu artikel
  if (error) throw error;
  return data;
};

// Create new article
const create = async ({ title, description, link }) => {
  const { data, error } = await supabase
    .from('article')
    .insert([{ title, description, link }])
    .single();  // Menginsert satu artikel sekaligus
  if (error) throw error;
  return data;
};

// Update article by ID
const update = async (id, { title, description, link }) => {
  const { data, error } = await supabase
    .from('article')
    .update({ title, description, link })
    .eq('id', id)
    .single();  // Mengupdate artikel berdasarkan ID
  if (error) throw error;
  return data;
};

// Delete article by ID
const deleteArticle = async (id) => {
  const { data, error } = await supabase
    .from('article')
    .delete()
    .eq('id', id);
  if (error) throw error;
  return data;
};

module.exports = {
  getAll,
  getById,
  create,
  update,
  deleteArticle
};