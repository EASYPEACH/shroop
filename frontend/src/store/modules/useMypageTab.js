import { defineStore } from "pinia";

export const useMypageTab = defineStore("mypageTab", {
  state: () => {
    return {
      tabIndex: 0,
    };
  },
  actions: {
    setTabIndex(index) {
      this.tabIndex = index;
    },
  },
});
