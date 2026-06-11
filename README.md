<div align="center">

# 🏗️ Fast CRUD Application

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-green?style=for-the-badge&logo=springboot&logoColor=white)
![MyBatis-Plus](https://img.shields.io/badge/MyBatis--Plus-3.5.5-blue?style=for-the-badge&logo=mybatis&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql&logoColor=white)
![Vue.js](https://img.shields.io/badge/Vue.js-3.5-brightgreen?style=for-the-badge&logo=vuedotjs&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-8.0-purple?style=for-the-badge&logo=vite&logoColor=white)
![Vue Router](https://img.shields.io/badge/Vue%20Router-4.6-blue?style=for-the-badge&logo=vuedotjs&logoColor=white)

一个基于 **Spring Boot + Vue 3** 的动态菜单管理系统 Demo

后端存储菜单数据，前端动态渲染侧边栏 + 动态注册路由

</div>

---

## ✨ 功能特性

- 📦 后端返回树形菜单结构，前端自动渲染任意层级
- 🛤️ 动态路由：前端根据后端数据自动注册路由（`import.meta.glob` + `router.addRoute()`）
- 🗄️ MySQL 持久化存储，支持新增/删除菜单
- 🌐 跨域配置，前后端分离开发
- 📖 附带完整的 Vue 3 学习手册（1300+ 行）

---

## 🏗️ 技术栈

| 层级 | 技术 | 版本 |
|------|------|------|
| 后端框架 | Spring Boot | 3.2.4 |
| ORM | MyBatis-Plus | 3.5.5 |
| 数据库 | MySQL | 8.0 |
| 前端框架 | Vue 3 (Composition API) | 3.5 |
| 路由 | Vue Router | 4.6 |
| 构建工具 | Vite | 8.0 |
| JDK | Java | 21 |

---

## 📁 项目结构

```
fast-crud-application/
├── backend/                              # 后端（Spring Boot）
│   └── fast-crud-application/
│       ├── pom.xml
│       └── src/main/
│           ├── java/com/bubua12/fca/
│           │   ├── FCQuickApplication.java     # 启动类
│           │   ├── config/
│           │   │   └── CorsConfig.java         # 跨域配置
│           │   ├── controller/
│           │   │   └── SystemController.java   # 菜单接口
│           │   ├── entity/
│           │   │   ├── Menu.java               # 菜单实体
│           │   │   └── CommonResult.java       # 统一响应
│           │   ├── mapper/
│           │   │   └── MenuMapper.java         # 数据访问
│           │   └── service/
│           │       └── MenuService.java        # 业务逻辑
│           └── resources/
│               ├── application.yaml
│               └── db/
│                   └── menu.sql               # 建表脚本
│
├── frontend/                             # 前端（Vue 3）
│   ├── Vue3-动态菜单学习手册.md             # 📖 学习手册
│   └── fc-frontend/
│       ├── vite.config.js
│       └── src/
│           ├── main.js                   # 入口文件
│           ├── App.vue                   # 根组件
│           ├── layout/
│           │   └── Layout.vue            # 布局骨架
│           ├── router/
│           │   ├── index.js              # 路由配置 + 守卫
│           │   └── dynamicLoad.js        # 动态组件加载
│           └── views/
│               ├── Home.vue
│               ├── About.vue
│               └── system/
│                   ├── user/index.vue
│                   ├── role/index.vue
│                   └── menu/index.vue
│
└── README.md
```

---

## 🚀 快速开始

### 环境要求

- JDK 21+
- Node.js 18+
- MySQL 8.0+

### 1. 初始化数据库

在 MySQL 中执行建表脚本：

```bash
mysql -u root -p < backend/fast-crud-application/src/main/resources/db/menu.sql
```

或手动复制 `menu.sql` 内容到 MySQL 客户端执行。

### 2. 启动后端

```bash
cd backend/fast-crud-application
# 修改 application.yaml 中的数据库账号密码
mvn spring-boot:run
```

后端启动在 `http://localhost:18082`

### 3. 启动前端

```bash
cd frontend/fc-frontend
npm install
npm run dev
```

前端启动在 `http://localhost:5173`

---

## 📡 API 接口

| 方法 | 路径 | 说明 |
|------|------|------|
| `GET` | `/system/menu/tree` | 获取菜单树 |
| `POST` | `/system/menu` | 新增菜单 |
| `DELETE` | `/system/menu/{id}` | 删除菜单 |

### 统一响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": [...]
}
```

### 示例：新增菜单

```bash
curl -X POST http://localhost:18082/system/menu \
  -H "Content-Type: application/json" \
  -d '{
    "parentId": 2,
    "name": "日志管理",
    "path": "/system/log",
    "icon": "LogIcon",
    "component": "system/log/index",
    "sort": 4
  }'
```

---

## 🗄️ 数据库设计

**表名：`sys_menu`**（邻接表模型，`parent_id` 表示父子关系）

| 字段 | 类型 | 说明 |
|------|------|------|
| `id` | BIGINT | 主键，自增 |
| `parent_id` | BIGINT | 父菜单ID（0 = 顶级） |
| `name` | VARCHAR(64) | 菜单名称 |
| `path` | VARCHAR(128) | 路由路径 |
| `icon` | VARCHAR(64) | 图标标识 |
| `component` | VARCHAR(128) | 前端组件路径 |
| `sort` | INT | 排序号（越小越靠前） |
| `create_time` | DATETIME | 创建时间 |
| `update_time` | DATETIME | 更新时间 |

---

## 🛤️ 动态路由流程

```
后端数据库 sys_menu
       ↓
GET /system/menu/tree（返回树形 JSON）
       ↓
┌─────────────────────────────────────────────┐
│  前端 router.beforeEach 守卫                  │
│  1. 拿到菜单数据                              │
│  2. 用 import.meta.glob 扫描 views/**/*.vue   │
│  3. 根据 component 字段匹配组件               │
│  4. router.addRoute() 动态注册路由            │
└─────────────────────────────────────────────┘
       ↓
Layout.vue 渲染侧边栏菜单
router-view 渲染当前页面
```

---

## 📖 学习手册

项目附带了一份完整的 [Vue 3 动态菜单学习手册](frontend/Vue3-动态菜单学习手册.md)，涵盖：

| 章节 | 内容 |
|------|------|
| 第 1-2 章 | 环境准备、项目结构 |
| 第 3-5 章 | SFC、响应式数据、模板语法 |
| 第 6-7 章 | 生命周期、fetch 请求 |
| 第 8 章 | Vue Router 路由 |
| 第 9 章 | 递归组件渲染无限层级菜单 |
| 第 10 章 | 完整布局实现 |
| 第 11 章 | Vue 3 知识点速查 |
| 第 12 章 | 三种动态路由方案对比（含若依方案） |

---

## 📝 License

MIT

---

<div align="center">

Made with ❤️ by [bubua12](https://github.com/bubua12)

</div>
