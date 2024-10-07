<template>
  <div>
    <!-- logo -->
    <div class="bg">
      <div class="header">
        <div class="top-left">
          <img src="../assets/images/logo.webp" alt="logo" width="132" height="34" />
            <nav id="nav">
            <ul>
              <li><a href="/">首页</a></li>
              <li v-for="(item,index) in list"  key:item :class="item.id==id?'active':''"><a :href="`list?id=${item.id}`">{{ item.name }}</a></li>
              <li><a href="comment">留言板</a></li>
            </ul>
          </nav>
        </div>

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
    </div>
  </div>
</template>

<script  setup>
import { ref } from 'vue'
import { CategoryList } from '../api/modules/category'
import { getUserInfo } from '../utils/user';
import { onMounted } from 'vue'
let pageSize = 10
let pageNum = 1

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
const list = ref()
//获取浏览器参数的id
const url = new URL(window.location.href)
const id = url.searchParams.get('id')
onMounted(() => {
  CategoryList({
    pageNum: pageNum,
    pageSize: pageSize
  }).then(res => {
    list.value = res.data.rows
    console.log(res)
  })
})
// 搜索
const search = () =>{
    const search = document.querySelector('.input-search').value
    window.location.href = `/search?query=${search}`
}
</script>
<style scoped>
@import url("../assets/css/list");
@import url("../assets/css/index");
@import url("../assets/css/login.css");
</style>
