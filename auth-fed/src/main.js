import { createApp } from 'vue'
import { Button, Form, Input, Tabs, message } from 'ant-design-vue'

import App from './App.vue'

import router from './router'

const app = createApp(App)
  .use(router)
  .use(Button)
  .use(Form)
  .use(Input)
  .use(Tabs)

//全局过滤器
app.config.globalProperties.$message = message
app.config.globalProperties.$filters = {}
app.provide('$message', message)

//挂载APP
app.mount('#app')
