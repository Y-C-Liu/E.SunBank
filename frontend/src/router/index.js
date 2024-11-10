import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../components/HomePage.vue';
import UserLogin from '../components/UserLogin.vue';
import UserRegister from '../components/UserRegister.vue';
import CreatePost from '../components/CreatePost.vue';
import PostList from '../components/PostList.vue';
import PostDetails from '../components/PostDetails.vue';
import EditPost from '../components/EditPost.vue';

const routes = [
  { path: '/', component: HomePage },
  { path: '/posts', component: PostList },
  { path: '/create-post', component: CreatePost },
  { path: '/posts/:postId', component: PostDetails, props: true },
  { path: '/login', component: UserLogin },
  { path: '/edit-post/:postId', component: EditPost, props: true },
  { path: '/register', component: UserRegister },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
