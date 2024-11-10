<template>
  <v-container>
    <v-row>
      <v-col>
        <!-- 根据用户登录状态显示不同的内容 -->
        <div v-if="user">
          <span>欢迎，{{ user.userName }}！</span>
          <v-btn color="primary" @click="logout">登出</v-btn>
        </div>
        <div v-else>
          <v-btn color="primary" @click="$router.push('/login')">登入</v-btn>
          <v-btn color="secondary" @click="$router.push('/register')">註冊</v-btn>
        </div>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <!-- 只有登录用户才能看到发文按钮 -->
        <v-btn v-if="user" color="primary" @click="$router.push('/create-post')">新增发文</v-btn>
        <h2>所有发文</h2>
        <div v-for="post in posts" :key="post.postId">
          <p>{{ post.content }}</p>
          <img :src="post.image" alt="Post Image" />
          <!-- 留言部分 -->
          <div>
            <h3>留言：</h3>
            <div v-for="comment in post.comments" :key="comment.commentId">
              <p>{{ comment.content }} - {{ comment.user.userName }}</p>
            </div>
            <!-- 只有登录用户才能留言 -->
            <div v-if="user">
              <v-textarea v-model="newComments[post.postId]" label="新增留言"></v-textarea>
              <v-btn @click="addComment(post.postId)">提交留言</v-btn>
            </div>
          </div>
          <!-- 只有作者本人才能编辑或删除发文 -->
          <div v-if="user && user.userId === post.user.userId">
            <v-btn color="primary" @click="editPost(post)">编辑发文</v-btn>
            <v-btn color="error" @click="deletePost(post.postId)">删除发文</v-btn>
          </div>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { ref, computed } from 'vue';
import { useStore } from 'vuex';
import apiService from '../services/apiService';

export default {
  name: 'HomePage',
  setup() {
    const store = useStore();
    const user = computed(() => store.state.user);

    const posts = ref([]);
    const newComments = ref({}); // 用于存储每个发文的新留言内容

    const fetchPosts = async () => {
      try {
        const response = await apiService.getAllPosts();
        posts.value = response.data;
      } catch (error) {
        console.error('無法取得發文：', error);
      }
    };

    const deletePost = async (postId) => {
      try {
        await apiService.deletePost(postId);
        fetchPosts(); // 删除成功后重新加载发文
      } catch (error) {
        console.error('無法刪除發文：', error);
      }
    };

    const logout = () => {
      store.commit('clearUser');
      // 可以添加登出后的其他处理，例如跳转到登录页面
      this.$router.push('/login');
    };

    // HomePage.vue 中的 addComment 方法
const addComment = async (postId) => {
  try {
    const content = newComments.value[postId];
    if (!content) {
      return;
    }
    await apiService.addComment({
      user: { userId: user.value.userId }, // 包装 user 对象
      post: { postId: postId }, // 包装 post 对象
      content: content,
    });
    newComments.value[postId] = '';
    fetchPosts(); // 重新加载发文以显示新留言
  } catch (error) {
    console.error('無法添加留言：', error);
  }
};

    const editPost = (post) => {
      // 跳转到编辑发文页面
      window.location.href = `/edit-post/${post.postId}`;
    };

    fetchPosts();

    return {
      user,
      posts,
      newComments,
      deletePost,
      logout,
      addComment,
      editPost,
    };
  },
};
</script>

<style scoped>
/* 可选的样式设置 */
</style>
