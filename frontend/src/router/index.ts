import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import AboutView from '../views/AboutView.vue';
import CreateKeyPairView from '@/views/CreateKeyPairView.vue';
import SignDocumentView from '@/views/SignDocumentView.vue';
import VerifyDocumentView from '@/views/VerifyDocumentView.vue';
import CommonLayout from '@/layout/Common.vue';
import UseCaseLayout from '@/layout/UseCase.vue';

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    component: CommonLayout,
    children: [{ path: '', name: 'home', component: HomeView }]
  },
  {
    path: '/about',
    component: CommonLayout,
    children: [{ path: '', name: 'about', component: AboutView }]
  },
  {
    path: '/create',
    component: UseCaseLayout,
    children: [{ path: '', name: 'createKeyPair', component: CreateKeyPairView }]
  },
  {
    path: '/sign',
    component: UseCaseLayout,
    children: [{ path: '', name: 'signDocument', component: SignDocumentView }]
  },
  {
    path: '/verify',
    component: UseCaseLayout,
    children: [{ path: '', name: 'verifyDocument', component: VerifyDocumentView }]
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
