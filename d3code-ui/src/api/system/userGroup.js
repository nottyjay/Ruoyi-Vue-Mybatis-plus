import request from '@/utils/request'

// 创建用户组
export function createUserGroup(data) {
  return request({
    url: '/system/user-group/create',
    method: 'post',
    data: data
  })
}

// 更新用户组
export function updateUserGroup(data) {
  return request({
    url: '/system/user-group/update',
    method: 'put',
    data: data
  })
}

// 删除用户组
export function deleteUserGroup(id) {
  return request({
    url: `/system/user-group/delete/${id}`,
    method: 'delete'
  })
}

// 获得用户组
export function getUserGroup(id) {
  return request({
    url: `/system/user-group/${id}`,
    method: 'get'
  })
}

// 获取用户组列表
export function listUserGroup(params) {
  return request({
    url: '/system/user-group/list',
    method: 'get',
    params
  })
}

export function changeUserGroupStatus(id, status) {

}

export function deleteUserInGroup(data) {
  return request({
    url: '/system/user-group/deleteUserInGroup',
    method: 'delete',
    data
  })
}

export function listUserByGroups(query) {
  return request({
    url: '/system/user-group/listInGroup',
    method: 'get',
    params: query
  })
}
