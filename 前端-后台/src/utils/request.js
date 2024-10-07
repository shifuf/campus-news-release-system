import { message } from 'ant-design-vue';
import axios from 'axios';

// 创建axios实例
const http = axios.create({
  baseURL: '/api', // 你的API基础路径
  timeout: 10000, // 请求超时时间
  headers: {
    'Content-Type': 'application/json',
  },
});

// 封装GET请求
export function get(url, params = {}) {
  return new Promise((resolve, reject) => {
    http
      .get(url, { params })
      .then(response => {
        resolve(response.data);
      })
      .catch(error => {
        message.error(error.message+'请联系管理员');
        reject(handleError(error));
      });
  });
}

// 封装POST请求
export function post(url, data) {
  return new Promise((resolve, reject) => {
    http
      .post(url, data)
      .then(response => {
        resolve(response.data);
      })
      .catch(error => {
        message.error(error.message+'请联系管理员');
        reject(handleError(error));
      });
  });
}

