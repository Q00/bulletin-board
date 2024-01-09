<template>
  <div class="formContainer">
    <form class="formWrapper" @submit.prevent="onSignup">
      <label>
        E-mail
        <input
          v-model="signupData.email"
          type="email"
          class="formInputEl"
          required
        />
      </label>
      <label>
        Password
        <input
          v-model="signupData.password"
          type="password"
          class="formInputEl"
          required
        />
      </label>
      <button type="submit" class="formSubmitBtn">signup</button>
    </form>
    <div>
      <nuxt-link to="/">
        <button type="button" class="goto">login</button>
      </nuxt-link>
    </div>
  </div>
</template>

<script>
const signupData = {
  email: '',
  password: '',
}

export default {
  layout: 'LoginLayout',
  data() {
    return {
      signupData: {  ...signupData },
    }
  },
  methods: {
    async onSignup() {
      try {
        const data = await this.$axios.$post('/api/signup', this.signupData)
        console.log(data)

        if (data.header.result) {
          alert('회원가입 성공')
          window.location.href = "/"

        }
      } catch (err) {
        if (typeof err.status === 'number') {
          // 500, 409 //
          if (err.status === 500) {
            alert('서버 오류')
            return
          }

          if (err.status === 409) {
            this.signupData = signupData
            alert('이미 존재하는 이메일 입니다.')
            return
          }
        }
        // <try에서 스트립트 오류일 때>
        throw err
      }
    },
  },
}
</script>

<style>
.formContainer {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.formWrapper {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.formInputEl {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.formSubmitBtn, .goto {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.formSubmitBtn:hover, .goto:hover {
  background-color: #0056b3;
}

label {
  display: flex;
  flex-direction: column;
}

@media (max-width: 600px) {
  .formContainer {
    width: 100%;
    padding: 10px;
  }
}

</style>
