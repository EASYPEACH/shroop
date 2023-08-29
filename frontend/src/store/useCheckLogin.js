import { defineStore } from "pinia";

export const useCheckLogin = defineStore("checkLogin", {
  state: () => {
    return {
      isLogin: true,
    };
  },
  actions: {
    setIsLogin(loginStatus) {
      this.isLogin = loginStatus;
    },
  },
});
