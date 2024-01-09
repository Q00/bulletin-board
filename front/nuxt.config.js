export default {
  // Global page headers: https://go.nuxtjs.dev/config-head
  server: {
    port: 3000,
    host: '0.0.0.0'
  },

  head: {
    title: 'bulletin-board',
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
  routeRules: {
    '/api/**': {
      proxy: { to: "http://localhost:3000/api/**", },
    },
    cors: true, // if enabled, also needs cors-preflight-request.ts Nitro middleware to answer CORS preflight requests
    headers: {
      // CORS headers
      'Access-Control-Allow-Origin': '*', // 'http://example:6006', has to be set to the requesting domain that you want to send the credentials back to
      'Access-Control-Allow-Methods': '*', // 'GET,HEAD,PUT,PATCH,POST,DELETE,OPTIONS'
      'Access-Control-Allow-Credentials': 'true',
      'Access-Control-Allow-Headers': '*', // 'Origin, Content-Type, Accept, Authorization, X-Requested-With'
      'Access-Control-Expose-Headers': '*',
      // 'Access-Control-Max-Age': '7200', // 7200 = caching 2 hours (Chromium default), https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Max-Age#directives
    },
  },

  axios: {
    proxy: true,
  },
  plugins: ['~/api/axios.js'],
  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {
    transpile: ['axios'],
  },
}
