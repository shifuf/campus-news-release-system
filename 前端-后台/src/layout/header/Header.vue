<template>
  <div class="header-bg">
    <Collapse></Collapse>
    <Bred></Bred>
  </div>
</template>

<script setup>

  import Collapse from './Collapse.vue'
  import Bred from '../menu/Bred.vue'
</script>
<style lang="scss" scoped>
.header-bg{
    color: #fff;
    display: flex;
    align-items: center;
}
</style>
