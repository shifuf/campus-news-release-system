import axios from 'axios';

// 创建axios实例
const http = axios.create({
  baseURL: '/api', 
  timeout: 10000,
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
        reject(handleError(error));
      });
  });
}

