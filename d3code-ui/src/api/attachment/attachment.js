import request from '@/utils/request'

// 查询文件管理列表
export function listAttachment(query) {
  return request({
    url: '/attachment/list',
    method: 'get',
    params: query
  })
}

export function listAttachmentByIds(ids) {
  return request({
    url: '/attachment/listByIds',
    method: 'get',
    params: { ids: ids.join(',') }
  })
}

// 查询文件管理详细
export function getAttachment(id) {
  return request({
    url: '/attachment/' + id,
    method: 'get'
  })
}

// 新增文件管理
export function addAttachment(data) {
  return request({
    url: '/attachment',
    method: 'post',
    data: data
  })
}

// 修改文件管理
export function updateAttachment(data) {
  return request({
    url: '/attachment',
    method: 'put',
    data: data
  })
}

// 删除文件管理
export function delAttachment(id) {
  return request({
    url: '/attachment/' + id,
    method: 'delete'
  })
}
