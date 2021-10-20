import request from '@/utils/request'

export const getConfig = (appId) => {
  return request.get(`oauth/config`, {
    params: {
      app_id: appId,
    },
  })
}

export const syncToken = (appId) => {
  return request.get(`oauth/sync`, {
    params: {
      app_id: appId,
    },
  })
}

export const login = (appId, account, password) => {
  return request.post(
    'account/login',
    {
      appId: appId,
      account: account,
      password: password,
    },
    {
      headers: {
        'auth-app-id': appId,
      },
    }
  )
}

/**
 * 发送验证码
 * @param {*} mobile
 * @returns
 */
export const sendCode = (mobile) => {
  return request.post(`account/code/${mobile}`)
}

/**
 * 验证码登录
 * @param {*} appId
 * @param {*} mobile
 * @param {*} vcode
 * @returns
 */
export const loginByCode = (appId, mobile, vcode) => {
  return request.post(
    'account/login/code',
    {
      appId: appId,
      mobile: mobile,
      verifyCode: vcode,
    },
    {
      headers: {
        'auth-app-id': appId,
      },
      params: {
        app_id: appId,
      },
    }
  )
}

export const getAccount = (appId) => {
  return request.get('account', {
    params: {
      app_id: appId,
    },
  })
}

export const logout = (appId) => {
  return request.put(
    'oauth/logout',
    {},
    {
      params: {
        app_id: appId,
      },
    }
  )
}

export const getAccessToken = (appId, secret, code) => {
  return request.get('oauth/token', {
    params: {
      grant_type: 'authorization_code',
      client_id: appId,
      client_secret: secret,
      code: code,
    },
  })
}
