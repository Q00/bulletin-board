// boardService.js
const axios = require('axios');

const SPRING_BOOT_API_URL = 'http://api:8080/api/board'; // Update with your Spring Boot server URL

class BoardService {
  async list({ page, size }, token) {
    try {
      console.log('page', page, 'size', size)
      const response = await axios.get(`${SPRING_BOOT_API_URL}?page=${page-1}&size=${size}`, {
        headers: { Authorization: token }
      });
      return response.data;
    } catch (error) {
      console.log(error)
      throw error;
    }
  }

  async detail(boardId, token) {
    try {
      const response = await axios.get(`${SPRING_BOOT_API_URL}/${boardId}`, {
        headers: { Authorization: token }
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async add( boardInput, token) {
    try {
      const response = await axios.post(SPRING_BOOT_API_URL, boardInput, {
        headers: { Authorization: token }
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async deleteBoard( boardId, token) {
    try {
      const response = await axios.delete(`${SPRING_BOOT_API_URL}/${boardId}`, {
        headers: { Authorization: token }
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  async editBoard( boardId, updateData, token) {
    try {
      const response = await axios.put(`${SPRING_BOOT_API_URL}/${boardId}`, {...updateData}, {
        headers: { Authorization: token }
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  }
}

module.exports = new BoardService();
