<template>
  <div id="app">
    <van-nav-bar
      title="查看"
      left-text="返回"
      right-text=""
      left-arrow
      @click-left="onClickLeft"
    />
    <van-sticky>
      <van-cell>
        <van-row>
          <van-col span="8">名称</van-col>
          <van-col span="8">数量</van-col>
          <van-col span="8">单位</van-col>
        </van-row>
      </van-cell>
    </van-sticky>
    <van-list v-if="productList.length">
      <van-cell v-for="(product, index) in productList" :key="index">
        <van-row>
          <van-col span="8">{{ product.name }}</van-col>
          <van-col span="8">{{ product.num }}</van-col>
          <van-col span="8">
            {{ unitMap[product.unitKey][product.unit] || product.unit }}
          </van-col>
        </van-row>
      </van-cell>
    </van-list>
    <van-empty v-else description="暂无数据"/>
    <van-button
      class="copy-btn"
      type="primary"
      round
      block
      @click="copyOrderInfo"
    >
      复制订单信息
    </van-button>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { getOrder } from '@/api/login'

export default {
  data() {
    return {
      orderInfo: {},
      productList: [],
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
      cacheOrderList: 'cacheOrderList'
    })
  },
  mounted() {
    getOrder(this.$route.query.id).then(({ data }) => {
      this.orderInfo = data
      this.orderInfo.productList = JSON.parse(data.productList)
      this.productList = this.orderInfo.productList.productList
    })
  },
  methods: {
    onClickLeft() {
      this.$router.push('/cart')
    },
    copyOrderInfo() {
      const _ = this.orderInfo
      let result = `店  名：${_.shopName}\n手机号：${_.phone}\n备  注：${
        _.message || '无'
      }\n--------------------------\n`
      for (const product of _.productList.productList) {
        const unit = this.unitMap[product.unitKey][product.unit] || product.unit
        result += `${product.name}\t${product.num}\t${unit}\n`
      }
      this.copyText(result)
    },
    copyText(text) {
      if (navigator.clipboard) {
        navigator.clipboard.writeText(text)
      } else {
        var textarea = document.createElement('textarea')
        document.body.appendChild(textarea)
        textarea.style.position = 'fixed'
        textarea.style.clip = 'rect(0 0 0 0)'
        textarea.style.top = '10px'
        textarea.value = text
        textarea.select()
        document.execCommand('copy', true)
        document.body.removeChild(textarea)
      }
      this.$toast.success('复制成功')
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

.copy-btn {
  width: 80%;
  margin: 20px auto;
}
</style>
