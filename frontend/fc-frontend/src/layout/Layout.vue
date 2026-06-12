<script setup>
import HeaderNavBar from './components/HeaderNavBar.vue'
import SidebarMenu from './components/SidebarMenu.vue'
import TabsBar from './components/TabsBar.vue'
import BreadcrumbNav from './components/BreadcrumbNav.vue'
import FooterBar from './components/FooterBar.vue'
</script>

<template>
  <el-container class="layout">
    <el-aside class="layout-aside" width="200px">
      <sidebar-menu />
    </el-aside>

    <el-container class="layout-body" direction="vertical">
      <el-header class="layout-header" height="50px">
        <header-nav-bar />
      </el-header>

      <tabs-bar />

      <el-main class="layout-main">
        <breadcrumb-nav />
        <router-view v-slot="{ Component }">
          <transition name="fade-transform" mode="out-in">
            <keep-alive>
              <component :is="Component" />
            </keep-alive>
          </transition>
        </router-view>
      </el-main>

      <el-footer class="layout-footer" height="40px">
        <footer-bar />
      </el-footer>
    </el-container>
  </el-container>
</template>

<style scoped>
.layout {
  height: 100vh;
  overflow: hidden;
}

.layout-aside {
  height: 100vh;
  background: #304156;
}

.layout-body {
  min-width: 0;
  height: 100vh;
}

.layout-header,
.layout-footer {
  padding: 0;
  flex-shrink: 0;
}

.layout-main {
  flex: 1;
  min-height: 0;
  overflow: auto;
  background: #f0f2f5;
}
</style>
