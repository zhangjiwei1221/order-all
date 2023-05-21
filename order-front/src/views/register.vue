<template>
  <div id="app">
    <van-nav-bar
      title="注册"
      left-text="返回"
      right-text=""
      left-arrow
      @click-left="onClickLeft"
    />
    <van-form @submit="onSubmit">
      <van-field
        v-model="username"
        name="username"
        label="用户名"
        placeholder="用户名"
        :rules="[{ required: true, message: '请填写用户名' }]"
      />
      <van-field
        v-model="password"
        type="password"
        name="password"
        label="密码"
        placeholder="密码"
        :rules="[{ required: true, message: '请填写密码' }]"
      />
      <van-field
        v-model="confirmPassword"
        type="password"
        name="confirmPassword"
        label="确认密码"
        placeholder="确认密码"
        :rules="[{ required: true, message: '请填写确认密码' }]"
      />
      <div style="margin: 16px">
        <van-button round block type="info" native-type="submit"
        >提交
        </van-button
        >
      </div>
    </van-form>
  </div>
</template>

<script>
import { register } from '@/api/login'

export default {
  data() {
    return {
      username: '',
      password: '',
      confirmPassword: ''
    }
  },
  methods: {
    onClickLeft() {
      this.$router.push('/login')
    },
    onSubmit() {
      if (this.password !== this.confirmPassword) {
        this.$toast.fail('两次密码输入不一致')
        return
      }
      register({
        username: this.username,
        password: this.password
      }).then(({ code, msg }) => {
        if (code !== 200) {
          this.$toast.fail(msg)
        } else {
          this.$router.push('/login')
        }
      })
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
