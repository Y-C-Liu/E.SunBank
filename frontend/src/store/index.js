// store/index.js
import { createStore } from 'vuex';

export default createStore({
  state: {
    user: null, // 存储当前登录的用户信息
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
    },
    clearUser(state) {
      state.user = null;
    },
  },
});
