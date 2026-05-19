import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login, getCurrentUser, logout } from '@/api/auth'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || 'null'))

  const setToken = (newToken) => {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  const setUserInfo = (info) => {
    userInfo.value = info
    localStorage.setItem('userInfo', JSON.stringify(info))
  }

  const doLogin = async (loginForm) => {
    const res = await login(loginForm)
    if (res.code === 200) {
      setToken(res.data.token)
      setUserInfo(res.data.user)
      return true
    }
    return false
  }

  const fetchUserInfo = async () => {
    const res = await getCurrentUser()
    if (res.code === 200) {
      setUserInfo(res.data)
    }
  }

  const doLogout = async () => {
    try {
      await logout()
    } catch (e) {
      console.error(e)
    }
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  const hasRole = (roleCode) => {
    if (!userInfo.value) return false
    return userInfo.value.roleCode === roleCode
  }

  const isAdmin = () => hasRole('ADMIN')
  const isTeacher = () => hasRole('TEACHER')
  const isStudent = () => hasRole('STUDENT')

  return {
    token,
    userInfo,
    setToken,
    setUserInfo,
    doLogin,
    fetchUserInfo,
    doLogout,
    hasRole,
    isAdmin,
    isTeacher,
    isStudent
  }
})