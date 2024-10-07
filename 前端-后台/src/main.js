import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
// 挂载router
import router from "./router/index" // 引入router
// 引入antd
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
//引入antd图标
import * as Icons from '@ant-design/icons-vue';

const app = createApp(App)
//注册antd图标
Object.keys(Icons).forEach(key => {
    app.component(key, Icons[key]);
})
const pinia = createPinia()
app.use(pinia).use(router).use(Antd).mount('#app')
