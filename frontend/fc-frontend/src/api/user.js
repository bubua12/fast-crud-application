import request from '@/utils/request'

// 获取所有应用
export function getMenuTree() {
    return request.get('/system/menu/tree')
}