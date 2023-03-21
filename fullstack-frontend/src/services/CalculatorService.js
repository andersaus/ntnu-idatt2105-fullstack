import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/api/calculations",
  withCredentials: false,
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
});

export default {
  async getCalculations(userID) {
    return (await apiClient.get("/user/" + userID)).data;
  },
  /* async getResult(userID, calculationID) {
    return apiClient.get("/user/" + userID + "/calculation/" + calculationID +"/result");
  }, */
  async postExpression(userID, expression) {
    return apiClient.post("/user/" + userID + "/add-calculation", expression);
  },
};
