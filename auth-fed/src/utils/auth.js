import Cookie from 'js-cookie'
const KEY_TOKEN = '__auth_token'
const KEY_REFRESH_TOKEN = '__auth_refresh_token'
const KEY_POOL_ID = '__auth_pool_id'
const KEY_APP_ID = '__auth_app_id'

export const storePoolId = (poolId) => {
  Cookie.set(KEY_POOL_ID, poolId)
}

export const getPoolId = () => {
  return Cookie.get(KEY_POOL_ID)
}

export const storeAppId = (appId) => {
  Cookie.set(KEY_APP_ID, appId)
}

export const getAppId = () => {
  return Cookie.get(KEY_APP_ID)
}

/**
 * 存储Token
 * @param {*} json
 */
export const storeToken = (json) => {
  Cookie.set(KEY_TOKEN, json.access_token, {
    expires: json.expires_in / (60 * 60 * 24),
  })
  if (Object.prototype.hasOwnProperty.call(json, 'refresh_token')) {
    Cookie.set(KEY_REFRESH_TOKEN, json.refresh_token, {
      expires: (json.expires_in * 2) / (60 * 60 * 24),
    })
  }
}

/**
 * 获取Token
 * @returns
 */
export const getToken = () => {
  return Cookie.get(KEY_TOKEN)
}

/**
 * 清空Token
 */
export const cleanToken = () => {
  Cookie.remove(KEY_TOKEN)
  Cookie.remove(KEY_REFRESH_TOKEN)
}
