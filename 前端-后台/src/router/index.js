import { createRouter, createWebHistory } from "vue-router"; // 导入路由
import Layout from '../layout/index.vue'
import { getUserId } from "../utils/user";
import { message } from 'ant-design-vue';
// 1. 配置路由  lintaibai--进来时候的初始页面
const routes = [
  
  // {
  //   path: "/", // 默认路由 home页面
  //   component: () => import("../views/user/Login.vue"),
  // },
  {
    path: "/", // 默认路由 home页面
    name: "Layout",
    component: Layout,
    redirect: '/home',
    meta:{
      // title: '首页',
    },
    children: [
      {
        path: "/home",
        name: "home",
        component: () => import("../views/home/index.vue"),
        meta: {
          title: '首页',
          icon: '#icondashboard'
        }
      }
    ]

  },
  {
    path: "/login", 
    name: "login",
    component: () => import("../views/user/Login.vue"),
    meta:{
      // title: '首页',
    },
  },
  //用户管理
  {
    path: "/user",
    name: "user",
    component: Layout,
    meta:{
      title: '用户管理',
    },
    children: [
      {
        path: "list",
        name: "user",
        component: () => import("../views/user/List.vue"),
        meta: {
          title: '用户列表',
          icon: '#iconyonghuguanli'
        }
      }
    ]
  },
  {
    path: "/category",
    name: "category",
    component: Layout,
    meta:{
      title: '分类管理',
    },
    children: [
      {
        path: "list",
        name: "category",
        component: () => import("../views/category/index.vue"),
        meta: {
          title: '分类列表',
          icon: '#iconyonghuguanli'
        }
      }
    ]
  },
  //新闻管理
  {
    path: "/article",
    name: "article",
    component: Layout,
    meta:{
      title: '文章管理',
    },
    children: [
      {
        path: "list",
        name: "list",
        component: () => import("../views/article/ArticleList.vue"),
        meta: {
          title: '文章列表',
          icon: '#iconyonghuguanli'
        }
      },
      {
        path: "add",
        name: "add",
        component: () => import("../views/article/ArticleAdd.vue"),
        meta: {
          title: '文章发布',
          icon: '#iconyonghuguanli'
        }
      },
    ]
  },
    //留言管理
    {
      path: "/comment",
      name: "comment",
      component: Layout,
      meta:{
        title: '留言管理',
      },
      children: [
        {
          path: "list",
          name: "comment",
          component: () => import("../views/comment/index.vue"),
          meta: {
            title: '留言列表',
            icon: '#iconyonghuguanli'
          }
        },
      ]
    }
];


// 2.返回一个 router 实列，为函数，配置 history 模式
const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 3.导出路由   去 main.ts 注册 router.ts
export default router