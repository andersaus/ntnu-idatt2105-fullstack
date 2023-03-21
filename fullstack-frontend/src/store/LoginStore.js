import { defineStore } from "pinia";
import LoginService from "../services/LoginService";

export const useLoginStore = defineStore('loginStore', {
    state: () => ({
        isLoggedIn: false,
        userID: null,
        username: '',
        //token: null,
    }),
    getters: {
        isAuthenticated: (state) => state.isLoggedIn,
        getUsername: (state) => state.user?.username,
        getID: (state) => state.user?.userID,
    },
    actions: {
        async fetchUserDetails(username) {
            const response = await LoginService.getUser(username);
            if(response.status === 200) {
                this.userID = response.data.userID;
                this.username = response.data.username;
            }
        },
        async login(credentials) {
            try {
                const response = await LoginService.checkPassword(credentials);
                if (response.status === 200) {
                    // this.isLoggedIn = true;
                    this.userID = response.data;
                    this.userID
                    this.username = credentials.username;
                    await this.fetchUserDetails(credentials.username);
                }
                console.log(this.isLoggedIn);
                console.log(this.userID);
                console.log(this.username);
                return response;
            } catch (error) {
                throw error;
            }
        },
    }
})