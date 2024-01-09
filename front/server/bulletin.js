const { Router } = require('express');
const router = Router();

const boardService = require('./boardService');

// Home all GET
router.get('/get', async function (request, response) {
  const { page = 0, size = 10 } = request.query;

  try {
    const list = await boardService.list({ page, size }, request.headers.authorization);
    response.status(200).json(list);
  } catch (error) {
    response.status(500).send('Server error');
  }
});

// Detail - one item GET
router.get('/get/:boardId', async function (request, response) {
  const { boardId } = request.params;

  try {
    const board = await boardService.detail(boardId, request.headers.authorization);
    response.status(200).json(board);
  } catch (error) {
    response.status(404).send('Board not found');
  }
});

router.post('/post', async function (request, response) {
  const boardInput = request.body;

  try {
    const result = await boardService.add(boardInput, request.headers.authorization);
    response.status(201).json(result);
  } catch (error) {
    console.log(error)
    response.status(500).send('Server error');
  }
});

// Detail - one item DELETE
router.delete('/delete/:itemId', async function (request, response) {
  const { itemId } = request.params;

  try {
    const result = await boardService.deleteBoard( itemId, request.headers.authorization);
    response.status(200).json(result);
  } catch (error) {
    response.status(404).send('Item not found');
  }
});

// Detail - one item PATCH
router.patch('/patch/:id', async function (request, response) {
  const boardId = request.params.id;
  const updateData = request.body;
console.log('updateData', updateData)
  try {
    const result = await boardService.editBoard(boardId, updateData);
    response.status(200).json(result);
  } catch (error) {
    console.log(error)
    response.status(404).send('Board not found');
  }
});

module.exports = router;