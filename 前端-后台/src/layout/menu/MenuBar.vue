<template>
  <!-- 菜单 -->
  <div>
    <a-menu
      v-model:openKeys="state.openKeys"
      v-model:selectedKeys="state.selectedKeys"
      mode="inline"
      theme="light"
      :inline-collapsed="state.collapsed"
      :items="items"
      @select="handleSelect"
    >
      <!-- 使用 router-link 组件创建导航链接 -->
      <!-- <a-menu-item key="/home">
          <router-link to="/home">首页</router-link>
        </a-menu-item>
        <a-menu-item key="/user" index="user">
          <router-link to="/user">About</router-link>
        </a-menu-item> -->
    </a-menu>
  </div>
</template>
<script setup>
import { reactive, watch, h } from 'vue';
import { useRouter } from 'vue-router';
import {
  BarsOutlined,
  MailOutlined,
  HomeOutlined,
  FormOutlined,
  TeamOutlined,
  MenuOutlined,
  ReadOutlined,
} from '@ant-design/icons-vue';
const route = useRouter(); // 使用 Vue Router
const state = reactive({
  collapsed: false,
  selectedKeys: [],
  openKeys: [],
  preOpenKeys: [],
});
const items = reactive([
  {
    key: 'home',
    name: 'home',
    icon: () => h(HomeOutlined),
    label: '首页',
    title: '首页',
  },
  {
    key: 'user',
    name: 'user',
    icon: () => h(TeamOutlined),
    label: '用户管理',
    title: '用户管理',
  },
  {
    key: 'category',
    name:'category',
    icon: () => h(BarsOutlined),
    label: '分类管理',
    title: '分类管理',
  },
  {
    key: 'article',
    name: 'article',
    icon: () => h(ReadOutlined),
    label: '文章管理',
    title: '文章管理',
    children: [
      {
        key: 'list',
        name: 'list',
        icon: () => h(MenuOutlined),
        label: '文章列表',
        title: '文章列表',
      },
      {
        key: 'add',
        name: 'add',
        icon: () => h(FormOutlined),
        label: '文章发布',
        title: '文章发布',
      },
    ],
  },
  {
    key: 'comment',
    name:'comment',
    icon: () => h(MailOutlined),
    label: '留言管理',
    title: '留言管理',
  },
]);
// watch(
//   () => state.openKeys,
//   (_val, oldVal) => {
//     state.preOpenKeys = oldVal;
//   }
// );
// 处理菜单选择
const handleSelect = ({ key }) => {
  // 根据菜单 key 跳转到对应的路由
  route.push({ name: key });
};
// 当前选择的菜单项
const currentKey = route.currentRoute.value.name;
// 设置当前选择的菜单项
state.selectedKeys = [currentKey];
// 页面加载时展开的菜单项
const serMenuOpen = (result) => {
  const openKeys = [];
  items.forEach((item) => {
    if (item.children) {
      item.children.forEach((child) => {
        if (child.name === result) {
          openKeys.push(item.key);
        }
      });
    }
  });
  return openKeys;
};
state.openKeys = serMenuOpen(currentKey);
// 监听路由变化
route.afterEach((to) => {
  state.selectedKeys = [to.name];
  state.openKeys = serMenuOpen(to.name);
});
</script>
