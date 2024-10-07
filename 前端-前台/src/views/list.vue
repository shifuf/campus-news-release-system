<template>
    <div class="body">
  <HeaderList>
  </HeaderList>
<div class="carousel ">
  <!-- <el-carousel indicator-position="outside">
    <el-carousel-item >
      <a-image src="http://localhost:8080/images/1727010374384.jpg" />
    </el-carousel-item>
  </el-carousel> -->
  <a-carousel arrows autoplay>
    <template #prevArrow>
      <div class="custom-slick-arrow" style="left: 10px; z-index: 1">
        <left-circle-outlined />
      </div>
    </template>
    <template #nextArrow>
      <div class="custom-slick-arrow" style="right: 10px">
        <right-circle-outlined />
      </div>
    </template>
    <div class="img" v-for="item in listImg"><img :src="item" /></div>
  </a-carousel>
</div>

  <!-- 新闻列表 -->
  <div class="box">
    <div class="box-list">
      <div class="box-content" v-for="(item,index) in listData">
        <ul>
          <li>
            <a :href="`details?id=${item.id}`"><img :src="item.coverimg" /></a>
            <div class="detail">
              <h3><a :href="`details?id=${item.id}`">{{ item.title }}</a></h3>
              <div class="binfo">
                <p>{{item.author}}</p>
              </div>
            </div>
          </li>
        </ul>
      </div>

    </div>
    <!-- 热搜榜 -->
    <div class="sidebar">
      <div class="rs"><a href="">热搜榜</a></div>
      <div class="rankNews">
        <ul>
          <li><i>1</i><a href="">乱港分子周庭弃保拒绝返港报到</a></li>
          <li><i>2</i><a href="">乱港分子周庭弃保拒绝返港报到</a></li>
          <li><i>3</i><a href="">弘扬宪法精神 培育法治文化</a></li>
          <li><i>4</i><a href="">被判无期死缓的官员 再被官媒点名</a></li>
          <li><i>5</i><a href="">女子经历断崖式衰老：看上去像70岁</a></li>
          <li><i>6</i><a href="">云南部分乡镇倡议不搞“杀猪饭”</a></li>
          <li><i>7</i><a href="">美共和党先罢免议长又驱逐议员</a></li>
          <li><i>8</i><a href="">河南一马路盲道疑是贴纸一撕就掉</a></li>
          <li><i>9</i><a href="">时隔一年半 比特币又升破4万美元</a></li>
        </ul>
      </div>
    </div>
  </div>
  <!-- <div>测试跳转过来的参数</div>
  <button @click="testa">
    点击发送请求
  </button>     -->
  </div>

</template>

<script  setup>
import { LeftCircleOutlined, RightCircleOutlined } from '@ant-design/icons-vue';
import HeaderList from '../components/HeaderList.vue'
import { onMounted, ref } from 'vue';
import { test } from '../api/modules/user'
import { CategoryLArtList } from '../api/modules/category'

// 分页参数
let pageSize = 10
let pageNum = 1
//获取浏览器参数的id
const url = new URL(window.location.href)
const id = url.searchParams.get('id')
// 推荐轮播图
let listImg = ref([]);
// 新闻列表
let listData = ref()

onMounted(() => {
  testa()
})
function testa  ()  {
  // test({
  //     pageNum:pageNum,
  //     pageSize:pageSize

  // }).then(res => {
  //   msg.value = res.msg
  //   list.value = res.data.rows
  //   console.log(res.data.msg)
  // })
  CategoryLArtList({
    id:id,
    pageNum:pageNum,
    pageSize:pageSize
  }).then(res =>{
    let data = res.data.rows
    data.forEach(e => {
      if(e.stop == 1){
        listImg.value.push(e.coverimg)
      }
    });
    
    listData.value = data
    console.log(res.data);
    
  })
}
</script>
<style lang="scss" scoped>
@import url("../assets/css/list");
// @import url("../assets/css/index");
//轮播图
/* For demo */
:deep(.slick-slide) {
  text-align: center;
  height: 560px;
  line-height: 560px;
  overflow: hidden;
}

:deep(.slick-arrow.custom-slick-arrow) {
  width: 25px;
  height: 25px;
  font-size: 25px;
  color: #fff;
  background-color: rgba(31, 45, 61, 0.11);
  transition: ease all 0.3s;
  opacity: 0.3;
  z-index: 1;
}
:deep(.slick-arrow.custom-slick-arrow:before) {
  display: none;
}
:deep(.slick-arrow.custom-slick-arrow:hover) {
  color: #fff;
  opacity: 0.5;
}

:deep(.slick-slide h3) {
  color: #fff;
}


.body{
  background: #f1f2f5;
  height: 100vh;
  // width: 1400px; /* 宽度 */
    margin: 0 auto; /* 内容居中 */
  .carousel{
    width: 1100px;
    margin: 0 auto;
    // position: relative;
    overflow: hidden;
    border-radius: 10px;
    img{
  width: 100%;
  height: 100%;
  object-fit: cover;
    }
  }
}

</style>
