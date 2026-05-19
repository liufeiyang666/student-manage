<template>
  <div class="teacher-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>教师列表</span>
          <el-button type="primary" @click="handleAdd">新增教师</el-button>
        </div>
      </template>
      <el-table :data="tableData">
        <el-table-column prop="teacherNo" label="教师编号" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="gender" label="性别">
          <template #default="{ row }">
            {{ row.gender === '1' ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        :total="total"
        layout="total, prev, pager, next, jumper"
        @current-change="handleQuery"
        style="margin-top: 20px; justify-content: flex-end; display: flex"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑教师' : '新增教师'" width="500px">
      <el-form ref="formRef" :model="form" :rules="formRules" label-width="80px">
        <el-form-item label="教师编号" prop="teacherNo">
          <el-input v-model="form.teacherNo" placeholder="请输入教师编号" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="男" value="1" />
            <el-option label="女" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="form.age" :min="18" :max="80" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
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
import { getTeacherList, createTeacher, updateTeacher, deleteTeacher } from '@/api/teacher'

const queryForm = reactive({
  pageNum: 1,
  pageSize: 10
})

const formRef = ref(null)
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = reactive({})
const tableData = ref([])
const total = ref(0)

const formRules = {
  teacherNo: [{ required: true, message: '请输入教师编号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
}

const fetchTeacherList = async () => {
  try {
    const res = await getTeacherList(queryForm)
    if (res.code === 200) {
      tableData.value = res.data.list
      total.value = res.data.total
    }
  } catch (error) {
    ElMessage.error('查询失败')
  }
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(form, {
    teacherNo: '',
    name: '',
    gender: '1',
    age: 30,
    phone: '',
    email: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  Object.assign(form, {
    id: row.id,
    teacherNo: row.teacherNo,
    name: row.name,
    gender: row.gender,
    age: row.age,
    phone: row.phone,
    email: row.email
  })
  dialogVisible.value = true
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定删除此教师？', '提示', { type: 'warning' })
    await deleteTeacher(row.id)
    ElMessage.success('删除成功')
    fetchTeacherList()
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
          await updateTeacher(form.id, form)
        } else {
          await createTeacher(form)
        }
        ElMessage.success(isEdit.value ? '更新成功' : '创建成功')
        dialogVisible.value = false
        fetchTeacherList()
      } catch (error) {
        ElMessage.error('操作失败')
      }
    }
  })
}

const handleQuery = () => {
  fetchTeacherList()
}

onMounted(() => {
  fetchTeacherList()
})
</script>

<style scoped lang="scss">
.teacher-page {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>