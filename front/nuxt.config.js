export default {
  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    title: 'bullentin-board',
    htmlAttrs: {
      lang: 'en',
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' },
      { name: 'format-detection', content: 'telephone=no' },
    ],
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css: [],

  buildModules: ['@nuxtjs/vuetify', '@nuxtjs/date-fns'],
  // Auto import components: https://go.nuxtjs.dev/config-components
  components: true,

  // Modules: https://go.nuxtjs.dev/config-modules
  serverMiddleware: ['~/server/index.js'],
  // https://fkkmemi.github.io/nuxt/nuxt-003-routing/
  modules: ['@nuxtjs/axios'],

  axios: {
    proxy: true,
  },
  plugins: ['~/api/axios.js'],
  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {
    transpile: ['axios'],
  },
}
