import {createApp} from 'vue'
import App from './App.vue' // 导入根组件
import router from "./router/index.js";

const app = createApp(App) // 创建应用实例，传入根组件
app.use(router)
app.mount('#app') // 挂载到 index.html 中 id="app" 的 DOM 元素上
