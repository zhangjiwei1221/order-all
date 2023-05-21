<template>
  <div id="app">
    <router-view/>
    <van-tabbar v-if="login && !$route.path.includes('-')" route>
      <van-tabbar-item icon="home-o" replace to="/home">首页</van-tabbar-item>
      <van-tabbar-item
        v-if="cartNum"
        :badge="cartNum"
        icon="cart-o"
        replace
        to="/cart">订单
      </van-tabbar-item>
      <van-tabbar-item v-else icon="cart-o" replace to="/cart">订单</van-tabbar-item>
      <van-tabbar-item icon="orders-o" replace to="/orders">订单列表</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import {getToken} from '@/utils/auth'

export default {
  data() {
    return {
      login: false,
      token: ''
    }
  },
  updated() {
    this.login = !!this.username
    this.token = getToken()
    if ((!this.login || !this.token) && this.$route.path !== '/register') {
      this.$router.push('/login')
    }
  },
  computed: {
    ...mapState({
      cartNum: 'cartNum',
      username: 'username'
    })
  }
}
</script>

<style lang="less">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
