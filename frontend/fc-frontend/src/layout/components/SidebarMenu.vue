<!-- el-menu 侧边栏 -->
<script setup>
import {ref, onMounted} from 'vue'

const menuList = ref([])

onMounted(() => {
  fetchMenus()
})

async function fetchMenus() {
  const response = await fetch('http://localhost:18082/system/menu/tree')
  const result = await response.json()
  if (result.code === 200) {
    menuList.value = result.data
  }
}

</script>

<template>

  <!-- 原来 Layout.vue 里的菜单逻辑挪过来，换成 el-menu -->
  <el-scrollbar>
    <el-menu
        :default-active="$route.path"
        router
        :collapse="false"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409eff"
    >

      <template v-for="menu in menuList" :key="menu.id">
        <!-- 有子菜单 -->
        <el-sub-menu v-if="menu.children && menu.children.length" :index="menu.path">
          <template #title>{{ menu.name }}</template>
          <el-menu-item
              v-for="child in menu.children"
              :key="child.id"
              :index="child.path"
          >
            {{ child.name }}
          </el-menu-item>
        </el-sub-menu>

        <!-- 没有子菜单 -->
        <el-menu-item v-else :index="menu.path">
          {{ menu.name }}
        </el-menu-item>

      </template>

    </el-menu>
  </el-scrollbar>

</template>

<style scoped>
.el-menu {
  border-right: none;
}
</style>