import { defineStore } from "pinia";

export const useNotifyList = defineStore("useNotifyList", {
  state: () => {
    return {
      notifyList: [],
      hasNotify: false,
    };
  },
  actions: {
    setNotifyList(newNotifyList) {
      this.notifyList = newNotifyList;
    },
    setHasNotify(newHasNotify) {
      this.hasNotify = newHasNotify;
    },
    getNotifyChecked(notifyList) {
      return notifyList.filter((list) => !list.checked);
    },
  },
});
