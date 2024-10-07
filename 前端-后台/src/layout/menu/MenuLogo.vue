<template>
  <div class="logo">
    <img src="../../assets/image/lzhitlogo.jpg" alt="">
    <span v-show="!collapsed" class="title">校园新闻管理</span>
  </div>
</template>

<script setup>

import { ref, reactive, computed, watch } from 'vue'
import { collapseStore } from '../../store/collapse/index'
const store = collapseStore()

const collapsed = computed(() => {
  return store.getCollapse
})

const show = ref(true)

watch(() => collapsed.value, (collapsed) => {
  if (!collapsed) {
    setTimeout(() => {
      show.value = !collapsed
    }, 250)
  } else {
    show.value = !collapsed
  }
})
</script>
<style lang="scss" scoped>
.logo {
  background-color: #fff;
  height: 50px;
  border: none;
  line-height: 50px;
  display: flex;
  align-items: center;
  padding-left: 18px;

  img {
    width: 43px;
    height: 43px;
    margin-right: 12px;
  }

  .transition-box {
    height: 50px;
    background-color: #fff;
    text-align: center;
    color: #fff;
    box-sizing: border-box;
    line-height: 50px;
    font-size: 20px;
    vertical-align: middle;
  }
}
</style>
