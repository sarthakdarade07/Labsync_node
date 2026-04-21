const jwt = require('jsonwebtoken');

const verifyToken = (req, res, next) => {
    const token = req.headers['authorization'];
    if (!token) return res.status(403).json({ message: 'No token provided' });

    const tokenBearer = token.split(' ')[1] || token;

    jwt.verify(tokenBearer, process.env.JWT_SECRET, (err, decoded) => {
        if (err) return res.status(401).json({ message: 'Unauthorized' });
        req.userId = decoded.id;
        req.roles = decoded.roles;
        next();
    });
};

const isAdmin = (req, res, next) => {
    if (req.roles && req.roles.includes('ROLE_ADMIN')) {
        next();
        return;
    }
    res.status(403).json({ message: 'Require Admin Role' });
};

module.exports = { verifyToken, isAdmin };
