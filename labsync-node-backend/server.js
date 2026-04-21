require('dotenv').config();
const mongoose = require('mongoose');

// Apply global transform before ANY models are loaded
mongoose.plugin(schema => {
    schema.set('toJSON', {
        virtuals: true,
        versionKey: false,
        transform: (doc, ret) => {
            ret.id = ret._id;
            delete ret._id;
        }
    });
});

const app = require('./src/app');
const connectDB = require('./src/config/db');

const PORT = process.env.PORT || 8080;

// Connect to MongoDB and start server
connectDB().then(() => {
    app.listen(PORT, () => {
        console.log(`Server is running on port ${PORT}`);
    });
});
