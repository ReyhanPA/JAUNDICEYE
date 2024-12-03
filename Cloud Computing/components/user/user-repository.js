const { createClient } = require('@supabase/supabase-js');
const dotenv = require('dotenv');
dotenv.config();

// Initialize Supabase client
const supabase = createClient(process.env.SUPABASE_PROJECT_URL, process.env.SUPABASE_API_KEY);

const createUser = async(userData) => {
    try {
        const { data, error } = await supabase.from('users').insert(userData).select();
        if (error) throw new Error(error.message);
        return data[0];
    } catch (error) {
        throw new Error(error.message);
    }
};

const findUserByUsername = async(username) => {
    try {
        const { data, error } = await supabase
            .from('users')
            .select('*')
            .eq('username', username)
            .single();

        if (error && error.code === 'PGRST116') return null;

        if (error) throw new Error(error.message);

        return data;
    } catch (error) {
        throw new Error(error.message);
    }
};


module.exports = { createUser, findUserByUsername };