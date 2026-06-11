import {createWebHistory, createRouter} from 'vue-router'

import Layout from '../layout/Layout.vue'
import {loadView} from './dynamicLoad.js'

// 修改为只留壳子
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

// 👇 是否已经加载过动态路由
let dynamicRoutesLoaded = false

// 👇 路由守卫：每次跳转前检查
router.beforeEach(async (to, from, next) => {
    if (!dynamicRoutesLoaded) {
        const response = await fetch('http://localhost:18082/system/menu/tree')
        const result = await response.json()
        if (result.code === 200) {
            addDynamicRoutes(result.data)
        }
        dynamicRoutesLoaded = true
        next(to.path)    // 👈 路由注册完，重新跳转到目标页面
    } else {
        next()            // 👈 已加载，直接放行
    }
})

function addDynamicRoutes(menus) {
    menus.forEach(menu => {
        if (menu.component) {
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


export default router