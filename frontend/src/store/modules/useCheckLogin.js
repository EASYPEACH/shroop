import { defineStore } from "pinia";

export const useCheckLogin = defineStore("checkLogin", {
  state: () => {
    return {
      isLogin: false,
      id: 0,
      nickName: "",
    };
  },
  actions: {
    setIsLogin(status) {
      this.isLogin = status.result;
      this.id = status.memberId;
      this.nickName = status.nickname;
    },
  },
});
