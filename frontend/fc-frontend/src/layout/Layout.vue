<!--  布局骨架：左侧菜单 + 右侧内容区 -->
<script setup>
import {ref, onMounted} from 'vue'

// ref 创建响应式变量，页面会自动更新
const menuList = ref([])

// onMounted = 组件挂载到页面后自动执行
onMounted(() => {
  fetchMenus()
})

async function fetchMenus() {
  const response = await fetch('http://localhost:18082/system/menu/tree')
  const result = await response.json()
  if (result.code === 200) {
    menuList.value = result.data   // .value 是 ref 的固定写法
  }
}
</script>

<template>
  <div class="layout">
    <!-- 左侧菜单栏 -->
    <div class="sidebar">
      <h3>这里是菜单</h3>
      <!-- 菜单内容后面填 -->
    </div>

    <!-- 右侧内容区 -->
    <div class="content">
      <router-view/>
    </div>
  </div>
</template>

<style scoped>

</style>