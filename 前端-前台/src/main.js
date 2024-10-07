import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
// 挂载router
import router from "./router/index" // 引入router
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import '../src/assets/css/main.css'
const app = createApp(App)
app.use(createPinia()).use(router).use(Antd)
app.mount('#app')
