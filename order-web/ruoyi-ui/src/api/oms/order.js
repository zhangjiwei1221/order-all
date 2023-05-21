import request from '@/utils/request'

// 查询订单列表
export function listOrder(query) {
  return request({
    url: '/oms/order/list',
    method: 'post',
    params: query
  })
}

// 查询订单详细
export function getOrder(id) {
  return request({
    url: '/oms/order/' + id,
    method: 'get'
  })
}

// 新增订单
export function addOrder(data) {
  return request({
    url: '/oms/order/save',
    method: 'post',
    data
  })
}

// 修改订单
export function updateOrder(data) {
  return request({
    url: '/oms/order/save',
    method: 'post',
    data
  })
}

// 删除订单
export function delOrder(id) {
  return request({
    url: '/oms/order/' + id,
    method: 'delete'
  })
}
