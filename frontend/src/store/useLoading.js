import { defineStore } from "pinia";

export const useApiLoading = defineStore("loading", {
  state: () => {
    return {
      isLoading: false,
    };
  },
  actions: {
    setIsLoading(status) {
      this.isLoading = status;
    },
  },
});
