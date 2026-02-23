import request from '@/utils/request';

// 日志-详情
export const logDetail = (params) => {
  return request({url: '/generator/log/detail', method: 'get', params: params})
}

// 日志-分页
export const logPage = (params) => {
  return request({url: '/generator/log/page', method: 'get', params: params})
}


