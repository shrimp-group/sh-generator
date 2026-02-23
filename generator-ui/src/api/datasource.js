import request from '@/utils/request';

// 数据源-新增
export const datasourceCreate = (params) => {
  return request({url: '/generator/datasource/create', method: 'post', data: params})
}

// 数据源-分页
export const datasourcePage = (params) => {
  return request({url: '/generator/datasource/page', method: 'get', params: params})
}

// 数据源-修改
export const datasourceUpdate = (params) => {
  return request({url: '/generator/datasource/update', method: 'post', data: params})
}

// 数据源-详情
export const datasourceDetail = (params) => {
  return request({url: '/generator/datasource/detail', method: 'get', params: params})
}

// 数据源-删除
export const datasourceRemove = (params) => {
  return request({url: '/generator/datasource/remove', method: 'post', data: params})
}

// 数据源-选项
export const datasourceOptions = (params) => {
  return request({url: '/generator/datasource/options', method: 'get', params: params})
}

