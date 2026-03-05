import request from '@/utils/request';

// 项目-删除
export const projectRemove = (params) => {
  return request({url: '/generator/project/remove', method: 'post', data: params})
}

// 项目-详情
export const projectDetail = (params) => {
  return request({url: '/generator/project/detail', method: 'get', params: params})
}

// 项目-分页
export const projectPage = (params) => {
  return request({url: '/generator/project/page', method: 'get', params: params})
}

// 项目-新增
export const projectCreate = (params) => {
  return request({url: '/generator/project/create', method: 'post', data: params})
}

// 项目-修改
export const projectUpdate = (params) => {
  return request({url: '/generator/project/update', method: 'post', data: params})
}

// 项目-复制
export const projectCopy = (params) => {
  return request({url: '/generator/project/copy', method: 'post', data: params})
}


