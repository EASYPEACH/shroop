import { defineStore } from "pinia";

export const useCheckLogin = defineStore("checkLogin", {
  state: () => {
    return {
      isLogin: true,
    };
  },
  actions: {
    setIsLogin() {
      this.isLogin = !this.isLogin;
    },
  },
});
