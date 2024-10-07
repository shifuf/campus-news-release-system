<template>
  <!--  -->
      <a-layout class="layout">
      <a-layout-sider class="asside" v-model:collapsed="collapsed " :trigger="null"  >
        <MenuLogo></MenuLogo>
        <menu-bar></menu-bar>
        
      </a-layout-sider>
      <a-layout>
        <a-layout-header class="header">
          <Header></Header>
        </a-layout-header>
        <a-layout-content class="main">
    
          <Tabs class="tabs"></Tabs>
          <div class="main-container">
            <router-view></router-view>
          </div>
        </a-layout-content>
      </a-layout>
    </a-layout>
</template>

<script setup>
import { computed } from 'vue'
import MenuBar from '../layout/menu/MenuBar.vue'
import MenuLogo from '../layout/menu/MenuLogo.vue'
import Header from '../layout/header/Header.vue'
import {collapseStore} from '../store/collapse/index'
import Tabs from './tabs/Tabs.vue'
const store = collapseStore()

const collapsed = computed(()=>{
  return store.getCollapse
})
</script>
<style lang="scss" scoped>
.layout{
    height: 100%;
    .asside{
        background-color: #fff;
        width: 200px;
    }
    .header{
        background-color: #2f54eb;
        height: 50px;
        display: flex;
        padding: 0 20px;
        align-items: center;
        justify-content: space-between;

    }
    .main{
        .tabs{
          height:50px;
          width: 100%;
          background-color: #fff;
          margin-bottom: 3px;
        }
        .main-container{
          background-color: #f0f2f5;
          padding: 20px;
        }
    }
}
</style>
