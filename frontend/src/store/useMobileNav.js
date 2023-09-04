import { defineStore } from "pinia";

export const useMobileNav = defineStore("mobileNav", {
  state: () => {
    return {
      isMobileNav: false,
    };
  },
  actions: {
    setIsMobileNav(status) {
      this.isMobileNav = status;
    },
  },
});
