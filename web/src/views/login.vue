<template>
  <a-row class="login">
    <a-col :span="8" :offset="8" class="login-main">
      <h1 style="text-align: center"><rocket-two-tone />&nbsp;YuanQL 12306售票系统</h1>
      <a-form
          :model="loginForm"
          name="basic"
          autocomplete="off"
      >
        <a-form-item
            label=""
            name="mobile"
            :rules="[{ required: true, message: '请输入手机号!' }]"
        >
          <a-input v-model:value="loginForm.mobile" placeholder="手机号"/>
        </a-form-item>

        <a-form-item
            label=""
            name="code"
            :rules="[{ required: true, message: '请输入验证码!' }]"
        >
          <a-input v-model:value="loginForm.code">
            <template #addonAfter>
              <a @click="sendCode">获取验证码</a>
            </template>a
          </a-input>
          <!--<a-input v-model:value="loginForm.code" placeholder="验证码"/>-->
        </a-form-item>

        <a-form-item>
          <a-button type="primary" block @click="login">登录</a-button>
        </a-form-item>

      </a-form>
    </a-col>
  </a-row>

</template>


<script>
import { defineComponent, reactive } from 'vue';
import axios from "axios";
import {notification} from "ant-design-vue";

export default defineComponent({
  name: "train-login-view",  // 此处加入可以解决Vue页面报错的问题
  setup() {
    const loginForm = reactive({
      mobile: '13000000000',
      code: '',
    });

    const sendCode = () => {
      axios.post("http://localhost:8000/member/member/send_code", {
        mobile: loginForm.mobile
      }).then(response => {
        let data = response.data;

        if (data.success) {
          notification.success({description: '发送验证码成功！！！'});
          loginForm.code = "8888";
        } else {
          notification.error({description: data.message});
        }
      })
    };

    const login = () => {
      axios.post("http://localhost:8000/member/member/login", loginForm).then(response => {
        let data = response.data;
        if (data.success) {
          notification.success({description: '登录成功！！！' });
          console.log("登录成功：", data.content);
        } else {
          notification.error({ description: data.message });
        }
      })
    };

    return {
      loginForm,
      login,
      sendCode,
    };
  },
});
</script>

<style>
.login-main h1 {
  font-size: 25px;
  font-weight: bold;
}
.login-main {
  margin-top: 100px;
  padding: 30px 30px 20px;
  border: 2px solid grey;
  border-radius: 10px;
  background-color: #fcfcfc;
}
</style>