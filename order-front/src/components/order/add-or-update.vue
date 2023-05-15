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
        v-model="name"
        name="name"
        label="店名"
        placeholder="店名"
        :rules="[{ required: true, message: '请填写店名' }]"
      />
      <van-field
        v-model="tel"
        type="tel"
        label="手机号"
        placeholder="手机号"
        :rules="[{ required: true, message: '请填写手机号' }]"
      />
      <van-field
        v-model="message"
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

export default {
  data() {
    return {
      name: '',
      tel: '',
      message: ''
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
      this.addOrder({
        name: this.name,
        tel: this.tel,
        message: this.message,
        time: new Date().toLocaleString('zh-CN')
      })
      this.$router.push('/cart')
    }
  }
}
</script>

<style lang="less"></style>
