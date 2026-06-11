import {createWebHistory, createRouter} from 'vue-router'

import Layout from '../layout/Layout.vue'
import Home from '../views/Home.vue'
import About from '../views/About.vue'
import User from '../views/User.vue'
import Role from '../views/Role.vue'

const routes = [
    {
        path: '/',
        component: Layout,
        redirect: '/home',
        children: [
            {path: 'home', name: 'Home', component: Home},
            {path: 'about', name: 'About', component: About},
            {path: 'system/user', name: 'User', component: User},
            {path: 'system/role', name: 'Role', component: Role},
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router