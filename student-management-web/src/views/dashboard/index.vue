<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <!-- 统计卡片 -->
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon student">
              <el-icon><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ dashboardData.studentCount }}</div>
              <div class="stat-label">学生总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon teacher">
              <el-icon><UserFilled /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ dashboardData.teacherCount }}</div>
              <div class="stat-label">教师总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon class-info">
              <el-icon><School /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ dashboardData.classCount }}</div>
              <div class="stat-label">班级总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon course">
              <el-icon><Reading /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ dashboardData.courseCount }}</div>
              <div class="stat-label">课程总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>成绩分布</span>
            </div>
          </template>
          <div ref="scoreChartRef" style="height: 350px"></div>
        </el-card>
      </el-col>
      
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>最近新增学生</span>
            </div>
          </template>
          <!-- 补全：修复你截断的表格代码 -->
          <el-table :data="dashboardData.recentStudents" style="width: 100%">
            <el-table-column prop="username" label="用户名" />
            <el-table-column prop="realName" label="姓名" />
            <el-table-column prop="className" label="班级" />
            <el-table-column prop="createTime" label="注册时间" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { User, UserFilled, School, Reading } from '@element-plus/icons-vue'
import * as echarts from 'echarts'

const scoreChartRef = ref(null)

const dashboardData = reactive({
  studentCount: 0,
  teacherCount: 0,
  classCount: 0,
  courseCount: 0,
  scoreDistribution: [],
  recentStudents: []
})

// 初始化图表
const initChart = () => {
  if (!scoreChartRef.value) return
  const chart = echarts.init(scoreChartRef.value)
  const data = dashboardData.scoreDistribution || []
  
  const option = {
    tooltip: { trigger: 'item' },
    legend: { bottom: '0%' },
    series: [
      {
        name: '成绩分布',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: { show: false, position: 'center' },
        emphasis: {
          label: { show: true, fontSize: 20, fontWeight: 'bold' }
        },
        labelLine: { show: false },
        data: data.map(item => ({ value: item.count, name: item.grade })) // 修复之前的语法错误
      }
    ]
  }
  chart.setOption(option)
}

// 模拟获取数据 (正式项目中替换为你自己的 API 请求)
const fetchData = async () => {
  // 这里应该是类似 const res = await getDashboardData()
  // 模拟数据：
  dashboardData.studentCount = 120
  dashboardData.teacherCount = 15
  dashboardData.classCount = 6
  dashboardData.courseCount = 20
  dashboardData.scoreDistribution = [
    { grade: '优秀 (90-100)', count: 30 },
    { grade: '良好 (80-89)', count: 45 },
    { grade: '中等 (70-79)', count: 25 },
    { grade: '及格 (60-69)', count: 15 },
    { grade: '不及格 (<60)', count: 5 }
  ]
  dashboardData.recentStudents = [
    { username: 'stu001', realName: '张三', className: '高三1班', createTime: '2024-05-18' },
    { username: 'stu002', realName: '李四', className: '高三2班', createTime: '2024-05-17' }
  ]
  
  initChart()
}

onMounted(() => {
  fetchData()
})
</script>

<style scoped lang="scss">
.dashboard {
  .stat-card {
    .stat-content {
      display: flex;
      align-items: center;
      .stat-icon {
        width: 60px;
        height: 60px;
        border-radius: 8px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 28px;
        color: #fff;
        margin-right: 15px;
        &.student { background: linear-gradient(135deg, #409eff, #53a8ff); }
        &.teacher { background: linear-gradient(135deg, #67c23a, #85ce61); }
        &.class-info { background: linear-gradient(135deg, #e6a23c, #ebb563); }
        &.course { background: linear-gradient(135deg, #f56c6c, #f78989); }
      }
      .stat-info {
        .stat-value { font-size: 24px; font-weight: bold; color: #303133; }
        .stat-label { font-size: 14px; color: #909399; margin-top: 5px; }
      }
    }
  }
  .card-header { font-weight: bold; }
}
</style>
