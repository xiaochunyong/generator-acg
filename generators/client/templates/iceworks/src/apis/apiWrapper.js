import assert from 'assert'
import { Message } from '@alifd/next';
import axios from 'axios';
import qs from 'qs';
import serverConfig from '../config/server.js';

function wrap(method, url, body, queryParameters, form) {
  if (url.indexOf('://') < 0) {
    url = serverConfig.apiEndpoint + url;
  }
  method = method.toLowerCase();
  const returnObj = {};
  const options = {
    url,
    method,
    params: queryParameters,
  };
  if (body) {
    options.data = body;
  } else if (method !== 'get') {
    options.data = qs.stringify(form);
  }
  options.paramsSerializer = function (params) {
    return qs.stringify(params);
  };
  options.headers = options.headers || {};
  // 后端检测ajax请求
  if (!options.headers['X-Requested-With']) {
    options.headers['X-Requested-With'] = 'XMLHttpRequest';
  }
  options.maxRedirects = 0;
  options.withCredentials = true;
  returnObj.options = options;
  returnObj.invoke = function (onSuccess, onError, onFinally) {
    return axios.request(options)
      .then((res) => {
        assert(res.status >= 200 && res.status < 300, '预期status值为200..<300');

        if (res.data.code === 0) {
          return res.data;
        } else if (res.data.code === 302) {
          window.location.href = res.data.data;
        } else if (res.data.code === 400) {
          // 业务逻辑参数错误
          Message.error(`参数错误: ${res.data.message}`);
          throw new Error(`${res.data.message} (${res.data.code})`);
        } else {
          // 系统自定义的错误码
          Message.error(`${res.data.message} (${res.data.code})`);
          throw new Error(`${res.data.message} (${res.data.code})`);
        }
      })
      .then(onSuccess)
      .catch((error) => {
        if (error.response) {
          // The request was made and the server responded with a status code
          // that falls out of the range of 2xx
          // Spring框架拦截的数据类型错误, 解析错误, Server Error
          if (error.response.status >= 400 && error.response.status < 500) {
            Message.error(`参数错误: ${error.response.data.message}`);
          } else if (error.response.status >= 500) {
            Message.error(`系统错误: ${error.response.data.message}`);
          }
          console.log(error.response.status, error.response.data, error.response.headers);
        } else if (error.request) {
          // The request was made but no response was received
          // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
          // http.ClientRequest in node.js
          console.log(error.request);
        } else {
          // Something happened in setting up the request that triggered an Error
          console.log('Error', error.message);
        }
        // eslint-disable-next-line no-unused-expressions
        onError && onError(error);
      })
      .finally(onFinally);
  };
  return returnObj;
}

export default wrap;
