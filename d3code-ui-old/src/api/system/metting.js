import request from '@/utils/request'

// 查询会议申请列表
export function listMetting(query) {
  return request({
    url: '/system/metting/list',
    method: 'get',
    params: query
  })
}

// 查询会议申请详细
export function getMetting(id) {
  return request({
    url: '/system/metting/' + id,
    method: 'get'
  })
}

// 新增会议申请
export function addMetting(data) {
  return request({
    url: '/system/metting',
    method: 'post',
    data: data
  })
}

// 修改会议申请
export function updateMetting(data) {
  return request({
    url: '/system/metting',
    method: 'put',
    data: data
  })
}

// 删除会议申请
export function delMetting(id) {
  return request({
    url: '/system/metting/' + id,
    method: 'delete'
  })
}

