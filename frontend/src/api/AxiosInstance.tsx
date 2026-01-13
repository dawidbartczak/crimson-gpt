import axios from "axios";

const BASE_URL = "http://127.0.0.1:8080/api/v1";

const axiosInstance = axios.create({
    baseURL: BASE_URL,
    headers: {
        "Content-Type": "application/json",
    }
});

axiosInstance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token");

        if (token) config.headers.Authorization = `Bearer ${token}`;

        return config;
    },
);

axiosInstance.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        return error.response;
    }
);

export default axiosInstance;
