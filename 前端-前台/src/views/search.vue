<template>
      <div class="header">
        <!-- logo -->
        <a href="/"><img src="../assets/images/logo.webp"  alt="logo" width="132" height="34"></a>

        <div class="search">
            <!-- 搜索框 -->
            <input class="input-search" type="text" placeholder="输入关键词进行搜索" :value="query">
            <!-- 搜索按钮 -->
            <div class="button-search" @click="search">搜索</div>
        </div>
    </div>
    
    <!-- 搜索后的内容列表 -->
    <div class="box">
        <ul v-for="item in list">    
            <li>
                <a :href="`details?id=${item.id}`" class="title">{{ item.title }}</a>
                <div class="info-box">
                    <img :src="item.coverimg" alt="">
                    <div>
                        <p>发布时间：{{ item.createtime }}</p>
                        <p>作者：{{item.author}}</p>
                    </div>
                </div>
            </li>
           
        </ul>
        <li v-show="status"><a-empty description="暂无数据"/></li>
    </div>
</template>

<script setup>
import { onMounted,ref } from 'vue';
import { ArticleList } from '../api/modules/article'
//获取浏览器参数的
const url = new URL(window.location.href)
const query = url.searchParams.get('query')
let status = false
let pageSize = 10
let pageNum = 1
let list = ref()

const search = () =>{
    const search = document.querySelector('.input-search').value
    window.location.href = `/search?query=${search}`
}
onMounted(() => {
    ArticleList({
        title: query,
        pageNum: pageNum,
        pageSize: pageSize
    }).then(res => {
        list.value = res.data.rows
        if(res.data.rows.length==0){
            status = true
        }
    })
})
</script>
<style>
@import url("../assets/css/main");
.header {
    width: 1200px;
    /* 宽度 */
    height: 80px;
    /* 高度 */
    margin: 20px auto;
    display: flex;
    /* 内容居中 */
}

/* 搜索 */
.search {
    width: 680px;
    margin-left: 30px;
    position: relative;

}

/* 搜索框 */
.input-search {
    padding-left: 24px;
    width: calc(100% - 24px);
    height: 42px;
    background-color: rgba(239, 239, 239, .6);
    border-radius: 80px;
    border: none;
    -webkit-box-shadow: 0 0 0 0.2px rgba(0, 0, 0, .1);
    box-shadow: 0 0 0 0.2px rgba(0, 0, 0, .1);
    vertical-align: middle;
    font-size: 18px;
    color: #333;
    outline-style: none;
}

/* 搜索按钮 */
.button-search {
    position: absolute;
    right: 0;
    top: 0;
    display: inline-block;
    width: 88px;
    height: 42px;
    line-height: 42px;
    cursor: pointer;
    background-color: #37f;
    border-radius: 36px;
    color: #fff;
    font-size: 16px;
    font-weight: 500;
    text-align: center;
}

/* 列表 */
.box {
    width: 1050px;
    margin: 0 auto;
    margin-top: 20px;
}

ul {
    width: 680px;
}

li a {
    color: #000;
}

li {
    margin-bottom: 50px;
}

/* 文字标题 */
.title {
    font-weight: 500;
    font-size: 20px;
    line-height: 28px;
    margin-bottom: 10px;
}

/* 信息详情 */
.info-box {
    display: flex;
}

.info-box img {
    width: 140px;
    height: 94px;
    margin-right: 15px;
    border-radius: 4px;
}

.info-box div {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    font-size: 14px;
    line-height: 22px;
    text-align: justify;
    color: #5c5c5c;
}

/* 内容为空 */
.null {
    width: 340px;
    margin: 0 auto;
}

.null img {
    margin-bottom: 20px;
}
</style>
