import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/store/user'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/',
    component: () => import('@/layout/index.vue'),
    redirect: '/dashboard',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { requiresAuth: true, title: '首页', icon: 'House' }
      },
      {
        path: 'students',
        name: 'Students',
        component: () => import('@/views/student/index.vue'),
        meta: { requiresAuth: true, title: '学生管理', icon: 'User', roles: ['ADMIN', 'TEACHER'] }
      },
      {
        path: 'classes',
        name: 'Classes',
        component: () => import('@/views/class/index.vue'),
        meta: { requiresAuth: true, title: '班级管理', icon: 'School', roles: ['ADMIN', 'TEACHER'] }
      },
      {
        path: 'courses',
        name: 'Courses',
        component: () => import('@/views/course/index.vue'),
        meta: { requiresAuth: true, title: '课程管理', icon: 'Reading', roles: ['ADMIN', 'TEACHER', 'STUDENT'] }
      },
      {
        path: 'scores',
        name: 'Scores',
        component: () => import('@/views/score/index.vue'),
        meta: { requiresAuth: true, title: '成绩管理', icon: 'Document', roles: ['ADMIN', 'TEACHER'] }
      },
      {
        path: 'teachers',
        name: 'Teachers',
        component: () => import('@/views/teacher/index.vue'),
        meta: { requiresAuth: true, title: '教师管理', icon: 'UserFilled', roles: ['ADMIN'] }
      },
      {
        path: 'users',
        name: 'Users',
        component: () => import('@/views/user/index.vue'),
        meta: { requiresAuth: true, title: '用户管理', icon: 'Setting', roles: ['ADMIN'] }
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/profile/index.vue'),
        meta: { requiresAuth: true, title: '个人中心', icon: 'UserFilled' }
      }
    ]
  },
  {
    path: '/403',
    name: '403',
    component: () => import('@/views/error/403.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  if (to.meta.requiresAuth && !userStore.token) {
    next('/login')
  } else if (to.path === '/login' && userStore.token) {
    next('/')
  } else if (to.meta.roles && to.meta.roles.length > 0) {
    const hasRole = to.meta.roles.some(role => userStore.hasRole(role))
    if (hasRole) {
      next()
    } else {
      next('/403')
    }
  } else {
    next()
  }
})

export default router