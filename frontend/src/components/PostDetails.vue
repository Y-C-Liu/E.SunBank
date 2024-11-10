<template>
    <v-container>
      <!-- 帖子详情 -->
      <v-card>
        <v-card-title>{{ post.content }}</v-card-title>
        <v-card-subtitle>作者: {{ post.user.userName }}</v-card-subtitle>
        <v-img v-if="post.image" :src="post.image" max-height="400px"></v-img>
      </v-card>
  
      <!-- 评论列表 -->
      <v-list>
        <v-subheader>評論</v-subheader>
        <v-list-item v-for="comment in comments" :key="comment.commentId">
          <v-list-item-content>
            <v-list-item-title>{{ comment.content }}</v-list-item-title>
            <v-list-item-subtitle>作者: {{ comment.user.userName }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list>
  
      <!-- 添加评论 -->
      <v-form @submit.prevent="addComment">
        <v-textarea
          label="新增評論"
          v-model="newComment"
          required
        ></v-textarea>
        <v-btn type="submit" color="primary">提交評論</v-btn>
      </v-form>
  
      <!-- 错误提示 -->
      <v-alert v-if="errorMessage" type="error">{{ errorMessage }}</v-alert>
    </v-container>
  </template>
  
  <script>
  import apiService from '../services/apiService.js';
  
  export default {
    data() {
      return {
        post: {},
        comments: [],
        newComment: '',
        errorMessage: '',
      };
    },
    created() {
      this.fetchPost();
      this.fetchComments();
    },
    methods: {
      async fetchPost() {
        try {
          const postId = this.$route.params.postId;
          const response = await apiService.getPostById(postId);
          this.post = response.data;
        } catch (error) {
          console.error('取得貼文失敗：', error);
        }
      },
      async fetchComments() {
        try {
          const postId = this.$route.params.postId;
          const response = await apiService.getCommentsByPostId(postId);
          this.comments = response.data;
        } catch (error) {
          console.error('取得評論失敗：', error);
        }
      },
      async addComment() {
        try {
          const userId = this.$store.state.userId;
          const postId = this.$route.params.postId;
  
          const response = await apiService.addComment({
            userId: userId,
            postId: postId,
            content: this.newComment,
          });
          console.log('評論添加成功', response.data);
          this.newComment = '';
          this.fetchComments();
        } catch (error) {
          console.error('新增評論失敗：', error);
          this.errorMessage = '新增評論失敗，請重試';
        }
      },
    },
  };
  </script>
  
  <style scoped>
  /* 样式 */
  </style>
  