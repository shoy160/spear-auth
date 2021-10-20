import { message } from 'ant-design-vue'
import { getToken, getPoolId } from '@/utils/auth'
import Axios from 'axios'

Axios.defaults.withCredentials = true

const messageKey = 'msg-global'
const baseApi = process.env.VUE_APP_API
const request = Axios.create({
  baseURL: baseApi,
  timeout: 5000,
})
request.url = (api) => {
  return `${baseApi}/${api}`
}
request.interceptors.request.use(
  (config) => {
    const poolId = getPoolId()
    const token = getToken()
    if (poolId) {
      config.headers['auth-pool-id'] = poolId
    }
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    console.error(error)
    Promise.reject(error)
  }
)

// respone interceptor
request.interceptors.response.use(
  (response) => {
    try {
      var res = response.data
      if (!res.success) {
        //错误码处理
        message.error({
          content: res.message,
          duration: 2,
          key: messageKey,
        })
        return Promise.reject({
          status: res.code || 500,
          message: res.message,
        })
      }
      return res
    } catch (e) {
      return Promise.reject(e)
    }
  },
  (error) => {
    console.error(error)
    message.error({
      content: '网络异常，请稍后重试',
      duration: 2,
      key: messageKey,
    })
    return Promise.reject(error)
  }
)

export const getUrl = (api) => {
  return `${baseApi}/${api}`
}

export default request
