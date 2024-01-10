import DictOptions from './DictOptions'
import DictData from './DictData'

export default function(dict, dictMeta) {
  return arrange(dict, dictMeta)
}

/**
 * 确定字典字段
 * @param {DictData} dict
 * @param  {...String} fields
 */
function determineDictField(dict, ...fields) {
  return fields.find(f => Object.prototype.hasOwnProperty.call(dict, f))
}

function arrange(dict, dictMeta) {
  const label = determineDictField(dict, dictMeta.labelField, ...DictOptions.DEFAULT_LABEL_FIELDS)
  const value = determineDictField(dict, dictMeta.valueField, ...DictOptions.DEFAULT_VALUE_FIELDS)
  let children = null
  if(dict.children){
    children = dict.children.map(item => arrange(item, dictMeta))
  }
  return new DictData(dict[label], dict[value], dict, children)
}
