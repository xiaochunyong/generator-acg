const path = require('path');

let publicPath = './';
const envParam = process.env.ENV || '';
if (envParam === 'uat') {
  publicPath = 'https://mtabc.aihuishou.com/uat/inspection/';
} else if (envParam === 'pro') {
  publicPath = 'https://mtabc.aihuishou.com/inspection/';
}

module.exports = {
  entry: 'src/index.jsx',
  alias: {
    '@': path.resolve(__dirname, './src/'),
  },
  publicPath,
  plugins: [
    ['ice-plugin-fusion', {
      themePackage: '@icedesign/theme',
    }],
    ['ice-plugin-moment-locales', {
      locales: ['zh-cn'],
    }],
  ],
  hash: true,
  devServer: {
    historyApiFallback: true,
  },
  chainWebpack: (config, { command }) => {
    console.log(command)
    if (command === 'build' || true) {
      config.optimization.splitChunks({
        cacheGroups: {
          vendor: {
            test: /[\\/]node_modules[\\/]/, // 匹配 node_modules 目录
            name: 'vendor',
            chunks: 'all',
            minChunks: 1,
          },
          app: {
            test: /[\\/]src[\\/]/, // 匹配 node_modules 目录
            name: 'app',
            chunks: 'all',
            minChunks: 1,
          },
        },
        chunks: 'async',
        minSize: 100000,
        maxSize: 300000,
        minChunks: 1,
        maxAsyncRequests: 5,
        maxInitialRequests: 3,
        automaticNameDelimiter: '~',
        name: true,
      }).minimize(true);
    }
  },
};
