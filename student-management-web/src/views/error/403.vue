<template>
  <div class="error-403">
    <div class="content">
      <h1>403</h1>
      <p>抱歉，您没有权限访问此页面</p>
      <el-button type="primary" @click="goHome">返回首页</el-button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goHome = () => {
  router.push('/dashboard')
}
</script>

<style scoped lang="scss">
.error-403 {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;

  .content {
    text-align: center;

    h1 {
      font-size: 100px;
      color: #909399;
      margin-bottom: 20px;
    }

    p {
      font-size: 24px;
      color: #606266;
      margin-bottom: 30px;
    }
  }
}
</style>