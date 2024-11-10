<template>
  <v-container>
    <v-form @submit.prevent="register">
      <!-- 用户名 -->
      <v-text-field
        label="使用者名稱"
        v-model="userName"
        name="userName"
        required
      ></v-text-field>

      <!-- 电子邮件 -->
      <v-text-field
        label="電子郵件"
        v-model="email"
        name="email"
        type="email"
        required
      ></v-text-field>

      <!-- 密码 -->
      <v-text-field
        label="密碼"
        v-model="password"
        name="password"
        type="password"
        required
        minlength="6"
      ></v-text-field>

      <!-- 手机号码 -->
      <v-text-field
        label="手機號碼"
        v-model="phoneNumber"
        name="phoneNumber"
        type="tel"
        required
      ></v-text-field>

      <!-- 自我介绍 -->
      <v-textarea
        label="自我介紹"
        v-model="biography"
        name="biography"
        rows="3"
        auto-grow
      ></v-textarea>

      <!-- 注册按钮 -->
      <v-btn type="submit" color="primary">註冊</v-btn>

      <!-- 错误信息提示 -->
      <v-alert v-if="errorMessage" type="error">{{ errorMessage }}</v-alert>
    </v-form>
  </v-container>
</template>

<script>
import apiService from '../services/apiService.js';

export default {
  data() {
    return {
      userName: "",
      email: "",
      password: "",
      phoneNumber: "",
      biography: "",
      errorMessage: "",
    };
  },
  methods: {
    async register() {
      // 清空错误信息
      this.errorMessage = "";

      try {
        // 发起注册请求
        const response = await apiService.registerUser({
        userName: this.userName,
        email: this.email,
        password: this.password,
        phoneNumber: this.phoneNumber,
        biography: this.biography || "",
      });
        
        // 注册成功后的反馈逻辑
        console.log('註冊成功', response.data);
        alert('註冊成功，請登入！');
        this.$router.push('/login');
      } catch (error) {
        // 捕获并显示错误信息
        console.error('註冊失敗：', error);
        this.errorMessage = '註冊失敗：' + (error.response?.data || error.message);
      }
    },
  },
};
</script>

<style scoped>
/* 可选样式 */
</style>
