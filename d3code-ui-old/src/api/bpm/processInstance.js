import request from '@/utils/request'

export function listProcessInstance(params) {
  return request({
    url: '/bpm/process-instance/list',
    method: 'get',
    params
  })
}

export function createProcessInstance(data) {
  return request({
    url: '/bpm/process-instance/create',
    method: 'POST',
    data: data
  })
}

export function cancelProcessInstance(id, reason) {
  return request({
    url: '/bpm/process-instance/cancel',
    method: 'DELETE',
    data: {
      id,
      reason
    }
  })
}

export function getProcessInstance(id) {
  return request({
    url: `/bpm/process-instance/get/${id}`,
    method: 'get'
  })
}
