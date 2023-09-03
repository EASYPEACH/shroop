import { defineStore } from "pinia";

export const useSearchProduct = defineStore("searchProduct", {
  state: () => {
    return {
      searchTitle: "",
    };
  },
  actions: {
    setSearchTitle(title) {
      this.searchTitle = title;
    },
  },
});
