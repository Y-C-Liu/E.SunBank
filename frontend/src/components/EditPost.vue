<template>
    <v-container>
      <v-form @submit.prevent="updatePost">
        <v-textarea
          label="內容"
          v-model="content"
          required
        ></v-textarea>
        <v-text-field
          label="圖片URL"
          v-model="image"
        ></v-text-field>
        <v-btn type="submit" color="primary">更新</v-btn>
      </v-form>
    </v-container>
  </template>
  
  <script>
  import { ref } from 'vue';
  import apiService from '../services/apiService';
  
  export default {
    name: 'EditPost',
    props: ['postId'],
    setup(props) {
      const content = ref('');
      const image = ref('');
  
      const fetchPost = async () => {
        try {
          const response = await apiService.getPostById(props.postId);
          content.value = response.data.content;
          image.value = response.data.image;
        } catch (error) {
          console.error('無法獲取發文：', error);
        }
      };
  
      const updatePost = async () => {
        try {
          await apiService.updatePost(props.postId, {
            content: content.value,
            image: image.value,
          });
          // 更新成功后跳转到主页
          window.location.href = '/home';
        } catch (error) {
          console.error('無法更新發文：', error);
        }
      };
  
      fetchPost();
  
      return {
        content,
        image,
        updatePost,
      };
    },
  };
  </script>
  
  <style scoped>
  /* 可选的样式设置 */
  </style>
  