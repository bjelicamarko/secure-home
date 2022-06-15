const { defineConfig } = require("@vue/cli-service");
const fs = require("fs");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    http2: true,
    https: {
      key: fs.readFileSync("keys/adminappfront.key"),
      cert: fs.readFileSync("keys/adminappfront.cer"),
    },
  },
});
