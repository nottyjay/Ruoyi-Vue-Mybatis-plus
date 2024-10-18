import request from '@/utils/request'

// 查询业务流表单列表
export function listBpmForm(query) {
  return request({
    url: '/bpm/bpmForm/list',
    method: 'get',
    params: query
  })
}

// 查询业务流表单详细
export function getBpmForm(id) {
  return request({
    url: '/bpm/bpmForm/' + id,
    method: 'get'
  })
}

// 新增业务流表单
export function addBpmForm(data) {
  return request({
    url: '/bpm/bpmForm',
    method: 'post',
    data: data
  })
}

// 修改业务流表单
export function updateBpmForm(data) {
  return request({
    url: '/bpm/bpmForm',
    method: 'put',
    data: data
  })
}

// 删除业务流表单
export function delBpmForm(id) {
  return request({
    url: '/bpm/bpmForm/' + id,
    method: 'delete'
  })
}

// 获得动态表单的精简列表
export function getSimpleForms() {
  return request({
    url: '/bpm/bpmForm/list-all-simple',
    method: 'get'
  })
}

