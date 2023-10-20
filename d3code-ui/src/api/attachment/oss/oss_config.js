import request from '@/utils/request'

// 查询存储配置列表
export function listOss_config(query) {
  return request({
    url: '/oss/oss_config/list',
    method: 'get',
    params: query
  })
}

// 查询存储配置详细
export function getOss_config(id) {
  return request({
    url: '/oss/oss_config/' + id,
    method: 'get'
  })
}

// 新增存储配置
export function addOss_config(data) {
  return request({
    url: '/oss/oss_config',
    method: 'post',
    data: data
  })
}

// 修改存储配置
export function updateOss_config(data) {
  return request({
    url: '/oss/oss_config',
    method: 'put',
    data: data
  })
}

// 删除存储配置
export function delOss_config(id) {
  return request({
    url: '/oss/oss_config/' + id,
    method: 'delete'
  })
}
