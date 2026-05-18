import request from '@/utils/request'

export function getClassList() {
  return request({
    url: '/classes',
    method: 'get'
  })
}

export function createClass(data) {
  return request({
    url: '/classes',
    method: 'post',
    data
  })
}

export function updateClass(id, data) {
  return request({
    url: `/classes/${id}`,
    method: 'put',
    data
  })
}

export function deleteClass(id) {
  return request({
    url: `/classes/${id}`,
    method: 'delete'
  })
}