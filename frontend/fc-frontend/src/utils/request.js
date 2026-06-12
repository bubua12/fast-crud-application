import axios from 'axios'
import {ElMessage} from "element-plus";

// 创建 axios 实例，配置基础信息
const service = axios.create({
    baseURL: import.meta.env.BASE_URL, // 所有请求的基础地址
    timeout: 20000, // 请求超时 20 秒
})

// ========== 请求拦截器 ==========
service.interceptors.request.use(
    config => {
        // 这里可以自动带上 token
        // const token = localStorage.getItem('token')
        // if (token) {
        //   config.headers['Authorization'] = 'Bearer ' + token
        // }
        return config
    }
)

// ========== 响应拦截器 ==========
// 每次收到响应之后自动执行
service.interceptors.response.use(
    response => {
        const res = response.data

        // 后端返回的 code 不是 200，统一提示错误
        if (res.code !== 200) {
            ElMessage.error(res.msg || '请求失败')
            return Promise.reject(new Error(res.message))
        }

        return res // 成功时直接返回，不用再 .json()
    },
    error => {
        // 网络错误、超时等
        ElMessage.error('网络异常，请稍后重试')
        return Promise.reject(error)
    }
)

export default service