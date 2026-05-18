<template>
  <div class="student-page">
    <el-card>
      <el-form :inline="true" :model="queryForm">
        <el-form-item label="姓名">
          <el-input v-model="queryForm.name" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="queryForm.studentNo" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="班级">
          <el-select v-model="queryForm.classId" placeholder="请选择班级" clearable>
            <el-option v-for="cls in classList" :key="cls.id" :label="cls.className" :value="cls.id" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card style="margin-top: 20px">
      <template #header>
        <div class="card-header">
          <span>学生列表</span>
          <div>
            <el-button type="danger" :disabled="!selectedIds.length" @click="handleBatchDelete">批量删除</el-button>
            <el-button type="primary" @click="handleAdd">新增学生</el-button>
          </div>
        </div>
      </template>
      <el-table :data="tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="studentNo" label="学号" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="genderName" label="性别" />
        <el-table-column prop="age" label="年龄" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="className" label="班级" />
        <el-table-column prop="statusName" label="状态" />
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
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleQuery"
        @current-change="handleQuery"
        style="margin-top: 20px; justify-content: flex-end; display: flex"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑学生' : '新增学生'" width="600px">
      <el-form ref="formRef" :model="form" :rules="formRules" label-width="80px">
        <el-form-item label="学号" prop="studentNo">
          <el-input v-model="form.studentNo" placeholder="请输入学号" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择">
            <el-option label="男" :value="1" />
            <el-option label="女" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="form.age" :min="1" :max="100" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="班级" prop="classId">
          <el-select v-model="form.classId" placeholder="请选择班级">
            <el-option v-for="cls in classList" :key="cls.id" :label="cls.className" :value="cls.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="入学日期" prop="enrollmentDate">
          <el-date-picker v-model="form.enrollmentDate" type="date" placeholder="请选择日期" value-format="YYYY-MM-DD" />
        </el-form-item>
        <el-form-item label="家庭住址" prop="address">
          <el-input v-model="form.address" type="textarea" placeholder="请输入家庭住址" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择">
            <el-option label="在读" :value="1" />
            <el-option label="休学" :value="0" />
            <el-option label="毕业" :value="2" />
          </el-select>
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
import { getStudentList, createStudent, updateStudent, deleteStudent, deleteStudents } from '@/api/student'
import { getClassList } from '@/api/class'

const queryForm = reactive({
  pageNum: 1,
  pageSize: 10,
  name: '',
  studentNo: '',
  classId: null
})

const formRef = ref(null)
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = reactive({})
const tableData = ref([])
const classList = ref([])
const total = ref(0)
const selectedIds = ref([])

const formRules = {
  studentNo: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
}

const fetchClassList = async () => {
  try {
    const res = await getClassList()
    if (res.code === 200) {
      classList.value = res.data
    }
  } catch (error) {
    console.error(error)
  }
}

const handleQuery = async () => {
  try {
    const res = await getStudentList(queryForm)
    if (res.code === 200) {
      tableData.value = res.data.list
      total.value = res.data.total
    }
  } catch (error) {
    ElMessage.error('查询失败')
  }
}

const handleReset = () => {
  queryForm.name = ''
  queryForm.studentNo = ''
  queryForm.classId = null
  queryForm.pageNum = 1
  handleQuery()
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(form, {
    studentNo: '',
    name: '',
    gender: 1,
    age: 18,
    phone: '',
    email: '',
    classId: null,
    enrollmentDate: '',
    address: '',
    status: 1
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  Object.assign(form, {
    id: row.id,
    studentNo: row.studentNo,
    name: row.name,
    gender: row.gender,
    age: row.age,
    phone: row.phone,
    email: row.email,
    classId: row.classId,
    enrollmentDate: row.enrollmentDate,
    address: row.address,
    status: row.status
  })
  dialogVisible.value = true
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定删除此学生？', '提示', { type: 'warning' })
    await deleteStudent(row.id)
    ElMessage.success('删除成功')
    handleQuery()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const handleBatchDelete = async () => {
  try {
    await ElMessageBox.confirm('确定删除选中的学生？', '提示', { type: 'warning' })
    await deleteStudents(selectedIds.value)
    ElMessage.success('批量删除成功')
    handleQuery()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const handleSelectionChange = (selection) => {
  selectedIds.value = selection.map(item => item.id)
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (isEdit.value) {
          await updateStudent(form.id, form)
        } else {
          await createStudent(form)
        }
        ElMessage.success(isEdit.value ? '更新成功' : '创建成功')
        dialogVisible.value = false
        handleQuery()
      } catch (error) {
        ElMessage.error('操作失败')
      }
    }
  })
}

onMounted(() => {
  fetchClassList()
  handleQuery()
})
</script>

<style scoped lang="scss">
.student-page {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>