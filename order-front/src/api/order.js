// 查询产品列表
import request from '@/utils/request'
import { getToken } from '@/utils/auth'

// 查询订单列表
export function listOrder() {
  return request({
    url: '/oms/order/list',
    method: 'post',
    params: {
      pageNum: 1,
      pageSize: 9999,
      token: getToken()
    }
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
