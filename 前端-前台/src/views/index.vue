<template>
  <!-- 头部 -->
  <Header></Header>
<!-- 首页主体 -->
   <!-- 要闻等 -->
   <div class="box">
    <div class="box-content" v-for="(item,index) in list" :key="item.id">
        <!-- 分类标题 -->
        <div class="title">
          <a href="">{{ item.name }}</a>
          <a :href="`list?id=${item.id}`">更多>></a>
        </div>
        <!-- 文章 -->
         
        <div class="article">
          <div  v-for="items in item.article" :key="items.id">
          <div class="article-ban" v-if="items.stop == 1">
            <img :src="items.coverimg" alt="" />
            <div class="ban-text">
              <!-- 推荐标题 -->
              <a :href="`details?id=${items.id}`">{{ items.title }}</a>
              <!-- 来源 -->
              <p><a href="">{{ items.author }}</a></p>
            </div>
          </div>
          <ul >
            <li v-if="items.stop == 0"><a :href="`details?id=${items.id}`">{{ items.title }}</a></li>
          </ul>
        </div>
        </div>
        
      </div>
  </div>

</template>

<script  setup>
import Header from '../components/Header.vue'
import { ListArticle } from '../api/modules/category'
import { onMounted,ref} from 'vue'
let pageSize = 10
let pageNum = 1

//获取文章列表
const list = ref([])


onMounted(() => {
  //获取分类列表
  ListArticle({
    pageSize:pageSize,
    pageNum:pageNum
  }).then(res => {
    list.value = res.data.rows
  })

})

</script>
<style lang="scss">
@import url("../assets/css/index");
</style>
