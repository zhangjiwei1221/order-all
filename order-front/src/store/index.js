import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const storage = window.localStorage
const username = storage.getItem('username')
const orderList = storage.getItem(`orderList${username}`)

const store = new Vuex.Store({
  state: {
    username: username || '',
    productList: [],
    cartNum: 0,
    cacheVegetableList: [],
    cacheSoyList: [],
    cacheOrderList: JSON.parse(orderList) || []
  },
  mutations: {
    setUsername(state, username) {
      state.username = username
      storage.setItem('username', username)
    },
    addProduct(state, product) {
      state.productList.push(product)
      state.cartNum += 1
    },
    removeProduct(state, product) {
      let index = -1
      let id = product.id
      for (let i = 0; i < state.productList.length; i++) {
        if (id) {
          if (state.productList[i].id === product.id) {
            index = i
            break
          }
        } else {
          if (state.productList[i].name === product.name) {
            index = i
            break
          }
        }
      }
      if (index !== -1) {
        state.productList.splice(index, 1)
        state.cartNum -= 1
        if (id <= state.cacheVegetableList.length) {
          let tmpIndex = -1
          for (let i = 0; i < state.cacheVegetableList.length; i++) {
            if (state.cacheVegetableList[i].id === id) {
              tmpIndex = i
              break
            }
          }
          if (tmpIndex !== -1) {
            if (state.cacheVegetableList[tmpIndex].count === 1) {
              state.cacheVegetableList.splice(tmpIndex, 1)
            } else {
              state.cacheVegetableList[tmpIndex].count -= 1
            }
          }
        } else {
          let tmpIndex = -1
          for (let i = 0; i < state.cacheSoyList.length; i++) {
            if (state.cacheSoyList[i].id === id) {
              tmpIndex = i
              break
            }
          }
          if (tmpIndex !== -1) {
            if (state.cacheSoyList[tmpIndex].count === 1) {
              state.cacheSoyList.splice(tmpIndex, 1)
            } else {
              state.cacheSoyList[tmpIndex].count -= 1
            }
          }
        }
      }
    },
    setCacheVegetableList(state, list) {
      state.cacheVegetableList = list
    },
    setCacheSoyList(state, list) {
      state.cacheSoyList = list
    },
    changeCartNum(state, num) {
      state.cartNum += num
    },
    addOrder(state) {
      state.productList = []
      state.cartNum = 0
      state.cacheVegetableList = []
      state.cacheSoyList = []
    },
    editOrder(state, order) {
      const productList = order.productList
      state.productList = productList.productList
      state.cartNum = productList.cartNum
      state.cacheVegetableList = productList.cacheVegetableList
      state.cacheSoyList = productList.cacheSoyList
    }
  }
})

export default store
