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

// 导出订单
export function exportOrder(id) {
  return downloadFile('/oms/order/export/' + id)
}

// 删除订单
export function delOrder(id) {
  return request({
    url: '/oms/order/' + id,
    method: 'delete'
  })
}

const downloadFile = function(url) {
  return request({
    url,
    method: 'get',
    responseType: 'blob'
  }).then(data => {
    const URL = window.URL.createObjectURL(data)
    const tempLink = document.createElement('a')
    tempLink.style.display = 'none'
    tempLink.href = URL
    tempLink.setAttribute('download', '订单.xlsx')
    if (typeof tempLink.download === 'undefined') {
      tempLink.setAttribute('target', '_blank')
    }
    document.body.appendChild(tempLink)
    tempLink.click()
    document.body.removeChild(tempLink)
    window.URL.revokeObjectURL(URL)
  })
}
