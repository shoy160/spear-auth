import request from "@/utils/request"

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
    "account/login",
    {
      appId: appId,
      account: account,
      password: password,
    },
    {
      headers: {
        "yz-auth-app-id": appId,
      },
    }
  )
}

export const loginByCode = (appId, mobile, vcode) => {
  return request.post(
    "account/login/code",
    {
      app_id: appId,
      mobile: mobile,
      vcode: vcode,
    },
    {
      headers: {
        "yz-auth-app-id": appId,
      },
    }
  )
}

export const getAccount = (appId) => {
  return request.get("account", {
    params: {
      app_id: appId,
    },
  })
}

export const logout = (appId) => {
  return request.put(
    "oauth/logout",
    {},
    {
      params: {
        app_id: appId,
      },
    }
  )
}

export const getAccessToken = (appId, secret, code) => {
  return request.get("oauth/token", {
    params: {
      grant_type: "authorization_code",
      client_id: appId,
      client_secret: secret,
      code: code,
    },
  })
}
