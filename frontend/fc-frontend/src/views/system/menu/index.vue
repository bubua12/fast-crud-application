<script setup>
import {ref, onMounted, computed} from 'vue'
import {ElMessage, ElMessageBox} from "element-plus";

// ==================== 数据 ====================
const menuTree = ref([]) // 所有菜单的树形数据
const selectedMenu = ref(null) // 当前选中的菜单节点
const dialogVisible = ref(false)   // 弹窗是否显示
const dialogTitle = ref('')        // 弹窗标题
const form = ref({})               // 表单数据
const isEdit = ref(false)          // 当前是编辑还是新增

// ==================== 生命周期 ====================
onMounted(() => {
  fetchMenus()
})

// ==================== 获取菜单树 ====================
async function fetchMenus() {
  const response = await fetch('http://localhost:18082/system/menu/tree')
  const result = await response.json()
  if (result.code === 200) {
    menuTree.value = result.data
  }
}

// ==================== 树节点点击 ====================
// 点击树节点时，记录选中的菜单
function handleNodeClick(data) {
  selectedMenu.value = data
}

// ==================== 右侧表格数据 ====================
// 右侧表格展示的数据：选中菜单的 children
// 如果没选中，展示顶级菜单（parentId === 0）
const tableData = computed(() => {
  if (selectedMenu.value) {
    return selectedMenu.value.children || []
  }
  // 没选中时展示顶级菜单
  return menuTree.value
})

// ==================== 新增菜单 ====================
function handleAdd() {
  isEdit.value = false
  dialogTitle.value = '新增菜单'
  form.value = {
    parentId: selectedMenu.value ? selectedMenu.value.id : 0,
    name: '',
    path: '',
    icon: '',
    component: '',
    sort: 0
  }
  dialogVisible.value = true
}

// ==================== 编辑菜单 ====================
function handleEdit(row) {
  isEdit.value = true
  dialogTitle.value = '编辑菜单'
  form.value = {...row}    // 复制一份，避免直接修改原数据
  dialogVisible.value = true
}

// ==================== 删除菜单 ====================
async function handleDelete(row) {
  await ElMessageBox.confirm(
      `确定要删除菜单「${row.name}」吗？`,
      '提示',
      {type: 'warning'}
  )
  const response = await fetch(`http://localhost:18082/system/menu/${row.id}`, {
    method: 'DELETE'
  })
  const result = await response.json()
  if (result.code === 200) {
    ElMessage.success('删除成功')
    await fetchMenus()    // 刷新数据
  }
}

// ==================== 提交表单（新增/编辑） ====================
async function handleSubmit() {
  // 清空时默认为顶级菜单
  if (!form.value.parentId) {
    form.value.parentId = 0
  }

  const url = 'http://localhost:18082/system/menu'
  const method = isEdit.value ? 'PUT' : 'POST'

  const response = await fetch(url, {
    method,
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(form.value)
  })
  const result = await response.json()
  if (result.code === 200) {
    ElMessage.success(isEdit.value ? '编辑成功' : '新增成功')
    dialogVisible.value = false
    await fetchMenus()    // 刷新数据
  }
}

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

          <!-- 👇 新增按钮 -->
          <el-button type="primary" style="margin-bottom: 15px" @click="handleAdd">
            + 新增菜单
          </el-button>

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

            <!-- 👇 操作列 -->
            <el-table-column label="操作" width="160">
              <template #default="{ row }">
                <el-button size="small" @click="handleEdit(row)">编辑</el-button>
                <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
              </template>
            </el-table-column>

          </el-table>
        </el-card>

      </el-col>
    </el-row>


    <!-- 👇 新增/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form label-width="100px">

        <el-form-item label="父菜单">
          <el-tree-select
              v-model="form.parentId"
              :data="menuTree"
              :props="{ label: 'name', children: 'children', value: 'id' }"
              placeholder="请选择父菜单（不选则为顶级菜单）"
              clearable
              check-strictly
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="菜单名称">
          <el-input v-model="form.name" placeholder="请输入菜单名称"/>
        </el-form-item>
        <el-form-item label="路由路径">
          <el-input v-model="form.path" placeholder="如 /system/log"/>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="form.icon" placeholder="如 LogIcon"/>
        </el-form-item>
        <el-form-item label="组件路径">
          <el-input v-model="form.component" placeholder="如 system/log/index"/>
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0"/>
        </el-form-item>

      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

  </div>
</template>

<style scoped>

</style>