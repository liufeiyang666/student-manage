<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <h1>学生管理系统</h1>
        <p>Student Management System</p>
      </div>
      <el-form ref="loginFormRef" :model="loginForm" :rules="rules" label-position="top">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleLogin" style="width: 100%">
            登录
          </el-button>
        </el-form-item>
      </el-form>
      <div class="login-footer">
        <p>测试账号：admin / 123456</p>
        <p>教师：teacher1 / 123456</p>
        <p>学生：student1 / 123456</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const loginFormRef = ref(null)
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  if (!loginFormRef.value) return
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const success = await userStore.doLogin(loginForm)
        if (success) {
          ElMessage.success('登录成功')
          router.push('/dashboard')
        }
      } catch (error) {
        console.error('Login error:', error)
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped lang="scss">
.login-container {
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  justify-content: center;
  align-items: center;

  .login-box {
    width: 400px;
    padding: 40px;
    background: white;
    border-radius: 10px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

    .login-header {
      text-align: center;
      margin-bottom: 40px;

      h1 {
        color: #333;
        margin-bottom: 10px;
      }

      p {
        color: #999;
        font-size: 14px;
      }
    }

    .login-footer {
      text-align: center;
      margin-top: 20px;
      font-size: 12px;
      color: #999;
      line-height: 1.8;
    }
  }
}
</style>