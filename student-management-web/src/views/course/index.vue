<template>
  <div class="course-page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>课程列表</span>
          <el-button type="primary" @click="handleAdd">新增课程</el-button>
        </div>
      </template>
      <el-table :data="tableData">
        <el-table-column prop="courseName" label="课程名称" />
        <el-table-column prop="courseCode" label="课程编号" />
        <el-table-column prop="credit" label="学分" />
        <el-table-column prop="teacherName" label="任课教师" />
        <el-table-column prop="description" label="描述" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑课程' : '新增课程'" width="500px">
      <el-form ref="formRef" :model="form" :rules="formRules" label-width="80px">
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="form.courseName" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="课程编号" prop="courseCode">
          <el-input v-model="form.courseCode" placeholder="请输入课程编号" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="学分" prop="credit">
          <el-input-number v-model="form.credit" :min="0" :max="10" :step="0.5" />
        </el-form-item>
        <el-form-item label="任课教师" prop="teacherId">
          <el-select v-model="form.teacherId" placeholder="请选择任课教师" clearable>
            <el-option v-for="t in teacherList" :key="t.id" :label="t.realName" :value="t.id" />
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
import { getCourseList, createCourse, updateCourse, deleteCourse } from '@/api/course'
import { getUserList } from '@/api/user'

const formRef = ref(null)
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = reactive({})
const tableData = ref([])
const teacherList = ref([])

const formRules = {
  courseName: [{ required: true, message: '请输入课程名称', trigger: 'blur' }],
  courseCode: [{ required: true, message: '请输入课程编号', trigger: 'blur' }]
}

const fetchCourseList = async () => {
  try {
    const res = await getCourseList()
    if (res.code === 200) {
      tableData.value = res.data
    }
  } catch (error) {
    ElMessage.error('查询失败')
  }
}

const fetchTeacherList = async () => {
  try {
    const res = await getUserList({ pageNum: 1, pageSize: 100 })
    if (res.code === 200) {
      teacherList.value = res.data.list.filter(user => user.roleCode === 'TEACHER')
    }
  } catch (error) {
    console.error(error)
  }
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(form, {
    courseName: '',
    courseCode: '',
    credit: 4,
    teacherId: null,
    description: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  Object.assign(form, {
    id: row.id,
    courseName: row.courseName,
    courseCode: row.courseCode,
    credit: row.credit,
    teacherId: row.teacherId,
    description: row.description
  })
  dialogVisible.value = true
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定删除此课程？', '提示', { type: 'warning' })
    await deleteCourse(row.id)
    ElMessage.success('删除成功')
    fetchCourseList()
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
          await updateCourse(form.id, form)
        } else {
          await createCourse(form)
        }
        ElMessage.success(isEdit.value ? '更新成功' : '创建成功')
        dialogVisible.value = false
        fetchCourseList()
      } catch (error) {
        ElMessage.error('操作失败')
      }
    }
  })
}

onMounted(() => {
  fetchCourseList()
  fetchTeacherList()
})
</script>

<style scoped lang="scss">
.course-page {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>