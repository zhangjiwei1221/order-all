<template>
  <div id="app">
    <van-nav-bar
      title="添加"
      left-text="返回"
      right-text=""
      left-arrow
      @click-left="onClickLeft"
    />
    <van-form @submit="onSubmit">
      <van-field
        v-model="shopName"
        name="shopName"
        label="店名"
        placeholder="店名"
        :rules="[{ required: true, message: '请填写店名' }]"
      />
      <van-field
        v-model="phone"
        type="tel"
        label="手机号"
        placeholder="手机号"
        :rules="[{ required: true, message: '请填写手机号' }]"
      />
      <van-field
        v-model="remark"
        rows="2"
        autosize
        label="备注"
        type="textarea"
        maxlength="100"
        placeholder="备注"
        show-word-limit
      />
      <div style="margin: 16px">
        <van-button round block type="info" native-type="submit">提交</van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import { addOrder } from '@/api/order'

export default {
  data() {
    return {
      shopName: '',
      phone: '',
      remark: ''
    }
  },
  methods: {
    ...mapMutations({
      addOrder: 'addOrder'
    }),
    onClickLeft() {
      this.$router.push('/cart')
    },
    onSubmit() {
      const _ = this.$store.state
      addOrder({
        shopName: this.shopName,
        phone: this.phone,
        remark: this.remark,
        createTime: new Date().toLocaleString('zh-CN'),
        productList: JSON.stringify({
          productList: _.productList,
          cartNum: _.cartNum,
          cacheVegetableList: _.cacheVegetableList,
          cacheSoyList: _.cacheSoyList
        })
      }).then(() => this.addOrder())
      this.$router.push('/cart')
    }
  }
}
</script>

<style lang="less"></style>
