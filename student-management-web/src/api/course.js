import request from '@/utils/request'

export function getCourseList() {
  return request({
    url: '/courses',
    method: 'get'
  })
}

export function createCourse(data) {
  return request({
    url: '/courses',
    method: 'post',
    data
  })
}

export function updateCourse(id, data) {
  return request({
    url: `/courses/${id}`,
    method: 'put',
    data
  })
}

export function deleteCourse(id) {
  return request({
    url: `/courses/${id}`,
    method: 'delete'
  })
}

export function getCoursesByTeacherId(teacherId) {
  return request({
    url: `/courses/teacher/${teacherId}`,
    method: 'get'
  })
}