import request from '@/utils/request'

export function getProcessDefinitionList(query) {
  return request({
    url: '/bpm/process-definition/list',
    method: 'get',
    params: query
  })
}

export function getProcessDefinitionBpmnXML(id) {
  return request({
    url: '/bpm/process-definition/get-bpmn-xml?id=' + id,
    method: 'get'
  })
}
