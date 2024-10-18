/**
 * @classdesc 字典数据
 * @property {String} label 标签
 * @property {*} value 标签
 * @property {Object} raw 原始数据
 * @property {Array} children 子数据
 */
export default class DictData {
  constructor(label, value, raw, children) {
    this.label = label
    this.value = value
    this.raw = raw
    this.children = children
  }
}
