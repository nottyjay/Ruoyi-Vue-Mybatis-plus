import request from '@/utils/request'

// 查询存储桶列表
export function listBucket(query) {
  return request({
    url: '/bucket/bucket/list',
    method: 'get',
    params: query
  })
}

// 查询存储桶详细
export function getBucket(id) {
  return request({
    url: '/bucket/bucket/' + id,
    method: 'get'
  })
}

// 新增存储桶
export function addBucket(data) {
  return request({
    url: '/bucket/bucket',
    method: 'post',
    data: data
  })
}

// 修改存储桶
export function updateBucket(data) {
  return request({
    url: '/bucket/bucket',
    method: 'put',
    data: data
  })
}

// 删除存储桶
export function delBucket(id) {
  return request({
    url: '/bucket/bucket/' + id,
    method: 'delete'
  })
}
