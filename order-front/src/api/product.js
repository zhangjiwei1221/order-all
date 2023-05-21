// 查询产品列表
import request from '@/utils/request'

export function listProduct(type) {
  return request({
    url: '/oms/product/list',
    method: 'post',
    params: {
      pageNum: 1,
      pageSize: 9999,
      type
    }
  })
}
