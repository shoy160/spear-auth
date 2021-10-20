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
import { storeToken, cleanToken } from '@/utils/auth'
import { getAccessToken, getAccount, logout } from '@/service/auth'
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
export default {
  name: 'Home',
  setup() {
    const route = useRoute()
    const router = useRouter()

    let type = 'code'
    let code = ''
    let appId = ''
    let secret = ''
    let token = {}

    const user = ref({})

    const redirect = () => {
      location.href = `/?app_id=${appId}&type=${type}`
    }
    const toLogin = () => {
      router.push({
        path: '/login',
        query: {
          app_id: appId,
          type: type,
        },
      })
    }
    const getToken = () => {
      getAccessToken(appId, secret, code)
        .then((json) => {
          token = json.data
          storeToken({ ...token })
          redirect()
        })
        .catch(toLogin)
    }
    const getUserInfo = () => {
      getAccount(appId)
        .then((json) => {
          user.value = json.data
        })
        .catch(toLogin)
    }

    onMounted(() => {
      type = route.query.type || 'code'
      code = route.query.code
      appId = route.query.app_id
      secret = route.query.secret
      if (code) {
        getToken()
      } else if (location.hash) {
        var split = location.hash.split('=')
        if (split.length == 2 && split[0] === '#token') {
          const token = split[1]
          storeToken({ access_token: token })
          redirect()
        }
      } else {
        getUserInfo()
      }
    })
    const handleLogout = () => {
      cleanToken()
      logout(appId).then(toLogin)
    }
    return {
      user,
      handleLogout,
    }
  },
}
</script>
<style scoped>
.home {
  text-align: center;
}
</style>
