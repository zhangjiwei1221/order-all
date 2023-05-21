<template>
  <div id="app">
    <van-sticky>
      <van-cell>
        <van-row>
          <van-col span="6">名称</van-col>
          <van-col span="6">数量</van-col>
          <van-col span="6">单位</van-col>
          <van-col span="6">
            <van-icon
              name="plus"
              size="18"
              color="#1989fa"
              @click="addProduct"
            />
          </van-col>
        </van-row>
        <van-divider/>
      </van-cell>
    </van-sticky>
    <van-list v-if="productList.length">
      <van-cell v-for="(product, index) in productList" :key="index">
        <van-row>
          <van-col span="6">{{ product.name }}</van-col>
          <van-col span="6">{{ product.num }}</van-col>
          <van-col span="6">
            {{ unitMap[product.unitKey][product.unit] || product.unit }}
          </van-col>
          <van-col span="6">
            <van-icon name="edit"
                      size="20"
                      color="#1989fa"
                      @click="editProduct(product, index)"/>
            <van-icon
              name="delete-o"
              size="20"
              style="margin-left: 10px"
              color="#ee0a24"
              @click="deleteProduct(product)"
            />
          </van-col>
        </van-row>
      </van-cell>
    </van-list>
    <van-empty v-else description="暂无数据"/>
    <van-submit-bar v-if="productList.length"
                    label="合计(件)："
                    currency=""
                    :decimal-length="0"
                    :price="cartNum * 100"
                    button-text="提交订单"
                    @submit="onSubmit"/>
  </div>
</template>

<script>
import {mapState, mapMutations} from 'vuex'

export default {
  data() {
    return {
      unitMap: {
        1: {
          1: '大包',
          2: '小包',
          3: '件',
          4: '斤'
        },
        2: {
          1: '市斤',
          2: '公斤',
          3: '件'
        },
        3: {
          1: '盒',
          2: '市斤',
          3: '公斤',
          4: '件'
        }
      }
    }
  },
  computed: {
    ...mapState({
      cartNum: 'cartNum',
      productList: 'productList'
    })
  },
  methods: {
    ...mapMutations({
      removeProduct: 'removeProduct',
    }),
    addProduct() {
      this.$router.push('/cart-save')
    },
    editProduct(product, index) {
      this.$router.push({
        path: '/cart-save',
        query: {
          ...product,
          index
        }
      })
    },
    deleteProduct(product) {
      this.removeProduct(product)
    },
    onSubmit() {
      this.$router.push('/order-save')
    }
  }
}
</script>

<style scoped lang="less">
::v-deep .van-cell__value--alone {
  text-align: center;
}

::v-deep .van-submit-bar {
  bottom: 55px;
}
</style>
