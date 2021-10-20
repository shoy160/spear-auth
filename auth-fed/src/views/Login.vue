<template>
  <div class="login">
    <main class="container">
      <div class="auth-header">
        <img :src="config.logo" alt="" />
        <div class="title">{{ config.name }}</div>
      </div>
      <a-tabs :default-active-key="loginType" @change="changeType">
        <a-tab-pane key="password" tab="密码登录" />
        <a-tab-pane key="code" tab="验证码登录" />
      </a-tabs>
      <a-form
        ref="loginForm"
        :model="model"
        :rules="rules"
        :label-col="{ span: 5 }"
        :wrapper-col="{ span: 24 }"
        @finish="handleLogin"
      >
        <div v-if="loginType === 'password'">
          <a-form-item name="account">
            <a-input
              v-model:value="model.account"
              size="large"
              placeholder="请输入登录账号"
              autofocus
            />
          </a-form-item>
          <a-form-item name="password">
            <a-input-password
              v-model:value="model.password"
              size="large"
              placeholder="请输入登录密码"
            />
          </a-form-item>
        </div>
        <div v-if="loginType === 'code'">
          <a-form-item name="mobile">
            <a-input
              v-model:value="model.mobile"
              size="large"
              autocomplete="off"
              placeholder="请输入手机号"
            />
          </a-form-item>
          <a-form-item name="vcode">
            <a-input
              v-model:value="model.vcode"
              size="large"
              autocomplete="off"
              placeholder="请输入短信验证码"
            >
              <template #suffix>
                <a-button @click="handleSend" v-if="vcodeTime <= 0"
                  >发送验证码</a-button
                >
                <a-button v-else disabled>{{ vcodeTime }}秒后重试</a-button>
              </template>
            </a-input>
          </a-form-item>
        </div>
        <a-form-item>
          <a-button type="primary" size="large" block html-type="submit">
            登录
          </a-button>
        </a-form-item>
      </a-form>
      <div class="other-action">
        <span>还没有账号？<a href="/register">立即注册</a></span>
        <span style="text-align:right">
          <a type="link" href="/forget">忘记密码</a>
        </span>
      </div>
    </main>
  </div>
</template>
<script>
import { storeToken, storePoolId, storeAppId } from '@/utils/auth'
import { getUrl } from '@/utils/request'
// import { message } from 'ant-design-vue'
import {
  getConfig,
  syncToken,
  login,
  sendCode,
  loginByCode,
} from '@/service/auth'
import { ref, onMounted, inject } from 'vue'
import { useRoute } from 'vue-router'
export default {
  name: 'Login',
  setup() {
    const route = useRoute()
    const $message = inject('$message')

    let appId = ''
    let type = 'code'
    let redirect = ''
    let config = ref({})
    const vcodeTime = ref(0)
    const loginType = ref('password')
    const model = ref({
      account: '',
      password: '',
      mobile: '',
      vcode: '',
      code: '',
    })

    const rules = ref({
      account: [{ required: true, message: '登录账号不能为空' }],
      password: [
        { required: true, message: '登录密码不能为空' },
        { min: 6, message: '登录密码至少6位', trigger: 'blur' },
      ],
      mobile: [{ required: true, message: '手机号码不能为空' }],
      vcode: [
        { required: true, message: '验证码不能为空' },
        { min: 6, max: 6, message: '请输入6位验证码' },
      ],
    })

    const changeTitle = () => {
      var link =
        document.querySelector("link[rel*='icon']") ||
        document.createElement('link')
      link.type = 'image/x-icon'
      link.rel = 'shortcut icon'
      link.href = config.value.logo
      document.getElementsByTagName('head')[0].appendChild(link)
      document.title = `${config.value.name} 登录 - Raveland统一认证中心`
    }

    const handleLogined = (json) => {
      console.log(json)
      storeToken(json.data)
      if (redirect) {
        location.href = redirect
      }
    }

    const verifyToken = () => {
      syncToken(appId).then((json) => {
        if (json && json.data.access_token) {
          handleLogined(json)
        }
      })
    }

    const initConfig = () => {
      getConfig(appId).then((json) => {
        const { id, poolId } = json.data
        config.value = json.data
        appId = id
        storeAppId(appId)
        storePoolId(poolId)
        if (!redirect) {
          redirect = getUrl(
            `oauth/authorize?client_id=${appId}&response_type=${type}`
          )
        }
        verifyToken()
        changeTitle()
      })
    }

    const changeType = (type) => {
      loginType.value = type
    }

    const handleSend = () => {
      const { mobile } = model.value
      if (!mobile || !/^1[0-9]{10}$/.test(mobile)) {
        $message.warn('请输入正确的手机号')
        return
      }
      sendCode(mobile).then((json) => {
        model.value.vcode = json.data
        $message.success('发送成功')
        vcodeTime.value = 60
        var timer = setInterval(() => {
          vcodeTime.value -= 1
          if (vcodeTime.value <= 0) {
            clearInterval(timer)
          }
        }, 1000)
      })
    }

    const handleLogin = () => {
      if (loginType.value === 'password') {
        const { account, password } = model.value
        login(appId, account, password).then(handleLogined)
      } else if (loginType.value === 'code') {
        const { mobile, vcode } = model.value
        loginByCode(appId, mobile, vcode).then(handleLogined)
      }
    }

    onMounted(() => {
      appId = route.query.app_id
      redirect = route.query.redirect_uri
      type = route.query.type || 'code'
      initConfig()
    })
    return {
      config,
      loginType,
      vcodeTime,
      model,
      rules,
      changeType,
      handleSend,
      handleLogin,
    }
  },
}
</script>
<style>
.login {
  display: flex;
  flex: auto;
  flex-direction: column;
  min-height: 0;
  min-height: 100vh;
  background-color: #f8f9ff;
  justify-content: center;
  align-items: center;
  padding: 0 6px;
}
.container {
  width: 440px;
  max-width: 100%;
  min-height: 610px;
  background-color: #fff;
  padding: 39px 38px 31px;
  margin: 24px auto;
  border-radius: 10px;
  display: flex;
  flex: none !important;
  flex-direction: column;
  box-shadow: 0 2px 10px 0 rgb(57 106 255 / 5%);
}
.container .auth-header {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 120px;
}
.container .auth-header > img {
  display: block;
  width: 80px;
  max-width: 100%;
  max-height: 100%;
  object-fit: cover;
}
.container .auth-header .title {
  font-family: 'PingFangSC-Medium';
  color: #abb9d7;
  margin-top: 0.5rem;
  font-size: 24px;
  margin-bottom: 24px;
}
.login .ant-tabs-bar {
  border-bottom: none;
}
.login .ant-tabs-nav-wrap {
  margin-bottom: 0;
}
.login .ant-tabs-ink-bar {
  background-color: #396aff;
}
.login .ant-tabs-nav .ant-tabs-tab {
  color: #aeb9d4;
  font-weight: 600;
  font-size: 16px;
}
.login .ant-tabs-nav .ant-tabs-tab-active {
  font-size: 18px;
  color: #396aff;
}
.other-action {
  color: #999;
  display: flex;
  justify-content: space-between;
  margin-top: 0.5rem;
}
.other-action a,
.other-action a:active,
.other-action a:visited {
  color: #396aff;
}
</style>
