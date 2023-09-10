import { defineStore } from "pinia";

export const useShowNotify = defineStore("showNotify", {
  state: () => {
    return {
      isShowNotify: false,
    };
  },
  actions: {
    setIsShowNotify(status) {
      this.isShowNotify = status;
    },
  },
});
