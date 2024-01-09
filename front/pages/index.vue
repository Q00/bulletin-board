<template>
  <v-app id="app">
    <div class="form-container">
      <form class="form-wrapper" @submit.prevent="onLogin">
        <div class="input-group">
          <label for="email">E-mail</label>
          <input
            id="email"
            v-model="loginData.email"
            type="email"
            required
            class="form-input"
          />
        </div>
        <div class="input-group">
          <label for="password">Password</label>
          <input
            id="password"
            v-model="loginData.password"
            type="password"
            required
            class="form-input"
          />
        </div>
        <button type="submit" class="form-submit-btn">Login</button>
        <!-- Sign Up button moved inside the form-wrapper -->
        <div class="signup-link">
          <nuxt-link to="/signup">
            <button type="button" class="goto">Sign Up</button>
          </nuxt-link>
        </div>
      </form>
    </div>
  </v-app>
</template>

<script>
export default {
  name: 'IndexPage',
  layout: 'LoginLayout',
  data() {
    return {
      loginData: {
        email: '',
        password: '',
      },
    }
  },
  methods: {
    async onLogin() {
      try {
        const { header, body } = await this.$axios.$post(
          '/api/login',
          this.loginData
        )

        const token = `Bearer ${body.accessToken}`

        const data = await this.$axios.$get(
          '/api/user', {headers: {'Authorization': token}}
        )
        if (header.result) {
          alert('로그인 성공')

          window.localStorage.setItem('USEREMAIL', data.body.email)
          window.localStorage.setItem('USERNAME', data.body.userName)
          window.localStorage.setItem('ACCESS_TOKEN', token)
          this.$router.push('/board/1')
        }
      } catch {
        alert('이메일 또는 비밀번호를 확인해주세요')
      }
    },
  },
}
</script>
<style>
.form-container {
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  padding-top: 50px;
}

.form-wrapper {
  width: 300px;
  border: none;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  border-radius: 8px;
  background: white;
}

.input-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.form-input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

.form-submit-btn {
  width: 100%;
  background-color: #007bff;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.form-submit-btn:hover {
  background-color: #0056b3;
}

.goto {
  background: transparent;
  border: 2px solid #007bff;
  color: #007bff;
  padding: 5px 15px;
  border-radius: 4px;
  cursor: pointer;
}

.goto:hover {
  background: #007bff;
  color: white;
}

.signup-link {
  text-align: center;
  margin-top: 20px;
}

</style>
