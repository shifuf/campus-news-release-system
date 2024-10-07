!<template>
<div>
    <!-- logo -->
    <div class="header">
      <img src="../assets/images/logo.webp" alt="logo" width="132" height="34" />
      <!-- 搜索框 -->
      <div class="header-right">
        <div class="top-search">
          <input class="input-search" type="text" placeholder="请输入需要搜索的新闻" />
          <button @click="search">搜索</button>
        </div>
        <div id="top-login">
          <div   v-if="!userInfo" class="top-login" ><a href="/login">登录</a></div>
          <div v-else class="i-login">
                <ul>
                <li class="i-down"><img :src="userInfo.avatar" alt="">
                    <ul>
                    <li>您好：{{ userInfo.nickname }}</li>
                    <a href="javascript:;"  @click="logout">退出登录</a>
                    </ul>
                </li>
                </ul>
            </div>
        </div>
      </div>
    </div>

  <nav>
    <div class="nav">
      <ul>
        <li><a href="/">首页</a></li>
         <li v-for="(item,index) in list" :key="item.id"><a :href="`list?id=${item.id}`">{{ item.name }}</a></li>
         <li><a href="comment">留言板</a></li>
        <!-- <li><a href="/list.html?type=yuleList&index=2&name=娱乐">娱乐</a></li>
        <li><a href="/list.html?type=kejiList&index=3&name=科技">科技</a></li>
        <li><a href="/list.html?type=tiyuList&index=4&name=体育">体育</a></li>
        <li><a href="/list.html?type=jiaoyuList&index=5&name=教育">教育</a></li>
        <li><a href="/list.html?type=lishiList&index=6&name=历史">历史</a></li>  -->
      </ul>
    </div>
  </nav>
</div>

</template>

<script setup>
import { ref } from 'vue'
import { CategoryList } from '../api/modules/category'
import { onMounted } from 'vue'

import { getUserInfo } from '../utils/user';
import { message } from 'ant-design-vue';
let pageSize = 10
let pageNum = 1
const list = ref()

// 获取用户信息
const userInfo =getUserInfo();
// 退出登录
const logout = () => {
  localStorage.removeItem('userInfo')
  message.success('退出成功')
  setTimeout(() => {
    window.location.href = '/'
  }, 1000)
}


// 搜索
const search = () =>{
    const search = document.querySelector('.input-search').value
    window.location.href = `/search?query=${search}`
}
onMounted(() => {
  CategoryList({
    pageNum: pageNum,
    pageSize: pageSize
  }).then(res => {
    list.value = res.data.rows
    console.log(res)
  })
})
</script>
<style scoped>
@import url("../assets/css/index.css");
@import url("../assets/css/login.css");
/* @import url("../assets/css/main.css"); */
</style>
