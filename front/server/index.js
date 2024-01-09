const express = require('express')
const app = express()
const bodyParser = require('body-parser')
const compression = require('compression')
const jwt = require('jsonwebtoken')

app.use(bodyParser.urlencoded({ extended: true }))
app.use(bodyParser.json())
app.use(compression())

// Require API routes
const bulletin = require('./bulletin')
const users = require('./users')
app.use('/', users)
app.use('/api', authenticateToken, bulletin)

// 토큰 인증 미들웨어
function authenticateToken(req, res, next) {
  const token = req.headers.authorization

  if (!token) {
    return res.status(401).json({ message: '인증되지 않은 요청입니다.' })
  }

    next() // 다음 미들웨어로 진행
}

app.listen(3000, () => {
  console.log('server start~!')
})

module.exports = app
