import axios from 'axios';

// 配置 axios 实例
const apiClient = axios.create({
  baseURL: 'http://localhost:8081/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default {
  // 用户注册
  async registerUser(user) {
    return apiClient.post('/users/register', user);
  },

  // apiService.js
  async loginUser(credentials) {
    try {
      return await apiClient.post('/auth/login', credentials);
    } catch (error) {
      console.error('登入請求失敗:', error);
      throw error;
    }
  },

  

  // 创建帖子
  async createPost(post) {
    try {
      return await apiClient.post('/posts/create', post);
    } catch (error) {
      console.error('建立貼文失敗:', error);
      throw error;
    }
  },

  // 获取所有帖子
  async getAllPosts() {
    try {
      return await apiClient.get('/posts');
    } catch (error) {
      console.error('獲取所有帖子失敗:', error);
      throw error;
    }
  },

  // 获取帖子详情
  async getPostById(postId) {
    try {
      return await apiClient.get(`/posts/${postId}`);
    } catch (error) {
      console.error('取得貼文失敗:', error);
      throw error;
    }
  },

  // 更新帖子
  async updatePost(postId, updatedPost) {
    try {
      return await apiClient.put(`/posts/${postId}`, updatedPost);
    } catch (error) {
      console.error('更新貼文失敗:', error);
      throw error;
    }
  },

  // 删除帖子
  async deletePost(postId) {
    try {
      return await apiClient.delete(`/posts/${postId}`);
    } catch (error) {
      console.error('刪除貼文失敗:', error);
      throw error;
    }
  },

  // 添加评论
  async addComment(comment) {
    try {
      return await apiClient.post('/comments/add', comment);
    } catch (error) {
      console.error('新增評論失敗:', error);
      throw error;
    }
  },

  // 根据帖子ID获取评论
  async getCommentsByPostId(postId) {
    try {
      return await apiClient.get(`/comments/post/${postId}`);
    } catch (error) {
      console.error('取得評論失敗:', error);
      throw error;
    }
  },
};
