<template>
  <div class="dashboard">
    <el-row :gutter="20">
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
          <el-table :data="dashboardData.recentStudents" style="width: 100%">
            <el-table-column prop="studentNo" label="学号" />
            <el-table-column prop="name" label="姓名" />
            <el-table-column prop="className" label="班级" />
            <el-table-column prop="genderName" label="性别" />
            <el-table-column prop="statusName" label="状态" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getDashboardOverview } from '@/api/dashboard'
import { ElMessage } from 'element-plus'

const dashboardData = ref({
  studentCount: 0,
  teacherCount: 0,
  classCount: 0,
  courseCount: 0,
  recentStudents: [],
  scoreDistribution: [],
  classStatistics: []
})

const scoreChartRef = ref(null)
let scoreChart = null

const fetchDashboardData = async () => {
  try {
    const res = await getDashboardOverview()
    if (res.code === 200) {
      dashboardData.value = res.data
      renderScoreChart()
    }
  } catch (error) {
    ElMessage.error('获取数据失败')
  }
}

const renderScoreChart = () => {
  if (!scoreChartRef.value) return
  
  try {
    if (!scoreChart.value) {
      scoreChart.value = echarts.init(scoreChartRef.value)
    }
    
    const data = dashboardData.value.scoreDistribution || []
    
    if (!data || data.length === 0) {
      scoreChart.value.setOption({
        title: {
          text: '暂无数据',
          left: 'center',
          top: 'center',
          textStyle: {
            color: '#999',
            fontSize: 14
          }
        }
      })
      return
    }
    
    const validData = data
      .filter(item => item && item.count !== undefined && item.grade !== undefined)
      .map(item => ({
        value: item.count,
        name: item.grade
      }))
    
    if (validData.length === 0) {
      scoreChart.value.setOption({
        title: {
          text: '暂无数据',
          left: 'center',
          top: 'center',
          textStyle: {
            color: '#999',
            fontSize: 14
          }
        }
      })
      return
    }
    
    const option = {
      title: {
        text: ''
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        right: '10%',
        top: 'center'
      },
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
          label: {
            show: false,
            position: 'center'
          },
          emphasis: {
            label: {
              show: true,
              fontSize: 20,
              fontWeight: 'bold'
            }
          },
          labelLine: {
            show: false
          },
          data: validData
        }
      ]
    }

    scoreChart.value.setOption(option)
  } catch (error) {
    console.error('渲染图表失败:', error)
  }
}

const handleResize = () => {
  if (scoreChart?.value) {
    scoreChart.value.resize()
  }
}

onMounted(() => {
  fetchDashboardData()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  scoreChart?.dispose()
})
</script>

<style scoped lang="scss">
.dashboard {
  .stat-card {
    .stat-content {
      display: flex;
      align-items: center;
      padding: 10px 0;

      .stat-icon {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-right: 20px;
        font-size: 30px;
        color: white;

        &.student {
          background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }

        &.teacher {
          background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }

        &.class-info {
          background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }

        &.course {
          background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
        }
      }

      .stat-info {
        flex: 1;

        .stat-value {
          font-size: 32px;
          font-weight: bold;
          color: #333;
        }

        .stat-label {
          font-size: 14px;
          color: #999;
        }
      }
    }
  }

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>