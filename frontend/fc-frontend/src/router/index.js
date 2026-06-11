import {createWebHistory, createRouter} from 'vue-router'

import Layout from '../layout/Layout.vue'
// import Home from '../views/Home.vue'
// import About from '../views/About.vue'
// import User from '../views/User.vue'
// import Role from '../views/Role.vue'
// import Menu from '../views/Menu.vue'

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

export default router