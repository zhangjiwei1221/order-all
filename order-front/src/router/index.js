import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

// 固定路由列表
export const constantRoutes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    component: () => import('@/views/home'),
    name: '首页'
  },
  {
    path: '/cart',
    component: () => import('@/views/cart'),
    name: '订单'
  },
  {
    path: '/cart-save',
    component: () => import('@/components/cart/add-or-update'),
    name: '保存'
  },
  {
    path: '/order-save',
    component: () => import('@/components/order/add-or-update'),
    name: '保存'
  },
  {
    path: '/order-view',
    component: () => import('@/components/order/view'),
    name: '查看'
  },
  {
    path: '/orders',
    component: () => import('@/views/orders'),
    name: '订单列表'
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    name: '登录'
  }
]

const createRouter = () =>
  new Router({
    mode: 'history',
    base: process.env.VUE_APP_PUBLIC_PATH || '',
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
  })

const router = createRouter()

export function resetRouter() {
  const { matcher } = createRouter()
  router.matcher = matcher
}

export default router
