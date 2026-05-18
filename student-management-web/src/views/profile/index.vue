<template>
  <div class="profile-page">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card>
          <div class="user-info-card">
            <el-avatar :size="100">
              {{ userInfo?.realName?.charAt(0) }}
            </el-avatar>
            <div class="user-name">{{ userInfo?.realName }}</div>
            <div class="user-role">
              <el-tag>{{ userInfo?.roleName }}</el-tag>
            </div>
            <div class="user-details">
              <p><i class="el-icon-user"></i> 用户名：{{ userInfo?.username }}</p>
              <p><i class="el-icon-phone"></i> 手机号：{{ userInfo?.phone }}</p>
              <p><i class="el-icon-message"></i> 邮箱：{{ userInfo?.email }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>修改密码</span>
            </div>
          </template>
          <el-form ref="passwordFormRef" :model="passwordForm" :rules="passwordRules" label-width="80px">
            <el-form-item label="原密码" prop="oldPassword">
              <el-input v-model="passwordForm.oldPassword" type="password" placeholder="请输入原密码" />
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input v-model="passwordForm.newPassword" type="password" placeholder="请输入新密码" />
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input v-model="passwordForm.confirmPassword" type="password" placeholder="请确认新密码" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleUpdatePassword">修改密码</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/store/user'

const userStore = useUserStore()
const userInfo = computed(() => userStore.userInfo)
const passwordFormRef = ref(null)

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== passwordForm.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const passwordRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const handleUpdatePassword = async () => {
  if (!passwordFormRef.value) return
  await passwordFormRef.value.validate(async (valid) => {
    if (valid) {
      ElMessage.success('密码修改成功，请重新登录')
      await userStore.doLogout()
    }
  })
}
</script>

<style scoped lang="scss">
.profile-page {
  .user-info-card {
    text-align: center;
    padding: 20px;

    .user-name {
      font-size: 22px;
      font-weight: bold;
      margin-top: 20px;
      margin-bottom: 10px;
    }

    .user-details {
      text-align: left;
      margin-top: 30px;
      font-size: 14px;
      color: #666;

      p {
        margin-bottom: 15px;
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