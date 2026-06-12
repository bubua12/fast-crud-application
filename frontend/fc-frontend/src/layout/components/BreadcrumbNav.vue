<!-- 面包屑 fixme -->
<script setup>
import {computed} from 'vue'
import {useRoute} from 'vue-router'

const route = useRoute()

const breadcrumbs = computed(() => {
  return route.matched
      .filter(r => r.path !== '/' && r.path !== '')
      .map(r => ({path: r.path, name: r.meta?.title || r.name || r.path}))
})
</script>

<template>
  <el-breadcrumb v-if="breadcrumbs.length" separator="/" style="margin-bottom: 16px">
    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item v-for="item in breadcrumbs" :key="item.path">
      {{ item.name }}
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>

<style scoped>

</style>