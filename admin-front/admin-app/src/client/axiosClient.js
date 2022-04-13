import axios from "axios";

const client = axios.create({
  baseURL: "http://localhost:8080",
});

client.interceptors.request.use(
  (config) => {
    let token = localStorage.getItem("USER_TOKEN");

    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }

    return config;
  },

  (error) => {
    return Promise.reject(error);
  }
);

export { client };
