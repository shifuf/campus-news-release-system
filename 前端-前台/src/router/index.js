import { createRouter, createWebHistory } from "vue-router"; // 导入路由
// 1. 配置路由
const routes= [
  {
    path: "/", // 默认路由 home页面
    component: () => import("../views/index.vue"),
  },
  //登录
  {
    path: "/login", 
    component: () => import("../views/user/login.vue"),
  },
  //注册
    {
    path: "/register", 
    component: () => import("../views/user/register.vue"),
  },
  //新闻列表页
  {
    path: "/list",
    component: () => import("../views/list.vue"),
  },
  //新闻详情页
  {
    path: "/details",
    component: () => import("../views/details.vue"),
  },
  //搜索页
  {
    path: "/search",
    component: () => import("../views/search.vue"),
  },
    //留言页
    {
      path: "/comment",
      component: () => import("../views/comments.vue"),
    }
];
// 2.返回一个 router 实列，为函数，配置 history 模式
const router = createRouter({
  history: createWebHistory(), 
  routes,
});
 
// 3.导出路由 
export default router