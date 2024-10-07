<template>
      <!-- 头部 -->
  <HeaderList></HeaderList>
  <!-- 文章详情 -->
  <div class="box">
    <span v-for="item in list">
    <!-- 标题 -->
    <!-- <h1>学习笔记 | 习主席为何特别关注这一国际论坛？</h1> -->
    <h1>{{ item.title }}</h1>
    <!-- 来源 -->
    <div>
      <p>{{item.updatetime}} 作者 {{item.author}}</p>
    </div>
    <MdPreview :editorId="idd" :modelValue="item.content" />
    <MdCatalog :editorId="idd" :scrollElement="scrollElement" />
</span>
    <!-- 文章内容 -->
    <!-- <div class="content">
      <div class="strong">
        <span class="">天天学习 </span>
        12月4日，习近平主席在致“2023从都国际论坛”的贺信中强调，面对前所未有的世界之变、时代之变和历史之变，人类社会必须团结起来，坚持互学互鉴、开放包容、合作共赢，弘扬全人类共同价值，共同建设更加美好的世界。
        <img src="./assets/image/641.webp.jpg" alt="" />
      </div>
    </div> -->
  </div>
</template>

<script setup>
import HeaderList from '../components/Header.vue'
import { onMounted, reactive, ref } from 'vue';
import { ArticleDetail } from '../api/modules/article'
import { MdPreview, MdCatalog } from 'md-editor-v3';
// preview.css相比style.css少了编辑器那部分样式
import 'md-editor-v3/lib/preview.css';

const idd = 'preview-only';
const scrollElement = document.documentElement;
//获取浏览器参数的id
const url = new URL(window.location.href)
const id = url.searchParams.get('id')
let list = reactive({})
const articleList = () => {
    ArticleDetail({
        id: id
    }).then(res => {
        list.value = res.data
        console.log(res.data.title);
        
    })
}
// 文章详情
onMounted(() => {
    articleList()
})
</script>
<style scoped>
@import url("../assets/css/details");
</style>
