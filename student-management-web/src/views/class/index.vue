<template>
  <div class="class-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>班级列表</span>
          <el-button type="primary" @click="handleAdd">新增班级</el-button>
        </div>
      </template>
      <el-table :data="tableData">
        <el-table-column prop="className" label="班级名称" />
        <el-table-column prop="classCode" label="班级编号" />
        <el-table-column prop="teacherName" label="班主任" />
        <el-table-column prop="studentCount" label="学生数" />
        <el-table-column prop="description" label="描述" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑班级' : '新增班级'" width="500px">
      <el-form ref="formRef" :model="form" :rules="formRules" label-width="80px">
        <el-form-item label="班级名称" prop="className">
          <el-input v-model="form.className" placeholder="请输入班级名称" />
        </el-form-item>
        <el-form-item label="班级编号" prop="classCode">
          <el-input v-model="form.classCode" placeholder="请输入班级编号" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="班主任" prop="teacherId">
          <el-select v-model="form.teacherId" placeholder="请选择班主任" clearable>
            <el-option v-for="t in teacherList" :key="t.id" :label="t.name" :value="t.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入描述" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getClassList, createClass, updateClass, deleteClass } from '@/api/class'
import { getTeacherList } from '@/api/teacher'

const formRef = ref(null)
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = reactive({})
const tableData = ref([])
const teacherList = ref([])

const formRules = {
  className: [{ required: true, message: '请输入班级名称', trigger: 'blur' }],
  classCode: [{ required: true, message: '请输入班级编号', trigger: 'blur' }]
}

const fetchClassList = async () => {
  try {
    const res = await getClassList()
    if (res.code === 200) {
      tableData.value = res.data
    }
  } catch (error) {
    ElMessage.error('查询失败')
  }
}

const fetchTeacherList = async () => {
  try {
    const res = await getTeacherList()
    if (res.code === 200) {
      teacherList.value = res.data.list
    }
  } catch (error) {
    console.error(error)
  }
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(form, {
    className: '',
    classCode: '',
    teacherId: null,
    description: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  Object.assign(form, {
    id: row.id,
    className: row.className,
    classCode: row.classCode,
    teacherId: row.teacherId,
    description: row.description
  })
  dialogVisible.value = true
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定删除此班级？', '提示', { type: 'warning' })
    await deleteClass(row.id)
    ElMessage.success('删除成功')
    fetchClassList()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          await updateClass(form.id, form)
        } else {
          await createClass(form)
        }
        ElMessage.success(isEdit.value ? '更新成功' : '创建成功')
        dialogVisible.value = false
        fetchClassList()
      } catch (error) {
        ElMessage.error('操作失败')
      }
    }
  })
}

onMounted(() => {
  fetchClassList()
  fetchTeacherList()
})
</script>

<style scoped lang="scss">
.class-page {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>