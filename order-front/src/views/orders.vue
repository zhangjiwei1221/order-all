<template>
  <div id="app">
    <van-sticky>
      <van-cell>
        <van-row>
          <van-col span="24">已提交订单列表</van-col>
        </van-row>
        <van-divider/>
      </van-cell>
    </van-sticky>
    <van-list v-if="cacheOrderList.length">
      <van-cell v-for="(order, index) in cacheOrderList" :key="index">
        <van-row>
          <van-col span="8">{{ order.shopName }}</van-col>
          <van-col span="12">{{ order.createTime }}</van-col>
          <van-col span="4">
            <van-icon
              name="eye-o"
              size="20"
              color="#1989fa"
              @click="viewOrder(order)"
            />
            <van-icon
              name="edit"
              size="20"
              style="margin-left: 10px"
              color="#1989fa"
              @click="edit(order)"
            />
          </van-col>
        </van-row>
      </van-cell>
    </van-list>
    <van-empty v-else description="暂无数据"/>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import { listOrder } from '@/api/order'

export default {
  data() {
    return {
      cacheOrderList: []
    }
  },
  mounted() {
    listOrder().then(({ rows }) => {
      rows.forEach(item => item.productList = JSON.parse(item.productList))
      this.cacheOrderList = rows
    })
  },
  methods: {
    ...mapMutations({
      editOrder: 'editOrder'
    }),
    viewOrder(order) {
      this.$router.push({
        path: 'order-view',
        query: {
          id: order.id
        }
      })
    },
    edit(order) {
      this.editOrder(order)
      this.$router.push('/cart')
    }
  }
}
</script>

<style scoped lang="less">
::v-deep .van-cell__value--alone {
  text-align: center;
}
</style>
