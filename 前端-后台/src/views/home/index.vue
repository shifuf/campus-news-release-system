<template>
  <div>首页</div>
</template>

<script setup>
import { onMounted, nextTick } from 'vue';
import { getUserId } from '../../utils/user';
import { message } from 'ant-design-vue';
import { useRouter } from 'vue-router';

const router = useRouter();

onMounted(() => {
  const userId = getUserId();
  if (!userId) {
    message.error('请先登录');
    nextTick(() => {
      router.push({ path: '/login' });
    });
  }
});
</script>
<style scoped></style>
