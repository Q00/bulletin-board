<template>
  <div>
    <header class="header">
      <div class="brand-container">
        <h1 class="greeting">Hello, <span class="name">{{ name }}</span></h1>
      </div>
      <div>
        <NuxtLink to="/">
          <button class="logout">Logout</button>
        </NuxtLink>
      </div>
    </header>
    <Nuxt />
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      name: '',
    }
  },
  mounted() {
    this.email = window.localStorage.getItem('USEREMAIL')
    this.name = window.localStorage.getItem('USERNAME')
  },
}
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  height: 80px;
  padding: 0 40px;
  background-color: #fff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  font-family: 'Segoe UI', sans-serif;
}

.brand-container {
  display: flex;
  align-items: center;
}

.greeting {
  font-size: 1.4em;
  color: #333;
  margin: 0;
}

.name {
  color: #5b93fa;
}

.logout {
  background-color: #ff5252;
  color: #fff;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.3s ease;
}

.logout:hover {
  background-color: #ff3b3b;
}
</style>
