import request from '@/utils/request';

// 代码模板-详情
export const templateDetail = (params) => {
  return request({url: '/generator/template/detail', method: 'get', params: params})
}

// 代码模板-新增
export const templateCreate = (params) => {
  return request({url: '/generator/template/create', method: 'post', data: params})
}

// 代码模板-分页
export const templatePage = (params) => {
  return request({url: '/generator/template/page', method: 'get', params: params})
}

// 代码模板-选项
export const templateOptions = (params) => {
  return request({url: '/generator/template/options', method: 'get', params: params})
}

// 代码模板-删除
export const templateRemove = (params) => {
  return request({url: '/generator/template/remove', method: 'post', data: params})
}

// 代码模板-修改
export const templateUpdate = (params) => {
  return request({url: '/generator/template/update', method: 'post', data: params})
}

