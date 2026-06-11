// 👇 Vite 提供的 API：编译时扫描 src/views/ 下所有 .vue 文件
// 返回：{ '/src/views/Home.vue': () => import(...), '/src/views/system/user/index.vue': () => import(...), ... }
const viewModules = import.meta.glob('@/views/**/*.vue')

/**
 * 根据后端返回的 component 路径，找到对应的懒加载函数
 *
 * 例：loadView("system/user/index")
 *   → 拼接成 "/src/views/system/user/index.vue"
 *   → 从 viewModules 里找到匹配的加载函数
 */
export function loadView(component) {
    const fullPath = `/src/views/${component}.vue`
    return viewModules[fullPath]
}

// import.meta.glob 是 Vite 编译时 API，它会自动扫描文件并生成映射对象，你不需要手动维护。