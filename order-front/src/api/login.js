import request from '@/utils/request'

// 登录方法
export function login(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    url: '/login',
    method: 'post',
    data: data
  })
}

// 查询订单详细
export function getOrder(id) {
  return request({
    url: '/oms/order/' + id,
    method: 'get'
  })
}


//注册
export function register(data) {
  return request({
    url: '/register',
    method: 'post',
    data
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}
