import request from '@/utils/request'

export function getTrainingDashboard(query) {
  return request({
    url: '/training/dashboard',
    method: 'get',
    params: query
  })
}
