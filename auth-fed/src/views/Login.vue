<template>
  <div class="login">
    <main class="container">
      <div class="auth-header">
        <span><img :src="config.logo" alt=""/></span>
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
        @finish="handlerLogin"
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
              placeholder="请输入手机号"
            />
          </a-form-item>
          <a-form-item name="vcode">
            <a-input
              v-model:value="model.vcode"
              size="large"
              placeholder="请输入短信验证码"
            >
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
import { getUrl } from "@/utils/request"
import { getConfig, syncToken, login, loginByCode } from "@/service/index"
import Cookie from "js-cookie"
// import User from "@ant-design/icons/User"
export default {
  name: "Login",
  components: {
    // User,
  },
  data() {
    return {
      loginType: "password",
      appId: "",
      type: "code",
      redirect: "",
      config: {},
      model: {
        account: "",
        password: "",
        mobile: "",
        vcode: "",
        code: "",
      },
      rules: {
        account: [{ required: true, message: "登录账号不能为空" }],
        password: [
          { required: true, message: "登录密码不能为空" },
          { min: 6, message: "登录密码至少6位", trigger: "blur" },
        ],
        mobile: [{ required: true, message: "手机号码不能为空" }],
        vcode: [{ required: true, message: "验证码不能为空" }],
      },
    }
  },
  mounted() {
    this.appId = this.$route.query.app_id
    this.redirect = this.$route.query.redirect_uri
    this.type = this.$route.query.type || "code"
    this.getConfig()
  },
  methods: {
    changeTitle() {
      var link =
        document.querySelector("link[rel*='icon']") ||
        document.createElement("link")
      link.type = "image/x-icon"
      link.rel = "shortcut icon"
      link.href = this.config.logo
      document.getElementsByTagName("head")[0].appendChild(link)
      document.title = `${this.config.name} 登录 - 云智云统一认证中心`
    },
    getConfig() {
      getConfig(this.appId).then((json) => {
        this.config = json.data
        this.appId = this.config.id
        Cookie.set("auth_pool_id", this.config.poolId)
        if (!this.redirect) {
          this.redirect = getUrl(
            `oauth/authorize?client_id=${this.appId}&response_type=${this.type}`
          )
        }
        this.verifyToken()
        this.changeTitle()
      })
    },
    verifyToken() {
      syncToken(this.appId).then((json) => {
        if (json && json.data.access_token) {
          this.handlerLogined(json)
        }
      })
    },
    changeType(type) {
      this.loginType = type
    },
    handlerLogined(json) {
      console.log(json)
      Cookie.set("auth_token", json.data.access_token)
      Cookie.set("refresh_token", json.data.refresh_token, {
        expires: (json.data.expires_in * 2) / (60 * 60 * 24),
      })
      if (this.redirect) {
        location.href = this.redirect
      }
    },
    handlerLogin() {
      //
      if (this.loginType === "password") {
        login(this.appId, this.model.account, this.model.password).then(
          this.handlerLogined
        )
      } else if (this.loginType === "code") {
        loginByCode(this.appId, this.model.mobile, this.model.vcode).then(
          this.handlerLogined
        )
      }
    },
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
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.container .auth-header .title {
  font-family: "PingFangSC-Medium";
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
