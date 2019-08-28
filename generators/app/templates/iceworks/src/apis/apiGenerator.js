const swaggerGen = require('swagger-vue');
const fs = require('fs');
const path = require('path');
const http = require('http');
const https = require('https');

const env = process.env.ENV || 'dev';

let host = 'http://localhost:8080';
let requestInvoker = http;

// todo: api地址内网化
switch (env.toLowerCase()) {
  case 'pro':
    host = 'https://007.aihuishou.com';
    requestInvoker = https;
    break;
  case 'uat':
    host = 'https://uat007.aihuishou.com';
    requestInvoker = https;
    break;
  default:
    break;
}

const consFile = fs.createWriteStream(path.join(__dirname, '../utils/cons.js'));
const consGenUrl = `${host}/v2/cons/gen`;
console.log(`generating constant file: ${consGenUrl}`);
requestInvoker.get(consGenUrl, (response) => {
  response.pipe(consFile);
});


const url = `${host}/v2/api-docs`;
console.log(`generating sdk: ${url}`);

const file = fs.createWriteStream(path.join(__dirname, './swagger.json'));
requestInvoker.get(url, (response) => {
  const stream = response.pipe(file);
  stream.on('finish', () => {
    const jsonData = require('./swagger.json');
    const opt = {
      swagger: jsonData,
      moduleName: 'api',
      className: 'api'
    }
    let codeResult = swaggerGen(opt);
    fs.readFile(path.join(__dirname, './api_replace.js'), 'utf8', (err, data) => {
      // console.log(codeResult.match(/export const request[\s\S\n]*?\/\*/g))
      codeResult = codeResult.replace(/export const request[\s\S]*?\/\*/g, `${data}\r\n/*`);
      fs.writeFileSync(path.join(__dirname, './api.js'), codeResult);
      console.log(`sdk generated: ${host}/v2/api-docs`);
    });
  })
});
