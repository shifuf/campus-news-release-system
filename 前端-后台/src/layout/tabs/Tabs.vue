<template>
    <a-tabs v-model:activeKey="activeKey" hide-add type="editable-card" @edit="onEdit" @tabClick="tabClick">
    <a-tab-pane v-for="pane in panes" :key="pane.path" :tab="pane.title" ></a-tab-pane>
  </a-tabs>
</template>

<script setup>
import { computed, onMounted, ref, watch} from 'vue';
import {  useRoute } from 'vue-router';
import {  useRouter } from 'vue-router';
import { tabsStore } from '../../store/tabs';
//当前路由
const route = useRoute(); 
const router = useRouter();

const store = tabsStore();
//选项卡数据
const panes = computed(() =>{
   return store.getTabs;
});

//当前激活的key
const activeKey = ref();
//设置当前激活选项
const setActiveKey = () =>{
    activeKey.value = route.path;
}
const remove = (targetKey) =>{
    if (targetKey === '/home') {
        return;
    }
   let lastIndex = 0;
   store.tabsList.forEach((pane, i) => {
       if (pane.path === targetKey) {
           lastIndex = i - 1;
       }
   });
   store.tabsList = store.tabsList.filter(pane => pane.path !== targetKey);
   if (lastIndex >= 0) {
       activeKey.value = store.tabsList[lastIndex].path;
   } else {
       activeKey.value = store.tabsList[0].path;
   }
   //跳转到新的路由
    router.push({path:activeKey.value});
}
//监听路由
watch(()=>route.path,()=>{
    //设置当前激活选项，当前路由的path
    setActiveKey();
    //添加tab
    addTab();
})
//刷新后保留当前tab
const beforeRefresh = () =>{
    if(route.path !='/login'){
        window.addEventListener('beforeunload',()=>{
            sessionStorage.setItem('tabs',JSON.stringify(store.tabsList));
        })
        let tabSesson = sessionStorage.getItem('tabs');
        if(tabSesson){
            let oldTabs = JSON.parse(tabSesson);
            if(oldTabs.length>0){
                store.tabsList = oldTabs;
            }
        }
    }

}
    
//删除tab
const onEdit = (targetKey) => {
    remove(targetKey);
}

//监听当前路由，把当前路由添加到tabs中
const addTab = () =>{
    const {path,meta} = route;
    const tab ={
        path:path,
        title:meta.title
    }
    //添加tab
    store.addTab(tab);

    
}

onMounted(()=>{
    //刷新后保留当前tab
    beforeRefresh();
    //设置当前激活选项，当前路由的path
    setActiveKey();
    //添加tab
    addTab();
})
//tab点击
const tabClick = (kye) =>{
    router.push(kye);
}
// onMounted(() => {
//     activeKey.value = route.currentRoute.value.path;
//     store.addTab({
//         title: route.currentRoute.value.meta.title,
//     });
// });
</script>
<style scoped></style>
