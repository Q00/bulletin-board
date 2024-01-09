const { Router } = require('express');
const axios = require('axios');
const router = Router();

const SPRING_BOOT_AUTH_API_URL = 'http://localhost:8080/api/user'; // Update with your Spring Boot server URL

router.post('/signup', async function (request, response) {
  axios.post(`${SPRING_BOOT_AUTH_API_URL}`, request.body)
  .then((res) => {
    response.status(201).json(res.data);
  })
  .catch((error) => { 
    console.log(error)
    response.status(error.response.status).send('Server error');
  })

});

router.post('/login', async function (request, response) {
  axios.post(`${SPRING_BOOT_AUTH_API_URL}/login`, request.body)
  .then((res) => {
    response.status(200).json(res.data);
  })
  .catch((error) => {
    response.status(error.response.status).send(error);
  })
});

router.get('/user', async function (request, response) {
  // Here, you would extract the user's details from the request, possibly a token
  // For this example, let's assume you're sending a token in the Authorization header
  const token = request.headers.authorization;
  console.log(token)
  try {
    const result = await axios.get(`${SPRING_BOOT_AUTH_API_URL}`, {
      headers: { Authorization: token }
    });
    response.status(200).json(result.data);
  } catch (error) {
    console.log(error)
    response.status(500).send('Server error');
  }
});

module.exports = router;