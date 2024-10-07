<template>
    <!-- 面包屑 -->
  <div>
    <a-breadcrumb class="bred">
    <a-breadcrumb-item class="item" v-for="item in tabs">{{item.meta.title}}</a-breadcrumb-item>
</a-breadcrumb>
  </div>
</template>

<script  setup>
import { onMounted, ref} from 'vue';
import {  useRouter } from 'vue-router';
//当前路由
const route = useRouter(); 

// 面包屑数据
const tabs = ref([]);

//获取面包屑数据
const getBred = () =>{
    const matched = route.currentRoute.value.matched;
    let mached = matched.filter(item =>item.meta && item.meta.title);
    // console.log(matched);
    
    const first = mached[0];
    if(first && first.path !=='/home'){
        mached = [{path:'/home',meta:{title:'首页'}},...mached];
    }
    tabs.value = mached;
}
onMounted(() => {
    getBred();
    route.afterEach(() => {
        getBred();
    })
})
</script>
<style lang="scss" scoped>
.bred{
    margin-left: 15px;
    .item{
        color: #fff;
    }
}
:deep(.ant-breadcrumb-separator){
    color: #fff;
}
</style>
