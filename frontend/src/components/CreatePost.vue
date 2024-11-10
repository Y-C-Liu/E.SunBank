<template>
  <v-container>
    <v-form @submit.prevent="createPost">
      <v-textarea
        label="内容"
        v-model="content"
        required
      ></v-textarea>
      <v-text-field
        label="圖片URL"
        v-model="image"
      ></v-text-field>
      <v-btn type="submit" color="primary">發布</v-btn>
      <!-- 错误提示 -->
      <v-alert v-if="errorMessage" type="error">{{ errorMessage }}</v-alert>
    </v-form>
  </v-container>
</template>

<script>
import { ref, computed } from 'vue';
import { useStore } from 'vuex';
import apiService from '../services/apiService.js';

export default {
  name: 'CreatePost',
  setup() {
    const store = useStore();
    const user = computed(() => store.state.user);

    const content = ref('');
    const image = ref('');
    const errorMessage = ref('');

    const createPost = async () => {
      try {
        if (!user.value) {
          errorMessage.value = '請先登入後再發帖';
          return;
        }
        const response = await apiService.createPost({
          userId: user.value.userId,
          content: content.value,
          image: image.value,
        });
        console.log('貼文創建成功', response.data);
        // 发布成功后跳转到主页
        window.location.href = '/';
      } catch (error) {
        console.error('無法建立發文：', error);
        errorMessage.value = '無法建立發文，請重試';
      }
    };

    return {
      content,
      image,
      errorMessage,
      createPost,
    };
  },
};
</script>
