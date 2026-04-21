const fs = require('fs');
const path = require('path');

const modelsDir = path.join(__dirname, 'src', 'models');
const files = fs.readdirSync(modelsDir);

for (const file of files) {
    if (!file.endsWith('.js') || file === 'autoIncrement.js') continue;
    let content = fs.readFileSync(path.join(modelsDir, file), 'utf8');
    
    // Remove mongoose-sequence
    content = content.replace("const AutoIncrement = require('mongoose-sequence')(mongoose);", "const autoIncrement = require('./autoIncrement');");
    
    // Check if plugin is already called, replace it
    const schemaMatch = content.match(/const\s+(\w+Schema)\s*=\s*new\s+mongoose\.Schema/);
    if (schemaMatch) {
        const schemaName = schemaMatch[1];
        content = content.replace(new RegExp(`${schemaName}\\.plugin\\(AutoIncrement.*\\);\\n`), `${schemaName}.plugin(autoIncrement, { model: '${file.replace('.js', '')}' });\n`);
        fs.writeFileSync(path.join(modelsDir, file), content);
    }
}
console.log('Applied custom autoIncrement to all models.');
