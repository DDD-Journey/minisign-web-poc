import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import CreateKeyPair from '@/views/CreateKeyPair.vue';
import SignDocument from '@/views/SignDocument.vue';
import VerifyDocument from '@/views/VerifyDocument.vue';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ '../views/AboutView.vue'),
  },
  {
    path: '/create',
    name: 'createKeyPair',
    component: CreateKeyPair,
  },
  {
    path: '/sign',
    name: 'signDocument',
    component: SignDocument,
  },
  {
    path: '/verify',
    name: 'verifyDocument',
    component: VerifyDocument,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
