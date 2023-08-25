import axios from "axios";

const api = axios.create({
  baseURL: "https://shroop-backend.shop",
  withCredentials: true,
});

api.interceptors.request.use(
  (config) => {
    return config;
  },
  (err) => {
    return Promise.reject(err);
  },
);
api.interceptors.response.use(
  (config) => {
    return config;
  },
  (err) => {
    return Promise.reject(err);
  },
);

export default api;
