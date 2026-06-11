<!--  布局骨架：左侧菜单 + 右侧内容区 -->
<script setup>
import {ref, onMounted} from 'vue'
import {loadView} from '@/router/dynamicLoad.js';
import {useRouter} from "vue-router";

const router = useRouter();

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
    // addDynamicRoutes 调用可以删掉，只保留获取菜单数据用于渲染侧边栏
  }
}

</script>

<template>
  <div class="layout">
    <!-- 左侧菜单栏 -->
    <div class="sidebar">
      <h3>这里是菜单</h3>
      <!-- 菜单内容后面填 -->

      <!-- v-for 遍历数组，:key 是必须的 -->
      <div v-for="menu in menuList" :key="menu.id">
        <!-- 有子菜单的：显示名称 + 子菜单 -->
        <div v-if="menu.children && menu.children.length > 0">
          <div class="menu-title">{{ menu.name }}</div>
          <div v-for="child in menu.children" :key="child.id">
            <router-link :to="child.path">{{ child.name }}</router-link>
          </div>
        </div>

        <!-- 没有子菜单的：直接显示链接 -->
        <router-link v-else :to="menu.path">{{ menu.name }}</router-link>
      </div>
    </div>

    <!-- 右侧内容区 -->
    <div class="content">
      <router-view/>
    </div>
  </div>
</template>

<style scoped>
/* scoped 表示样式只对当前组件生效 */
.layout {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 200px;
  background: #304156;
  color: #bfcbd9;
  padding: 10px;
}

.content {
  flex: 1;
  padding: 20px;
  background: #f0f2f5;
}

.menu-title {
  padding: 8px 12px;
  font-weight: bold;
}

a {
  display: block;
  padding: 6px 12px 6px 24px;
  color: #bfcbd9;
  text-decoration: none;
}

a:hover, a.router-link-active {
  background: rgba(255, 255, 255, 0.15);
  color: #fff;
}
</style>