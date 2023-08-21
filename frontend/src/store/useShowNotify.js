import { defineStore } from "pinia";

export const useShowNotify = defineStore("showNotify", {
  state: () => {
    return {
      isShowNotify: false,
    };
  },
  actions: {
    setIsShowNotify() {
      this.isShowNotify = !this.isShowNotify;
    },
  },
});
