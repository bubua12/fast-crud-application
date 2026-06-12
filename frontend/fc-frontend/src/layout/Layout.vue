<!--  布局骨架：左侧菜单 + 右侧内容区 -->
<script setup>
import HeaderNavBar from './components/HeaderNavBar.vue'
import SidebarMenu from './components/SidebarMenu.vue'
import TabsBar from './components/TabsBar.vue'
import BreadcrumbNav from './components/BreadcrumbNav.vue'
import FooterBar from './components/FooterBar.vue'
</script>

<template>
  <el-container style="height: 100vh">

    <!-- 左侧菜单 -->
    <el-aside width="200px">
      <sidebar-menu/>
    </el-aside>

    <!-- 右侧区域 -->
    <el-container direction="vertical">
      <!-- 顶部导航 -->
      <el-header height="50px">
        <header-nav-bar/>
      </el-header>

      <!-- 标签页 -->
      <tabs-bar/>

      <!-- 内容区 -->
      <el-main style="background: #f0f2f5">
        <breadcrumb-nav/>
        <router-view v-slot="{ Component }">
          <transition name="fade-transform" mode="out-in">
            <keep-alive>
              <component :is="Component"/>
            </keep-alive>
          </transition>
        </router-view>
      </el-main>

      <!-- 底部 -->
      <el-footer height="40px">
        <footer-bar/>
      </el-footer>

    </el-container>

  </el-container>
</template>

<style scoped>
</style>