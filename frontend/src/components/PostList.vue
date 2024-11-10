<template>
    <v-container>
      <v-list>
        <v-list-item v-for="post in posts" :key="post.postId">
          <v-list-item-content>
            <v-list-item-title>{{ post.content }}</v-list-item-title>
            <v-list-item-subtitle>{{ post.user.userName }}</v-list-item-subtitle>
            <!-- 显示图片 -->
            <v-img v-if="post.image" :src="post.image" max-height="200px"></v-img>
            <!-- 查看详情按钮 -->
            <v-btn @click="goToPost(post.postId)">看詳情</v-btn>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-container>
  </template>
  
  <script>
  import apiService from '../services/apiService.js';
  
  export default {
    data() {
      return {
        posts: [],
      };
    },
    created() {
      this.fetchPosts();
    },
    methods: {
      async fetchPosts() {
        try {
          const response = await apiService.getAllPosts();
          this.posts = response.data;
        } catch (error) {
          console.error('取得貼文失敗：', error);
        }
      },
      goToPost(postId) {
        this.$router.push(`/posts/${postId}`);
      },
    },
  };
  </script>
  
  <style scoped>
  /* 样式 */
  </style>
  