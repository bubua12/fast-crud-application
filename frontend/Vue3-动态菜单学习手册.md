# Vue 3 动态菜单 Demo —— 学习手册

> 本手册带你从零搭建一个 Vue 3 前端项目，实现从后端获取菜单数据、动态渲染侧边栏菜单、点击菜单跳转到对应页面。
> 每一步都会讲解涉及的 Vue 3 知识点，边做边学。

---

## 目录

- [第一章：环境准备与项目初始化](#第一章环境准备与项目初始化)
- [第二章：项目结构与入口文件](#第二章项目结构与入口文件)
- [第三章：Vue 3 基础 —— 单文件组件 SFC](#第三章vue-3-基础--单文件组件-sfc)
- [第四章：响应式数据 —— ref 与 reactive](#第四章响应式数据--ref-与-reactive)
- [第五章：模板语法](#第五章模板语法)
- [第六章：生命周期 —— onMounted](#第六章生命周期--onmounted)
- [第七章：发送请求获取后端菜单数据](#第七章发送请求获取后端菜单数据)
- [第八章：Vue Router 路由](#第八章vue-router-路由)
- [第九章：递归组件 —— 渲染无限层级菜单](#第九章递归组件--渲染无限层级菜单)
- [第十章：完整布局 —— 侧边栏 + 内容区](#第十章完整布局--侧边栏--内容区)
- [第十一章：常用 Vue 3 知识点速查](#第十一章常用-vue-3-知识点速查)
- [第十二章：三种路由方案对比](#第十二章三种路由方案对比)
- [附录：常见问题](#附录常见问题)

---

## 第一章：环境准备与项目初始化

### 1.1 确认环境

确保已安装 Node.js（18+），终端运行：

```bash
node -v
npm -v
```

### 1.2 用 Vite 创建项目

```bash
cd D:/workspaces/IdeaProjects/fast-crud-application/frontend
npm create vite@latest fc-frontend -- --template vue
```

> 💡 **知识点：Vite**
> Vite 是下一代前端构建工具，启动速度极快。`--template vue` 表示使用 Vue 模板。
> 创建后会生成一个标准的 Vue 3 项目骨架。

### 1.3 安装依赖

```bash
npm install
```

### 1.4 安装 Vue Router

```bash
npm install vue-router@4
```

> 💡 **知识点：Vue Router**
> Vue Router 是 Vue.js 官方的路由管理器，用于实现单页面应用（SPA）的页面跳转。
> Vue 3 对应 Vue Router 4.x 版本。

### 1.5 启动开发服务器

```bash
npm run dev
```

浏览器打开 `http://localhost:5173`，看到 Vue 欢迎页说明项目创建成功。

---

## 第二章：项目结构与入口文件

### 2.1 认识项目结构

```
frontend/
├── index.html              ← 入口 HTML（Vite 特有，放在根目录）
├── package.json            ← 项目配置和依赖
├── vite.config.js          ← Vite 构建配置
├── public/                 ← 静态资源（不会被编译）
│   └── vite.svg
└── src/                    ← 源代码目录（你写的所有代码都在这里）
    ├── main.js             ← 应用入口，创建 Vue 实例
    ├── App.vue             ← 根组件
    ├── assets/             ← 静态资源（会被编译处理）
    │   └── ...
    └── components/         ← 组件目录
        └── HelloWorld.vue
```

### 2.2 入口文件 main.js

```js
// src/main.js
import { createApp } from 'vue'   // 从 vue 中导入创建应用的函数
import App from './App.vue'        // 导入根组件

createApp(App)  // 创建应用实例，传入根组件
    .mount('#app')  // 挂载到 index.html 中 id="app" 的 DOM 元素上
```

> 💡 **知识点：应用实例**
> `createApp()` 创建一个 Vue 应用实例。`.mount('#app')` 把它挂载到页面上。
> 所有的 Vue 组件都是从这个根组件开始，形成一棵组件树。

### 2.3 index.html

```html
<!-- index.html -->
<!DOCTYPE html>
<html lang="en">
<body>
  <div id="app"></div>     <!-- Vue 会接管这个 div -->
  <script type="module" src="/src/main.js"></script>
</body>
</html>
```

> 💡 **知识点：SPA（单页面应用）**
> 整个应用只有一个 HTML 页面。Vue 接管 `#app` 后，所有内容都通过 JavaScript 动态渲染。
> 页面切换不会刷新浏览器，而是由 JavaScript 动态替换组件。

---

## 第三章：Vue 3 基础 —— 单文件组件 SFC

### 3.1 什么是 SFC

Vue 的组件以 `.vue` 文件存在，称为**单文件组件（Single File Component）**。
一个 `.vue` 文件包含三部分：

```vue
<!-- MyComponent.vue -->
<template>
  <!-- HTML 模板：定义组件的结构（必须有） -->
  <div>
    <h1>{{ title }}</h1>
  </div>
</template>

<script setup>
// JavaScript 逻辑：定义数据和行为
import { ref } from 'vue'
const title = ref('Hello Vue 3!')
</script>

<style scoped>
/* CSS 样式：scoped 表示只对当前组件生效 */
h1 {
  color: #42b883;
}
</style>
```

> 💡 **知识点：`<script setup>`**
> Vue 3.2 引入的语法糖。写在 `<script setup>` 中的代码会自动执行，
> 里面的变量和函数会自动暴露给模板使用，不需要手动 return。
> 这是 Vue 3 推荐的写法，比传统的 `export default` 更简洁。

### 3.2 组件的组合关系

```
App.vue                 ← 根组件
├── Layout.vue          ← 布局组件（侧边栏 + 内容区）
│   ├── SideMenu.vue    ← 菜单组件
│   └── <router-view>   ← 路由出口（显示当前页面）
└── pages/
    ├── Home.vue        ← 首页
    ├── UserManage.vue  ← 用户管理
    ├── RoleManage.vue  ← 角色管理
    └── About.vue       ← 关于页面
```

---

## 第四章：响应式数据 —— ref 与 reactive

### 4.1 ref —— 包装单个值

```vue
<script setup>
import { ref } from 'vue'

// ref() 创建一个响应式引用
const count = ref(0)        // 包装基本类型
const name = ref('Vue')     // 包装字符串
const menuList = ref([])    // 也可以包装对象/数组

// 在 JS 中访问/修改值需要 .value
count.value++
console.log(count.value)  // 1

// 在模板中直接用，不需要 .value（Vue 会自动解包）
</script>

<template>
  <!-- 模板中直接用 count，不需要 count.value -->
  <p>计数：{{ count }}</p>
  <button @click="count++">+1</button>
</template>
```

> 💡 **知识点：为什么需要 ref？**
> JavaScript 中基本类型（number、string）赋值是拷贝，不是引用。
> `ref()` 把值包装在一个对象的 `.value` 属性中，这样 Vue 就能追踪到变化并自动更新页面。
> 记住：**JS 中用 `.value`，模板中直接用变量名**。

### 4.2 reactive —— 包装对象

```vue
<script setup>
import { reactive } from 'vue'

// reactive() 创建响应式对象，不需要 .value
const user = reactive({
  name: '张三',
  age: 18
})

// 直接修改属性
user.name = '李四'   // ✅ 响应式，页面会更新
</script>

<template>
  <p>{{ user.name }}, {{ user.age }}岁</p>
</template>
```

> 💡 **知识点：ref vs reactive 怎么选？**
> - **`ref`**：推荐作为默认选择。可以包装任何类型，模板中使用更自然。
> - **`reactive`**：只适合包装对象。不能替换整个对象（会丢失响应性）。
> - **建议**：日常开发中优先用 `ref`，简单好记。

### 4.3 computed —— 计算属性

```vue
<script setup>
import { ref, computed } from 'vue'

const firstName = ref('张')
const lastName = ref('三')

// computed 会缓存结果，只在依赖变化时重新计算
const fullName = computed(() => firstName.value + lastName.value)
</script>

<template>
  <p>全名：{{ fullName }}</p>
</template>
```

> 💡 **知识点：computed vs methods**
> - `computed` 有缓存，依赖不变就不重新计算
> - methods 每次渲染都会重新执行
> - 需要根据其他值**派生**出新值时用 `computed`

---

## 第五章：模板语法

### 5.1 文本插值与属性绑定

```vue
<template>
  <!-- 文本插值：双花括号 -->
  <h1>{{ title }}</h1>

  <!-- 属性绑定：v-bind（缩写 :） -->
  <a :href="url">链接</a>
  <img :src="imageUrl" />

  <!-- 动态 CSS 类 -->
  <div :class="{ active: isActive }">动态 class</div>

  <!-- 动态 style -->
  <div :style="{ color: textColor }">动态 style</div>
</template>
```

### 5.2 条件渲染

```vue
<template>
  <!-- v-if / v-else：决定元素是否渲染（不存在于 DOM 中） -->
  <p v-if="isLoggedIn">欢迎回来！</p>
  <p v-else>请先登录</p>

  <!-- v-show：通过 display 控制显隐（始终在 DOM 中） -->
  <p v-show="isVisible">我可以被显示/隐藏</p>
</template>
```

> 💡 **知识点：v-if vs v-show**
> - `v-if`：条件为 false 时，元素**完全不渲染**（从 DOM 中移除）。适合切换频率低的场景。
> - `v-show`：始终渲染，通过 CSS `display:none` 隐藏。适合频繁切换的场景。

### 5.3 列表渲染

```vue
<script setup>
import { ref } from 'vue'

const fruits = ref([
  { id: 1, name: '苹果' },
  { id: 2, name: '香蕉' },
  { id: 3, name: '橙子' }
])
</script>

<template>
  <!-- v-for 遍历数组，:key 是必须的，用于 Vue 识别每个元素 -->
  <ul>
    <li v-for="item in fruits" :key="item.id">
      {{ item.name }}
    </li>
  </ul>
</template>
```

> 💡 **知识点：为什么 `:key` 很重要？**
> Vue 用 key 来追踪每个节点的身份。当数据变化时，Vue 通过 key 判断哪些元素需要
> 重新渲染、移动或删除。不写 key 会导致性能问题和潜在 bug。
> **永远用唯一标识（如 id）作为 key，不要用 index。**

### 5.4 事件处理

```vue
<script setup>
import { ref } from 'vue'
const count = ref(0)

function increment() {
  count.value++
}

function handleClick(msg, event) {
  console.log(msg, event)
}
</script>

<template>
  <!-- @click 是 v-on:click 的缩写 -->
  <button @click="increment">+1</button>

  <!-- 传参 -->
  <button @click="handleClick('hello', $event)">点击</button>
</template>
```

---

## 第六章：生命周期 —— onMounted

```vue
<script setup>
import { ref, onMounted } from 'vue'

const data = ref(null)

// 组件挂载到 DOM 后执行（此时可以操作 DOM、发起网络请求）
onMounted(() => {
  console.log('组件已挂载！')
  // 通常在这里调用后端接口获取数据
  fetchData()
})

async function fetchData() {
  // ...
}
</script>
```

> 💡 **知识点：Vue 3 生命周期钩子**
>
> | 钩子 | 时机 | 常见用途 |
> |---|---|---|
> | `onMounted` | 组件挂载后 | 发请求、操作 DOM |
> | `onUpdated` | 数据变化导致重新渲染后 | 依赖 DOM 更新的操作 |
> | `onUnmounted` | 组件卸载后 | 清理定时器、取消订阅 |
>
> 本项目中，我们在 `onMounted` 里调用后端接口获取菜单数据。

---

## 第七章：发送请求获取后端菜单数据

### 7.1 使用 fetch 调用后端接口

```vue
<script setup>
import { ref, onMounted } from 'vue'

const menuList = ref([])     // 存放菜单数据
const loading = ref(false)   // 加载状态
const error = ref(null)      // 错误信息

onMounted(() => {
  fetchMenus()
})

async function fetchMenus() {
  loading.value = true
  try {
    // 调用后端接口
    const response = await fetch('http://localhost:18082/system/menu/tree')
    const result = await response.json()

    // 判断后端返回的业务状态码
    if (result.code === 200) {
      menuList.value = result.data    // 把菜单数据存到响应式变量中
    } else {
      error.value = result.message
    }
  } catch (e) {
    error.value = '请求失败：' + e.message
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div v-if="loading">加载中...</div>
  <div v-else-if="error">{{ error }}</div>
  <ul v-else>
    <li v-for="menu in menuList" :key="menu.id">{{ menu.name }}</li>
  </ul>
</template>
```

> 💡 **知识点：async/await**
> `async function` 声明一个异步函数，`await` 等待一个 Promise 完成。
> 配合 `try/catch` 处理成功和失败，比回调函数和 `.then()` 更易读。

### 7.2 封装请求工具（可选，但推荐）

在实际项目中，通常会封装一个请求工具，统一处理基础 URL 和错误：

```js
// src/utils/request.js
const BASE_URL = 'http://localhost:18082'

/**
 * 封装 fetch，统一处理基础 URL 和 JSON 解析
 */
export async function get(url) {
  const response = await fetch(BASE_URL + url)
  return response.json()
}
```

使用时简化为：

```js
import { get } from '@/utils/request'

const result = await get('/system/menu/tree')
if (result.code === 200) {
  menuList.value = result.data
}
```

> 💡 **知识点：`@/` 路径别名**
> Vite 默认把 `@/` 配置为 `src/` 目录的别名，方便引用模块。
> 例如 `import { get } from '@/utils/request'` 等价于 `import { get } from '../utils/request'`

---

## 第八章：Vue Router 路由

### 8.1 什么是路由

在单页面应用（SPA）中，URL 变化时不会刷新页面，而是由 JavaScript 动态切换显示的组件。
Vue Router 就是做这件事的：**URL 变了 → 对应的组件显示出来**。

### 8.2 配置路由

创建路由配置文件：

```js
// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'

// 导入页面组件
import Home from '@/views/Home.vue'
import UserManage from '@/views/UserManage.vue'
import RoleManage from '@/views/RoleManage.vue'
import MenuManage from '@/views/MenuManage.vue'
import About from '@/views/About.vue'

// 定义路由规则
const routes = [
  { path: '/',        redirect: '/home' },        // 根路径重定向到首页
  { path: '/home',          component: Home },
  { path: '/system/user',   component: UserManage },
  { path: '/system/role',   component: RoleManage },
  { path: '/system/menu',   component: MenuManage },
  { path: '/about',         component: About },
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),   // 使用 HTML5 history 模式（URL 没有 # 号）
  routes
})

export default router
```

> 💡 **知识点：History 模式 vs Hash 模式**
> - **Hash 模式**（默认）：URL 带 `#`，如 `http://localhost:5173/#/home`
> - **History 模式**：URL 干净，如 `http://localhost:5173/home`
> - 开发阶段两者效果一样，生产环境 History 模式需要服务器配置支持。

### 8.3 注册路由

在 `main.js` 中注册路由插件：

```js
// src/main.js
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'     // 导入路由配置

createApp(App)
    .use(router)                  // 注册路由插件
    .mount('#app')
```

> 💡 **知识点：插件系统 `.use()`**
> Vue 3 通过 `.use()` 注册插件。路由、状态管理等都是插件。
> `.use(router)` 让所有组件都能使用 `<router-link>` 和 `<router-view>`。

### 8.4 使用路由

```vue
<template>
  <!-- router-link：导航链接，点击后 URL 变化但不刷新页面 -->
  <router-link to="/home">首页</router-link>
  <router-link to="/system/user">用户管理</router-link>

  <!-- router-view：路由出口，当前 URL 匹配的组件会渲染在这里 -->
  <router-view />
</template>
```

> 💡 **知识点：`<router-link>` vs `<a>`**
> `<router-link>` 是 Vue Router 提供的组件，点击时**不会刷新页面**，只是改变 URL 并渲染对应组件。
> 普通的 `<a>` 标签会触发页面刷新，失去 SPA 的优势。

---

## 第九章：递归组件 —— 渲染无限层级菜单

### 9.1 什么是递归组件

后端返回的菜单是树形结构（有 children 嵌套），前端需要**递归**渲染每一层。
Vue 组件可以调用自身，这就是**递归组件**。

### 9.2 实现递归菜单组件

```vue
<!-- src/components/MenuItem.vue -->
<template>
  <!-- 遍历菜单列表 -->
  <template v-for="item in menus" :key="item.id">
    <!-- 有子菜单：显示可展开的分组 -->
    <div v-if="item.children && item.children.length > 0" class="menu-group">
      <div class="menu-title" @click="toggle(item.id)">
        <span>{{ item.name }}</span>
        <span class="arrow">{{ expanded[item.id] ? '▼' : '▶' }}</span>
      </div>
      <!-- 🔑 关键：组件调用自身，渲染子菜单 -->
      <div v-show="expanded[item.id]" class="sub-menu">
        <MenuItem :menus="item.children" />
      </div>
    </div>

    <!-- 没有子菜单：显示为可点击的菜单项 -->
    <router-link v-else :to="item.path" class="menu-item">
      {{ item.name }}
    </router-link>
  </template>
</template>

<script setup>
import { reactive } from 'vue'

// 定义 props，接收父组件传来的数据
const props = defineProps({
  menus: {
    type: Array,
    required: true
  }
})

// 控制每个菜单的展开/收起状态
const expanded = reactive({})

function toggle(id) {
  expanded[id] = !expanded[id]
}
</script>

<style scoped>
.menu-group { margin-left: 10px; }
.menu-title {
  padding: 8px 12px;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.menu-title:hover { background: rgba(255,255,255,0.1); }
.menu-item {
  display: block;
  padding: 8px 12px 8px 24px;
  color: #ccc;
  text-decoration: none;
}
.menu-item:hover,
.menu-item.router-link-active {
  background: rgba(255,255,255,0.15);
  color: #fff;
}
.arrow { font-size: 10px; }
.sub-menu { padding-left: 8px; }
</style>
```

> 💡 **知识点：`defineProps`**
> Vue 3 `<script setup>` 中使用 `defineProps` 声明组件接收的属性（props）。
> 父组件通过属性传递数据给子组件，实现**父子组件通信**。
>
> ```vue
> <!-- 父组件 -->
> <MenuItem :menus="menuList" />
>
> <!-- 子组件中通过 props.menus 接收 -->
> ```

> 💡 **知识点：递归组件注意事项**
> 组件在 `<script setup>` 中默认自动注册自身，可以直接用自己的文件名引用。
> 但在某些 IDE 中可能需要通过 `defineOptions({ name: 'MenuItem' })` 显式声明组件名。

---

## 第十章：完整布局 —— 侧边栏 + 内容区

### 10.1 目录结构

完成所有开发后，项目结构如下：

```
src/
├── main.js                    ← 入口：创建应用 + 注册路由
├── App.vue                    ← 根组件：渲染 <router-view>
├── router/
│   └── index.js               ← 路由配置
├── utils/
│   └── request.js             ← 请求封装
├── components/
│   └── MenuItem.vue           ← 递归菜单组件
├── views/
│   ├── Layout.vue             ← 布局：侧边栏 + 内容区
│   ├── Home.vue               ← 首页
│   ├── UserManage.vue         ← 用户管理
│   ├── RoleManage.vue         ← 角色管理
│   ├── MenuManage.vue         ← 菜单管理
│   └── About.vue              ← 关于页面
└── assets/
    └── style.css              ← 全局样式
```

### 10.2 App.vue —— 根组件

```vue
<!-- src/App.vue -->
<template>
  <router-view />
</template>
```

根组件只做一件事：渲染当前路由对应的组件。

### 10.3 Layout.vue —— 主布局

```vue
<!-- src/views/Layout.vue -->
<template>
  <div class="layout">
    <!-- 侧边栏 -->
    <aside class="sidebar">
      <h2 class="logo">管理系统</h2>
      <div v-if="loading" class="loading">加载中...</div>
      <MenuItem v-else :menus="menuList" />
    </aside>

    <!-- 主内容区 -->
    <main class="content">
      <router-view />    <!-- 子路由在这里渲染 -->
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import MenuItem from '@/components/MenuItem.vue'
import { get } from '@/utils/request'

const menuList = ref([])
const loading = ref(true)

onMounted(async () => {
  try {
    const result = await get('/system/menu/tree')
    if (result.code === 200) {
      menuList.value = result.data
    }
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.layout {
  display: flex;
  height: 100vh;
}
.sidebar {
  width: 240px;
  background: #304156;
  color: #bfcbd9;
  overflow-y: auto;
}
.logo {
  text-align: center;
  padding: 20px;
  color: #fff;
  font-size: 18px;
  border-bottom: 1px solid rgba(255,255,255,0.1);
}
.content {
  flex: 1;
  padding: 20px;
  background: #f5f5f5;
  overflow-y: auto;
}
.loading {
  text-align: center;
  padding: 20px;
  color: #999;
}
</style>
```

### 10.4 更新路由配置 —— 嵌套路由

使用**嵌套路由**，让页面在 Layout 的内容区中渲染：

```js
// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () => import('@/views/Layout.vue'),   // 懒加载
    redirect: '/home',
    children: [                                        // 嵌套子路由
      { path: 'home',          component: () => import('@/views/Home.vue') },
      { path: 'system/user',   component: () => import('@/views/UserManage.vue') },
      { path: 'system/role',   component: () => import('@/views/RoleManage.vue') },
      { path: 'system/menu',   component: () => import('@/views/MenuManage.vue') },
      { path: 'about',         component: () => import('@/views/About.vue') },
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
```

> 💡 **知识点：嵌套路由**
> 父路由的组件中放一个 `<router-view>`，子路由的组件就会渲染在其中。
> 这样 Layout（侧边栏）保持不变，只有内容区切换。

> 💡 **知识点：懒加载 `() => import()`**
> `() => import('@/views/Home.vue')` 是动态导入，打包时会把每个页面拆成单独的文件，
> 只有访问该页面时才加载，减少首屏加载体积。

### 10.5 页面组件示例

```vue
<!-- src/views/Home.vue -->
<template>
  <div>
    <h1>首页</h1>
    <p>欢迎使用管理系统！点击左侧菜单浏览不同页面。</p>
  </div>
</template>
```

```vue
<!-- src/views/UserManage.vue -->
<template>
  <div>
    <h1>用户管理</h1>
    <p>这里是用户管理页面的内容。</p>
  </div>
</template>
```

其他页面同理，替换标题和内容即可。

### 10.6 全局样式

```css
/* src/assets/style.css */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}
```

在 `main.js` 中引入：

```js
import './assets/style.css'
```

---

## 第十一章：常用 Vue 3 知识点速查

### 11.1 核心 API

| API | 用途 | 示例 |
|-----|------|------|
| `ref()` | 创建响应式引用 | `const count = ref(0)` |
| `reactive()` | 创建响应式对象 | `const obj = reactive({a: 1})` |
| `computed()` | 计算属性（有缓存） | `const double = computed(() => count.value * 2)` |
| `watch()` | 监听数据变化 | `watch(count, (newVal) => {...})` |
| `onMounted()` | 挂载后执行 | `onMounted(() => fetch())` |
| `defineProps()` | 声明组件属性 | `const props = defineProps({menus: Array})` |
| `defineEmits()` | 声明组件事件 | `const emit = defineEmits(['update'])` |

### 11.2 模板指令

| 指令 | 用途 | 示例 |
|------|------|------|
| `v-bind` / `:` | 绑定属性 | `:href="url"` |
| `v-on` / `@` | 绑定事件 | `@click="handle"` |
| `v-if` / `v-else` | 条件渲染（销毁重建） | `v-if="show"` |
| `v-show` | 条件显隐（CSS 控制） | `v-show="show"` |
| `v-for` | 列表渲染 | `v-for="item in list" :key="item.id"` |
| `v-model` | 双向绑定 | `v-model="inputValue"` |

### 11.3 父子组件通信

```
父 → 子：通过 props 传递数据
子 → 父：通过 emit 触发事件
```

```vue
<!-- 父组件 -->
<ChildComp :title="msg" @update="handleUpdate" />

<!-- 子组件 ChildComp.vue -->
<script setup>
const props = defineProps({ title: String })
const emit = defineEmits(['update'])
emit('update', '新数据')
</script>
```

### 11.4 watch —— 侦听器

```vue
<script setup>
import { ref, watch } from 'vue'

const keyword = ref('')

// 监听 keyword 变化
watch(keyword, (newVal, oldVal) => {
  console.log(`搜索词从 "${oldVal}" 变为 "${newVal}"`)
  // 可以在这里发请求搜索
})
</script>
```

---

## 第十二章：三种路由方案对比

### 12.1 方案总览

| 方案 | 谁控制菜单 | 加新页面要改什么 | 代表框架 |
|------|-----------|----------------|---------|
| A. 纯静态路由 | 前端代码写死 | 改 `router/index.js` | 简单项目、学习项目 |
| B. 前端映射表 + 动态路由 | 后端数据库 | 改 `menuMap.js` + 后端加数据 | 中小项目 |
| C. 后端返回组件路径 + 动态路由 | 后端数据库 | 后端加数据（前端不用改） | 若依（RuoYi）、大型项目 |

你当前项目用的是**方案 A（纯静态路由）**，下面详细介绍方案 B 和方案 C。

---

### 12.2 方案 A：纯静态路由（你当前的写法）

所有路由写死在 `router/index.js` 里，后端加了新菜单，前端必须改代码重新发布。

```js
// src/router/index.js
const routes = [
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children: [
      { path: 'home', component: Home },
      { path: 'system/user', component: User },
      { path: 'system/role', component: Role },
      // 每加一个页面就要在这里加一行
    ]
  }
]
```

> 适合学习和小型项目，简单直接。

---

### 12.3 方案 B：前端映射表 + 动态路由

#### 核心思路

`router/index.js` 只保留 Layout 壳子，子路由由后端菜单数据动态注册。
前端维护一个**映射表**，告诉程序"组件名 → 加载哪个文件"。

#### 整体流程

```
1. router/index.js 只定义 Layout 壳子（静态）
         ↓
2. Layout.vue 的 onMounted 里调后端接口拿菜单数据
         ↓
3. 用映射表把菜单的 component 字段转成懒加载函数
         ↓
4. 用 router.addRoute() 逐个注册为 Layout 的子路由
         ↓
5. 用同一份菜单数据渲染侧边栏
```

#### 第一步：改造 router/index.js

```js
// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

// 只保留壳子，children 为空
const routes = [
  {
    path: '/',
    name: 'Layout',       // 👈 给个名字，addRoute 要用
    component: Layout,
    redirect: '/home',
    children: []
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
```

#### 第二步：创建映射表 menuMap.js

```js
// src/router/menuMap.js

// 组件名 → 懒加载函数
// 后端菜单数据的 component 字段对应这里的 key
const menuMap = {
  Home: () => import('@/views/Home.vue'),
  About: () => import('@/views/About.vue'),
  User: () => import('@/views/User.vue'),
  Role: () => import('@/views/Role.vue'),
}

export default menuMap
```

> 💡 **知识点：`() => import()` 动态导入**
> 这是 ES Module 的懒加载语法，只有访问该路由时才加载对应的 JS 文件，
> 而不是一次性全部加载，提升首屏性能。

#### 第三步：Layout.vue 里调接口 + 注册动态路由

```vue
<!-- src/layout/Layout.vue -->
<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import menuMap from '@/router/menuMap'

const router = useRouter()
const menuList = ref([])

onMounted(() => {
  fetchMenus()
})

async function fetchMenus() {
  const response = await fetch('http://localhost:18082/system/menu/tree')
  const result = await response.json()

  if (result.code === 200) {
    menuList.value = result.data
    addDynamicRoutes(result.data)
  }
}

// 递归遍历菜单树，把每个叶子菜单注册为路由
function addDynamicRoutes(menus) {
  menus.forEach(menu => {
    // 👇 有 component 且映射表里有，就注册路由
    if (menu.component && menuMap[menu.component]) {
      router.addRoute('Layout', {
        path: menu.path,
        name: menu.name,
        component: menuMap[menu.component]
      })
    }
    if (menu.children && menu.children.length > 0) {
      addDynamicRoutes(menu.children)
    }
  })
}
</script>
```

#### 第四步：后端菜单数据加 component 字段

```json
[
  {
    "id": 1, "name": "首页", "path": "/home",
    "component": "Home"
  },
  {
    "id": 2, "name": "系统管理", "path": "/system",
    "children": [
      { "id": 3, "name": "用户管理", "path": "/system/user", "component": "User" },
      { "id": 4, "name": "角色管理", "path": "/system/role", "component": "Role" }
    ]
  }
]
```

其中 `"component": "User"` 对应映射表里的 `User: () => import('@/views/User.vue')`。

#### 方案 B 的优缺点

| | 说明 |
|---|---|
| ✅ 优点 | 路由代码不用每次改，菜单由后端控制 |
| ✅ 优点 | 映射表是白名单，后端填错不会加载到非法组件 |
| ❌ 缺点 | 加新页面还是要改 `menuMap.js`（但比改路由配置简单） |

---

### 12.4 方案 C：后端返回组件路径（若依方案）

#### 核心思路

后端菜单数据直接存**前端文件路径**，前端用 Vite 的 `import.meta.glob` 批量扫描所有 `.vue` 文件，运行时根据路径动态加载。

前端**完全不需要维护映射表**，只要 `.vue` 文件存在就能加载。

#### 整体流程

```
1. router/index.js 只定义 Layout 壳子
         ↓
2. Layout.vue 调后端接口拿菜单数据
         ↓
3. 后端返回 component: "system/user/index"
         ↓
4. 用 import.meta.glob 找到对应的 .vue 文件并加载
         ↓
5. 用 router.addRoute() 注册路由
```

#### 第一步：router/index.js（同方案 B）

```js
// src/router/index.js — 和方案 B 完全一样
import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/home',
    children: []
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
```

#### 第二步：创建动态加载工具函数

```js
// src/router/dynamicLoad.js

// 👇 Vite 提供的 API：批量扫描 src/views/ 下所有 .vue 文件
// 返回一个对象，key 是文件路径，value 是懒加载函数
const viewModules = import.meta.glob('@/views/**/*.vue')

/**
 * 根据后端返回的 component 路径，找到对应的懒加载函数
 *
 * 后端返回: "system/user/index"
 * 拼接后:   "/src/views/system/user/index.vue"
 * 从 viewModules 中找到匹配的加载函数
 */
export function loadView(component) {
  const fullPath = `/src/views/${component}.vue`
  return viewModules[fullPath]
}
```

> 💡 **知识点：`import.meta.glob()`**
> 这是 Vite 提供的批量导入 API，它在编译时扫描匹配的文件，
> 自动生成一个 `{ 文件路径: 懒加载函数 }` 的映射对象。
>
> 例如扫描结果：
> ```js
> {
>   '/src/views/Home.vue': () => import('/src/views/Home.vue'),
>   '/src/views/system/user/index.vue': () => import('/src/views/system/user/index.vue'),
>   // ...
> }
> ```
>
> 所以只要前端有这个 `.vue` 文件，后端填对路径就能加载，**不需要手动维护映射表**。

#### 第三步：Layout.vue 里调接口 + 注册动态路由

```vue
<!-- src/layout/Layout.vue -->
<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { loadView } from '@/router/dynamicLoad'

const router = useRouter()
const menuList = ref([])

onMounted(() => {
  fetchMenus()
})

async function fetchMenus() {
  const response = await fetch('http://localhost:18082/system/menu/tree')
  const result = await response.json()

  if (result.code === 200) {
    menuList.value = result.data
    addDynamicRoutes(result.data)
  }
}

function addDynamicRoutes(menus) {
  menus.forEach(menu => {
    if (menu.component) {
      // 👇 用 loadView 动态加载组件
      const componentFn = loadView(menu.component)
      if (componentFn) {
        router.addRoute('Layout', {
          path: menu.path,
          name: menu.name,
          component: componentFn
        })
      }
    }
    if (menu.children && menu.children.length > 0) {
      addDynamicRoutes(menu.children)
    }
  })
}
</script>
```

#### 第四步：后端返回前端文件路径

```json
[
  {
    "id": 1, "name": "首页", "path": "/home",
    "component": "Home"
  },
  {
    "id": 2, "name": "系统管理", "path": "/system",
    "children": [
      {
        "id": 3, "name": "用户管理", "path": "/system/user",
        "component": "system/user/index"
      },
      {
        "id": 4, "name": "角色管理", "path": "/system/role",
        "component": "system/role/index"
      }
    ]
  }
]
```

前端对应的目录结构：

```
src/views/
├── Home.vue                    ← 对应 component: "Home"
└── system/
    ├── user/
    │   └── index.vue           ← 对应 component: "system/user/index"
    └── role/
        └── index.vue           ← 对应 component: "system/role/index"
```

#### 方案 C 的优缺点

| | 说明 |
|---|---|
| ✅ 优点 | 前端完全不用改映射表，只管写 `.vue` 文件 |
| ✅ 优点 | 后端灵活配置，真正实现"加菜单不改前端代码" |
| ❌ 缺点 | 后端填错路径前端会白屏，需要做好错误处理 |
| ❌ 缺点 | 安全性略低，需要后端保证不填恶意路径 |

---

### 12.5 三种方案对比总结

```
方案 A（静态路由）：
  router/index.js 里写死所有路由
  加新页面 → 改 router/index.js → 重新发布前端

方案 B（前端映射表）：
  router/index.js 只有壳子
  menuMap.js 维护 组件名 → 加载函数 的映射
  加新页面 → 改 menuMap.js + 后端加数据 → 重新发布前端

方案 C（后端返回路径）：
  router/index.js 只有壳子
  import.meta.glob 自动扫描所有 .vue 文件
  加新页面 → 只在后端加数据 → 前端不用改（但要重新部署前端让新 .vue 文件生效）
```

| | 纯静态 (A) | 映射表 (B) | 后端路径 (C) |
|---|---|---|---|
| 复杂度 | ⭐ | ⭐⭐ | ⭐⭐⭐ |
| 灵活性 | 低 | 中 | 高 |
| 安全性 | 高 | 高 | 中 |
| 适合场景 | 学习、小型项目 | 中小项目 | 大型管理系统（若依） |

> 💡 **路由守卫 —— 解决刷新丢失问题**
> 以上方案 B 和 C 都有一个问题：用户刷新页面后，`addRoute()` 注册的动态路由会丢失。
> 解决方法是在 `router.beforeEach` 路由守卫中判断是否已加载，未加载则重新请求菜单并注册路由。
> 这是进阶内容，等你把基础流程跑通后再学习。

---

## 附录：常见问题

### Q1: 页面空白，控制台报错 `Cannot find module`
检查 import 路径是否正确，Vite 中用 `@/` 代表 `src/`。

### Q2: 点击菜单，URL 变了但页面没变
确认 `<router-view />` 是否放在了正确的位置。

### Q3: 后端接口请求失败（CORS 错误）
确认后端的 `CorsConfig` 已生效，后端需要重启。

### Q4: 菜单数据加载不出来
1. 确认后端已启动（端口 18082）
2. 浏览器 F12 → Network 查看请求是否成功
3. 检查返回的 JSON 结构是否正确

### Q5: 递归菜单组件报错 `Maximum recursive updates exceeded`
检查 `v-for` 是否有正确的 `:key`，以及 `v-if` 条件是否能正确终止递归。

---

> 📖 **学习建议**
> 1. 先按手册从零搭建项目，每完成一步都用 `npm run dev` 预览效果
> 2. 遇到不理解的 API，查看 [Vue 3 官方文档](https://cn.vuejs.org/)
> 3. 路由相关问题查 [Vue Router 文档](https://router.vuejs.org/zh/)
> 4. 尝试自己扩展：添加更多菜单项、修改样式、增加新页面
