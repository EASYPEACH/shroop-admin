import { defineStore } from "pinia";

export const useCheckLogin = defineStore("checkLogin", {
  state: () => {
    return {
      isLogin: false,
    };
  },
  actions: {
    setIsLogin(status) {
      this.isLogin = status;
    },
  },
});
