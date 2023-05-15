import Vue from 'vue'
import Vant from 'vant'
import App from './App.vue'
import 'vant/lib/index.css'
import store from './store'
import router from './router'

Vue.use(Vant)

Vue.config.productionTip = false

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
