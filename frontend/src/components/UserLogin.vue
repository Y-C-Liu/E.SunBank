<template>
  <v-container>
    <!-- 表单，用于提交登录 -->
    <v-form @submit.prevent="login">
      <!-- 手机号码输入框 -->
      <v-text-field
        label="手機號"
        v-model="phoneNumber"
        type="tel"
        required
      ></v-text-field>

      <!-- 密码输入框 -->
      <v-text-field
        label="密碼"
        v-model="password"
        type="password"
        required
      ></v-text-field>

      <!-- 登录按钮 -->
      <v-btn type="submit" color="primary">登入</v-btn>

      <!-- 错误提示 -->
      <v-alert v-if="errorMessage" type="error">{{ errorMessage }}</v-alert>
    </v-form>
  </v-container>
</template>

<script>
import apiService from '../services/apiService.js';
import { useStore } from 'vuex';

export default {
  data() {
    return {
      phoneNumber: "",
      password: "",
      errorMessage: "",
    };
  },
  setup() {
    const store = useStore();
    return { store };
  },
  methods: {
    async login() {
      try {
        const response = await apiService.loginUser({
          phoneNumber: this.phoneNumber,
          password: this.password,
        });
        console.log('登入成功', response.data);
        // 存储用户信息到 Vuex
        this.store.commit('setUser', response.data);
        // 跳转到主页
        this.$router.push('/');
      } catch (error) {
        console.error('登入失敗：', error);
        this.errorMessage = '登入失敗，請檢查手機號碼和密碼';
      }
    },
  },
};
</script>

<style scoped>
/* 您可以在这里添加一些样式 */
</style>
