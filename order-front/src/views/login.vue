<template>
  <div class="login-container">
    <img class="logo" :src="require('../assets/logo.png')" alt="logo"/>
    <van-form ref="form" class="login-form">
      <van-cell-group>
        <van-field
          v-model="form.username"
          label="用户名"
          placeholder="用户名"
          clearable
          :rules="[{ required: true, message: '请输入用户名' }]"
        />
        <van-field
          v-model="form.password"
          label="密码"
          type="password"
          placeholder="密码"
          clearable
          :rules="[{ required: true, message: '请输入密码' }]"
        />
      </van-cell-group>
    </van-form>
    <van-button
      class="login-btn"
      type="primary"
      round
      block
      @click="handleLogin"
    >登录
    </van-button>
    <div class="register"
         @click="register">立即注册</div>
    <div class="copy-right">© 2023 苏州苏豫供应链服务有限公司</div>
  </div>
</template>

<script>
import {mapMutations} from 'vuex'
import {login} from '@/api/login'
import {setToken} from '@/utils/auth'

export default {
  data() {
    return {
      form: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    ...mapMutations({
      setUsername: 'setUsername'
    }),
    handleLogin() {
      this.$refs.form.validate().then(() => {
        login(this.form.username, this.form.password).then(({token}) => {
          if (!token) {
            this.$toast.fail('用户名或密码错误')
            return
          }
          setToken(token)
          this.setUsername(this.form.username)
          this.$router.push('/home')
        })
      })
    },
    register() {
      this.$router.push('/register')
    }
  }
}
</script>

<style scoped lang="less">
.login-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh;
  background-color: #fff;
}

.logo {
  width: 100px;
  height: 100px;
  margin-bottom: 30px;
}

.login-form {
  width: 80%;
  margin: 0 auto;
}

.login-btn {
  width: 80%;
  margin-top: 20px;
}

.register {
  cursor: pointer;
  color: #1989fa;
  font-size: 14px;
  margin: 10px 15%;
  align-self: flex-end;
}

.copy-right {
  position: fixed;
  bottom: 20px;
  color: #999;
  font-size: 14px;
  text-align: center;
  width: 100%;
}
</style>
