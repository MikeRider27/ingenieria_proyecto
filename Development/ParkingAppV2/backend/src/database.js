const mongoose = require('mongoose');

async function connect() {
    await mongoose.connect('mongodb://localhost/flutter-prueba', {
        useNewUrlParser: true
    });
    console.log('Database: Connected');
};

module.exports = { connect };