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

const BASE_URL = 'https://www.nuri777.kro.kr';

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
