import { createApp } from "vue"
import { Button, Form, Input, Tabs } from "ant-design-vue"

import App from "./App.vue"

import router from "./router"

createApp(App)
  .use(router)
  .use(Button)
  .use(Form)
  .use(Input)
  .use(Tabs)
  .mount("#app")
