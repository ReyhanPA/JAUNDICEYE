const { createClient } = require('@supabase/supabase-js');
const dotenv = require('dotenv');
dotenv.config();

const supabase = createClient(process.env.SUPABASE_PROJECT_URL, process.env.SUPABASE_API_KEY);

// Get All Hospitals
const getAll = async() => {
    const { data, error } = await supabase
        .from('hospitals')
        .select('*');
    if (error) throw error;
    return data;
}

module.exports = {
    getAll
}