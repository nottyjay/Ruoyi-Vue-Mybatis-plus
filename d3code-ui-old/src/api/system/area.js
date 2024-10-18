import request from '@/utils/request'

// 查询地区管理列表
export function listArea(query) {
  return request({
    url: '/system/area/list',
    method: 'get',
    params: query
  })
}

// 查询地区管理详细
export function getArea(id) {
  return request({
    url: '/system/area/' + id,
    method: 'get'
  })
}

// 新增地区管理
export function addArea(data) {
  return request({
    url: '/system/area',
    method: 'post',
    data: data
  })
}

// 修改地区管理
export function updateArea(data) {
  return request({
    url: '/system/area',
    method: 'put',
    data: data
  })
}

// 删除地区管理
export function delArea(id) {
  return request({
    url: '/system/area/' + id,
    method: 'delete'
  })
}

// 查询地区管理树状菜单
export function getAreaTree() {
  return request({
    url: '/system/area/getTree',
    method: 'get'
  })
}

export function syncProcess() {
  return request({
    url: '/system/area/syncProcess',
    method: 'get'
  })
}
