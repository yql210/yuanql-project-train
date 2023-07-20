<template>
  <a-layout-sider width="200" style="background: #fff">
    <a-menu
        mode="inline"
        style="height: 100%"
    >
      <a-menu-item key="/welcome">
        <router-link to="/welcome">
          <coffee-outlined /> &nbsp;欢迎
        </router-link>
      </a-menu-item>

      <a-menu-item key="/passenger">
        <router-link to="/passenger">
          <coffee-outlined /> &nbsp;乘车人管理
        </router-link>
      </a-menu-item>
    </a-menu>
  </a-layout-sider>
</template>

<script>
import {defineComponent, ref} from 'vue';


export default defineComponent({
  name: "train-the-sider-view",  // 此处加入可以解决Vue页面报错的问题
  setup() {

    return {
      selectedKeys2: ref(['1']),
      openKeys: ref(['sub1']),
    };
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
