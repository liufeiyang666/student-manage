<template>
  <el-container class="layout-container">
    <el-aside :width="isCollapse ? '64px' : '200px'">
      <div class="logo">
        <h1 v-if="!isCollapse">学生管理系统</h1>
        <h1 v-else>SMS</h1>
      </div>
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
      >
        <template v-for="route in menuRoutes" :key="route.path">
          <el-menu-item :index="route.path">
            <el-icon><component :is="route.meta.icon" /></el-icon>
            <template #title>{{ route.meta.title }}</template>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>

    <el-container class="main-container">
      <el-header>
        <div class="header-left">
          <el-icon class="collapse-btn" @click="toggleCollapse">
            <Fold v-if="!isCollapse" />
            <Expand v-else />
          </el-icon>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/dashboard' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="currentRoute">{{ currentRoute.meta.title }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="32" :src="''">
                {{ userInfo?.realName?.charAt(0) }}
              </el-avatar>
              <span class="user-name">{{ userInfo?.realName }}</span>
              <el-icon class="el-icon--right"><arrow-down /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">
                  <el-icon><User /></el-icon> 个人中心
                </el-dropdown-item>
                <el-dropdown-item command="logout">
                  <el-icon><SwitchButton /></el-icon> 退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/store/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const isCollapse = ref(false)
const userInfo = computed(() => userStore.userInfo)

const menuRoutes = computed(() => {
  const routes = router.getRoutes()
  const mainRoute = routes.find(r => r.path === '/')
  if (!mainRoute) return []
  return mainRoute.children
    .filter(route => {
      if (!route.meta?.title) return false
      if (route.meta.roles) {
        return route.meta.roles.some(role => userStore.hasRole(role))
      }
      return true
    })
    .sort((a, b) => {
      const order = { Dashboard: 0, Students: 1, Classes: 2, Courses: 3, Scores: 4, Teachers: 5, Users: 6, Profile: 9 }
      return order[a.name] - order[b.name]
    })
})

const activeMenu = computed(() => route.path)
const currentRoute = computed(() => route)

const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value
}

const handleCommand = async (command) => {
  if (command === 'logout') {
    await userStore.doLogout()
    router.push('/login')
  } else if (command === 'profile') {
    router.push('/profile')
  }
}
</script>

<style scoped lang="scss">
.layout-container {
  height: 100%;

  .el-aside {
    background-color: #304156;
    transition: width 0.3s;

    .logo {
      height: 60px;
      line-height: 60px;
      text-align: center;
      color: #fff;
      font-size: 18px;
      font-weight: bold;
      background-color: #2b3a4a;
    }

    .el-menu {
      border: none;
    }
  }

  .main-container {
    background-color: #f0f2f5;

    .el-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      background-color: #fff;
      box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

      .header-left {
        display: flex;
        align-items: center;
        gap: 20px;

        .collapse-btn {
          cursor: pointer;
          font-size: 20px;
          transition: transform 0.3s;
          &:hover {
            color: #409EFF;
          }
        }
      }

      .header-right {
        .user-info {
          display: flex;
          align-items: center;
          gap: 8px;
          cursor: pointer;

          .user-name {
            margin-left: 8px;
          }
        }
      }
    }

    .el-main {
      padding: 20px;
    }
  }
}
</style>