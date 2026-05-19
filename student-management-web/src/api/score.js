import request from '@/utils/request'

export function getScoreList(params) {
  return request({
    url: '/scores',
    method: 'get',
    params
  })
}

export function createScore(data) {
  return request({
    url: '/scores',
    method: 'post',
    data
  })
}

export function updateScore(id, data) {
  return request({
    url: `/scores/${id}`,
    method: 'put',
    data
  })
}

export function deleteScore(id) {
  return request({
    url: `/scores/${id}`,
    method: 'delete'
  })
}

export function getScoreStatistics(params) {
  return request({
    url: '/scores/statistics',
    method: 'get',
    params
  })
}

export function getScoresByStudentId(studentId) {
  return request({
    url: `/scores/student/${studentId}`,
    method: 'get'
  })
}