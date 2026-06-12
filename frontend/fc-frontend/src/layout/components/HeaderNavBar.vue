<!-- 顶部导航栏 -->
<script setup>
import {useRouter} from "vue-router";

const router = useRouter()

function logout() {
  // 清除登录状态（后续有登录功能时补充）
  router.push('/login')
}
</script>

<template>
  <div class="header">
    <div class="left">
      <span class="logo">🏗️</span>
      <span class="title">Fast CRUD</span>
    </div>
    <div class="right">
      <span class="user">👤 admin</span>
      <el-button text @click="logout">退出</el-button>
    </div>
  </div>
</template>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  padding: 0 20px;
  border-bottom: 1px solid #e4e7ed;
  background: #fff;
}

.left {
  display: flex;
  align-items: center;
  gap: 8px;
}

.logo {
  font-size: 20px;
}

.title {
  font-size: 18px;
  font-weight: bold;
  color: #303133;
}

.right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user {
  color: #606266;
}
</style>