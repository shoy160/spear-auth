<template>
  <div class="home">
    <div>id: {{ user.id }}</div>
    <div>account: {{ user.account }}</div>
    <div>email: {{ user.email }}</div>
    <div>mobile: {{ user.mobile }}</div>
    <div>name: {{ user.name }}</div>
    <div>
      {{ user }}
    </div>
    <a-button @click="handleLogout" type="primary" style="margin-top:50px"
      >退出登录</a-button
    >
  </div>
</template>

<script>
import { getAccessToken, getAccount, logout } from "@/service/index"
import Cookie from "js-cookie"
export default {
  name: "Home",
  data() {
    return {
      type: "code",
      code: "",
      appId: "",
      secret: "",
      token: {},
      user: {},
    }
  },
  mounted() {
    this.code = this.$route.query.code
    this.type = this.$route.query.type || "code"
    this.appId = this.$route.query.app_id
    this.secret = this.$route.query.secret

    if (this.code) {
      this.getAccessToken()
    } else if (location.hash) {
      var split = location.hash.split("=")
      if (split.length == 2 && split[0] === "#token") {
        this.token = split[1]
        Cookie.set("auth_token", this.token)
        this.getUserInfo()
      }
    }
  },
  methods: {
    getAccessToken() {
      getAccessToken(this.appId, this.secret, this.code)
        .then((json) => {
          this.token = json.data
          this.getUserInfo()
        })
        .catch(() => {
          this.$router.push({
            path: "/login",
            query: {
              app_id: this.appId,
            },
          })
        })
    },
    getUserInfo() {
      getAccount(this.appId).then((json) => {
        this.user = json.data
      })
    },
    handleLogout() {
      Cookie.remove("auth_token")
      Cookie.remove("refresh_token")
      logout(this.appId).then(() => {
        this.$router.push({
          path: "/login",
          query: {
            app_id: this.appId,
            type: this.type,
          },
        })
      })
    },
  },
}
</script>
<style scoped>
.home {
  text-align: center;
}
</style>
