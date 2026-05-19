<template>
  <div class="score-page">
    <el-card>
      <el-form :inline="true" :model="queryForm">
        <el-form-item label="课程">
          <el-select v-model="queryForm.courseId" placeholder="请选择课程" clearable>
            <el-option v-for="c in courseList" :key="c.id" :label="c.courseName" :value="c.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="学期">
          <el-input v-model="queryForm.semester" placeholder="请输入学期" />
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
          <span>成绩列表</span>
          <el-button type="primary" @click="handleAdd">新增成绩</el-button>
        </div>
      </template>
      <el-table :data="tableData">
        <el-table-column prop="studentName" label="学生姓名" />
        <el-table-column prop="studentNo" label="学号" />
        <el-table-column prop="className" label="班级" />
        <el-table-column prop="courseName" label="课程" />
        <el-table-column prop="usualScore" label="平时成绩" />
        <el-table-column prop="examScore" label="期末成绩" />
        <el-table-column prop="totalScore" label="总成绩" />
        <el-table-column prop="semester" label="学期" />
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

    <el-card style="margin-top: 20px">
      <template #header>
        <span>成绩统计</span>
      </template>
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-label">平均分</div>
            <div class="stat-value">{{ statistics.avgScore }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-label">最高分</div>
            <div class="stat-value">{{ statistics.maxScore }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-label">最低分</div>
            <div class="stat-value">{{ statistics.minScore }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-item">
            <div class="stat-label">及格率</div>
            <div class="stat-value">{{ statistics.passRate }}%</div>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑成绩' : '新增成绩'" width="500px">
      <el-form ref="formRef" :model="form" :rules="formRules" label-width="80px">
        <el-form-item label="学生" prop="studentId">
          <el-select v-model="form.studentId" placeholder="请选择学生" filterable>
            <el-option v-for="s in studentList" :key="s.id" :label="`${s.name} - ${s.studentNo}`" :value="s.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="课程" prop="courseId">
          <el-select v-model="form.courseId" placeholder="请选择课程">
            <el-option v-for="c in courseList" :key="c.id" :label="c.courseName" :value="c.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="平时成绩" prop="usualScore">
          <el-input-number v-model="form.usualScore" :min="0" :max="100" :precision="1" />
        </el-form-item>
        <el-form-item label="期末成绩" prop="examScore">
          <el-input-number v-model="form.examScore" :min="0" :max="100" :precision="1" />
        </el-form-item>
        <el-form-item label="学期" prop="semester">
          <el-input v-model="form.semester" placeholder="请输入学期，如：2024-2025-1" />
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
import { getScoreList, createScore, updateScore, deleteScore, getScoreStatistics } from '@/api/score'
import { getStudentList } from '@/api/student'
import { getCourseList } from '@/api/course'

const queryForm = reactive({
  pageNum: 1,
  pageSize: 10,
  courseId: null,
  semester: ''
})

const formRef = ref(null)
const dialogVisible = ref(false)
const isEdit = ref(false)
const form = reactive({})
const tableData = ref([])
const studentList = ref([])
const courseList = ref([])
const total = ref(0)
const statistics = ref({})

const formRules = {
  studentId: [{ required: true, message: '请选择学生', trigger: 'change' }],
  courseId: [{ required: true, message: '请选择课程', trigger: 'change' }],
  usualScore: [{ required: true, message: '请输入平时成绩', trigger: 'blur' }],
  examScore: [{ required: true, message: '请输入期末成绩', trigger: 'blur' }],
  semester: [{ required: true, message: '请输入学期', trigger: 'blur' }]
}

const fetchStudentList = async () => {
  try {
    const res = await getStudentList({ pageNum: 1, pageSize: 1000 })
    if (res.code === 200) {
      studentList.value = res.data.list
    }
  } catch (error) {
    console.error(error)
  }
}

const fetchCourseList = async () => {
  try {
    const res = await getCourseList()
    if (res.code === 200) {
      courseList.value = res.data
    }
  } catch (error) {
    console.error(error)
  }
}

const fetchStatistics = async () => {
  try {
    const res = await getScoreStatistics({
      courseId: queryForm.courseId,
      semester: queryForm.semester
    })
    if (res.code === 200) {
      statistics.value = res.data
    }
  } catch (error) {
    console.error(error)
  }
}

const handleQuery = async () => {
  try {
    const res = await getScoreList(queryForm)
    if (res.code === 200) {
      tableData.value = res.data.list
      total.value = res.data.total
    }
  } catch (error) {
    ElMessage.error('查询失败')
  }
  fetchStatistics()
}

const handleReset = () => {
  queryForm.courseId = null
  queryForm.semester = ''
  queryForm.pageNum = 1
  handleQuery()
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(form, {
    studentId: null,
    courseId: null,
    usualScore: 0,
    examScore: 0,
    semester: ''
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  Object.assign(form, {
    id: row.id,
    studentId: row.studentId,
    courseId: row.courseId,
    usualScore: row.usualScore,
    examScore: row.examScore,
    semester: row.semester
  })
  dialogVisible.value = true
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定删除此成绩？', '提示', { type: 'warning' })
    await deleteScore(row.id)
    ElMessage.success('删除成功')
    handleQuery()
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
          await updateScore(form.id, form)
        } else {
          await createScore(form)
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
  fetchStudentList()
  fetchCourseList()
  handleQuery()
})
</script>

<style scoped lang="scss">
.score-page {
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .stat-item {
    text-align: center;
    padding: 20px;
    background: #f5f7fa;
    border-radius: 4px;

    .stat-label {
      font-size: 14px;
      color: #909399;
      margin-bottom: 10px;
    }

    .stat-value {
      font-size: 28px;
      font-weight: bold;
      color: #333;
    }
  }
}
</style>