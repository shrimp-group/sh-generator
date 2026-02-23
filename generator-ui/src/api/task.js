import request from '@/utils/request';

// 任务-分页
export const taskList = (params) => {
  return request({url: '/generator/task/list', method: 'get', params: params})
}

// 任务-保存
export const taskSave = (params) => {
  return request({url: '/generator/task/save', method: 'post', data: params})
}

