<template>
  <div id="app">
    <van-nav-bar
      :title="title"
      left-text="返回"
      right-text=""
      left-arrow
      @click-left="onClickLeft"
    />
    <van-form @submit="onSubmit">
      <van-field
        v-model="name"
        name="name"
        label="名称"
        placeholder="名称"
        :rules="[{ required: true, message: '请填写名称' }]"
      />
      <van-field
        v-model="num"
        type="number"
        name="num"
        label="数量"
        placeholder="数量"
        :rules="[{ required: true, message: '请填写数量' }]"
      />
      <van-field
        v-model="unit"
        name="unit"
        label="单位"
        placeholder="单位"
        :rules="[{ required: true, message: '请填写单位' }]"
      />
      <div style="margin: 16px">
        <van-button round block type="info" native-type="submit"
          >提交</van-button
        >
      </div>
    </van-form>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'

export default {
  data() {
    return {
      id: '',
      name: '',
      num: '',
      unit: '',
      count: '',
      index: -1,
      title: '添加'
    }
  },
  mounted() {
    const params = this.$route.query
    if (params.name) {
      this.title = '修改'
      this.id = params.id
      this.count = params.count
      this.name = params.name
      this.num = params.num
      this.unit = params.unit
      this.index = params.index
    }
  },
  methods: {
    ...mapMutations({
      addProduct: 'addProduct',
      removeProduct: 'removeProduct'
    }),
    onClickLeft() {
      this.$router.push('/cart')
    },
    onSubmit(values) {
      if (this.index !== -1) {
        this.removeProduct({
          id: Number(this.id),
          count: this.count
        })
      }
      this.addProduct({
        ...values,
        unitKey: '1'
      })
      this.$router.push('/cart')
    }
  }
}
</script>

<style lang="less"></style>
