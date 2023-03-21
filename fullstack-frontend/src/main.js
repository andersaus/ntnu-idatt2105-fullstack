import { createPinia } from 'pinia'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { useLoginStore } from './store/LoginStore'

import './assets/main.css'

const pinia = createPinia()

const app = createApp(App)

app.provide('useLoginStore', useLoginStore);
app.use(router)
app.use(pinia)

app.mount('#app')


// createApp(App).use(router).use(createPinia()).mount('#app')