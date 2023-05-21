import request from '@/utils/request'

// 查询产品列表
export function listProduct(query) {
  return request({
    url: '/oms/product/list',
    method: 'post',
    params: query
  })
}

// 查询产品详细
export function getProduct(id) {
  return request({
    url: '/oms/product/' + id,
    method: 'get'
  })
}

// 新增产品
export function addProduct(data) {
  return request({
    url: '/oms/product/save',
    method: 'post',
    data
  })
}

// 修改产品
export function updateProduct(data) {
  return request({
    url: '/oms/product/save',
    method: 'post',
    data
  })
}

// 删除产品
export function delProduct(id) {
  return request({
    url: '/oms/product/' + id,
    method: 'delete'
  })
}
