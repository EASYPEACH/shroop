import { defineStore } from "pinia";

export const useNotifyList = defineStore("useNotifyList", {
  state: () => {
    return {
      notifyList: [],
      hasNotify: false,
    };
  },
  getters: {
    getNotifyChecked: (state) => {
      return state.notifyList.filter((list) => !list.checked);
    },
  },
  actions: {
    setNotifyList(newNotifyList) {
      this.notifyList = newNotifyList;
    },
    setHasNotify(newHasNotify) {
      this.hasNotify = newHasNotify;
    },
  },
});
