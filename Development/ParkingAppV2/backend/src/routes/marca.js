const { Router } = require('express');
const router = Router();

const { getMarcas, getMarcaById, createMarca, updateMarca, deleteMarca } = require('../controllers/marca.controller');

router.get('/marca', getMarcas);
router.get('/marca/:id', getMarcaById);
//router.post('/users', createUser);
//router.put('/users/:id', updateUser)
//router.delete('/users/:id', deleteUser);

module.exports = router;