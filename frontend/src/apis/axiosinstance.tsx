import axios, { AxiosInstance } from 'axios';

export const interceptors = (instance: AxiosInstance, token: string | null) => {
  instance.interceptors.request.use(
    config => {
      config.headers.Authorization = `Bearer ${token}`;
      return config;
    },
    error => Promise.reject(error.response),
  );
  return instance;
};

// const BASE_URL = 'http://203.237.107.65:80/api';
const BASE_URL = 'http://localhost:8080';

const axiosApi = (url: string, options?: object) => {
  const instance = axios.create({ baseURL: url, ...options });
  interceptors(instance, null);
  return instance;
};

const axiosAuthApi = (url: string, token: string | null, options?: object) => {
  const instance = axios.create({ baseURL: url, ...options });
  interceptors(instance, token);
  return instance;
};

export const axBase = () => axiosApi(BASE_URL);
export const axAuth = (token: string) => axiosAuthApi(BASE_URL, token);
