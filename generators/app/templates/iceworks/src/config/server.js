import {env} from './app';

// eslint-disable-next-line import/no-mutable-exports
let serverConfig;

switch (env) {
  case 'uat':
    serverConfig = {
      domain: '',
      apiEndpoint: '//uat007.aihuishou.com',
      websocketEndpoint: './inspection-websocket',
    };
    break;
  case 'pro':
    serverConfig = {
      domain: '',
      apiEndpoint: '//007.aihuishou.com',
      websocketEndpoint: './inspection-websocket',
    };
    break;
  default:
    serverConfig = {
      domain: 'http://localhost:3000',
      apiEndpoint: '//localhost:8080',
      websocketEndpoint: 'http://localhost:8080/inspection-websocket',
      // apiEndpoint: "http://192.168.9.47:8080",
      // apiEndpoint: 'http://192.168.9.220:8080',
    };
    break;
}

export default serverConfig;
