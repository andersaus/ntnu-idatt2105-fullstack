import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/api/users",
  withCredentials: false,
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
});

export default {
  async checkPassword(credentials) {
    return apiClient.post("/check-password", credentials);
  },
  async getUser(username) {
    return apiClient.get("/" + username);
  },
};