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
import request from "@/utils/request"
// import Cookie from "js-cookie"
export default {
  name: "Home",
  data() {
    return {
      code: "",
      appId: "",
      token: {},
      user: {},
    }
  },
  mounted() {
    this.code = this.$route.query.code
    this.appId = this.$route.query.app_id
    this.getAccessToken()
  },
  methods: {
    getAccessToken() {
      request
        .get("oauth/token", {
          params: {
            grant_type: "authorization_code",
            client_id: this.appId,
            client_secret: "yvzoww1vhinsir4rmorteirfz6h4ersn",
            code: this.code,
          },
        })
        .then((json) => {
          this.token = json.data
          this.getUserInfo()
        })
    },
    getUserInfo() {
      request
        .get("account", {
          params: {
            app_id: this.appId,
          },
          headers: {
            Authorization: `${this.token.token_type} ${this.token.access_token}`,
          },
        })
        .then((json) => {
          this.user = json.data
        })
    },
    handleLogout() {
      request
        .put(
          "oauth/logout",
          {},
          {
            params: {
              app_id: this.appId,
            },
          }
        )
        .then(() => {
          this.$router.push({
            path: "/login",
            query: {
              app_id: this.appId,
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
