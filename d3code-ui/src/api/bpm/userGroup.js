import request from '@/utils/request'

// 创建用户组
export function createUserGroup(data) {
  return request({
    url: '/bpm/user-group/create',
    method: 'post',
    data: data
  })
}

// 更新用户组
export function updateUserGroup(data) {
  return request({
    url: '/bpm/user-group/update',
    method: 'put',
    data: data
  })
}

// 删除用户组
export function deleteUserGroup(id) {
  return request({
    url: `/bpm/user-group/delete/${id}`,
    method: 'delete'
  })
}

// 获得用户组
export function getUserGroup(id) {
  return request({
    url: `/bpm/user-group/${id}`,
    method: 'get'
  })
}

// 获取用户组列表
export function listUserGroup(params) {
  return request({
    url: '/bpm/user-group/list',
    method: 'get',
    params
  })
}
