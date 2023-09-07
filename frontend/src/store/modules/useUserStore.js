import { defineStore } from "pinia";

export const useUserStore = defineStore("user", {
  state: () => ({
    loginId: "",
    nickname: "",
    password: "",
    phoneNumber: "",
    agreeShroop: false,
    agreePersonal: false,
    agreeIdentify: false,
  }),
  actions: {
    signUp(user) {
      this.loginId = user.loginId;
      this.nickname = user.nickname;
      this.password = user.password;
      this.phoneNumber = user.phoneNumber;
      this.agreeShroop = user.agreeShroop;
      this.agreePersonal = user.agreePersonal;
      this.agreeIdentify = user.agreeIdentify;
    },
  },
});
