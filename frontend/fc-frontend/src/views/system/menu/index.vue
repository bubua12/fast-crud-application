<script setup>
import {ref, onMounted, computed} from 'vue'

// 所有菜单的树形数据
const menuTree = ref([])
// 当前选中的菜单节点
const selectedMenu = ref(null)

onMounted(() => {
  fetchMenus()
})

async function fetchMenus() {
  const response = await fetch('http://localhost:18082/system/menu/tree')
  const result = await response.json()
  if (result.code === 200) {
    menuTree.value = result.data
  }
}

// 点击树节点时，记录选中的菜单
function handleNodeClick(data) {
  selectedMenu.value = data
}

// 右侧表格展示的数据：选中菜单的 children
// 如果没选中，展示顶级菜单（parentId === 0）
const tableData = computed(() => {
  if (selectedMenu.value) {
    return selectedMenu.value.children || []
  }
  // 没选中时展示顶级菜单
  return menuTree.value
})

</script>

<template>
  <div>
    <h2>菜单管理</h2>

    <el-row :gutter="20">
      <!-- 左侧：菜单树 -->
      <el-col :span="8">
        <el-card header="菜单结构">
          <el-tree
              :data="menuTree"
              :props="{ label: 'name', children: 'children' }"
              node-key="id"
              highlight-current
              default-expand-all
              @node-click="handleNodeClick"
          />
        </el-card>
      </el-col>


      <!-- 右侧：菜单列表 -->
      <el-col :span="16">

        <el-card :header="selectedMenu ? selectedMenu.name + ' - 子菜单列表' : '顶级菜单列表'">
          <el-table :data="tableData" border stripe>
            <el-table-column prop="id" label="ID" width="80"/>
            <el-table-column prop="name" label="菜单名称"/>
            <el-table-column prop="path" label="路由路径"/>
            <el-table-column prop="component" label="组件路径"/>
            <el-table-column prop="sort" label="排序" width="80"/>
            <el-table-column label="有无子菜单" width="120">
              <template #default="{ row }">
                <el-tag :type="row.children && row.children.length ? 'success' : 'info'">
                  {{ row.children && row.children.length ? '有' : '无' }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>

      </el-col>
    </el-row>
  </div>
</template>

<style scoped>

</style>