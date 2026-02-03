import axios from "axios";

// Create an Axios instance with common configuration
const axiosInstance = axios.create({
  baseURL: "https://dummyjson.com",
  timeout: 10000,
});

axios.interceptors.request.use((config) => {
  const token = "Bearer Token";
  config.headers.Authorization = token;
  return config;
});

axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    // Handle the error globally
    console.error("An error occurred:", error);
    return Promise.reject(error);
  }
);

export default axiosInstance;
