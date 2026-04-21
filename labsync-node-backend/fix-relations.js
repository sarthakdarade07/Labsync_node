const fs = require('fs');
const path = require('path');

const modelsDir = path.join(__dirname, 'src', 'models');
const files = fs.readdirSync(modelsDir);

for (const file of files) {
    if (!file.endsWith('.js') || file === 'autoIncrement.js') continue;
    let content = fs.readFileSync(path.join(modelsDir, file), 'utf8');
    
    // Replace mongoose.Schema.Types.ObjectId with Number
    content = content.replace(/mongoose\.Schema\.Types\.ObjectId/g, 'Number');
    fs.writeFileSync(path.join(modelsDir, file), content);
}
console.log('Replaced all ObjectIds with Numbers for relations.');
