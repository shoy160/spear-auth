module.exports = {
//   publicPath: process.env.NODE_ENV === "development" ? "/" : "/",
  devServer: {
    proxy: {
      "/api": {
        target: "http://192.168.2.14:9862",
        secure: false,
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          "^/api": "",
        },
      },
    },
  },
}
