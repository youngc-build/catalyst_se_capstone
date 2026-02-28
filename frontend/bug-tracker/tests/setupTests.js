import { createApp } from 'vue'
import App from '../src/App.vue'
import { createPinia } from 'pinia'

// Create a global app instance for testing
const app = createApp(App)
app.use(createPinia())

// Mount the app to the DOM for testing
app.mount('#app')

// Export the app instance for use in tests
export default app
