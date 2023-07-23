<template>
  <a-layout-sider width="200" style="background: #fff">
    <a-menu
        v-model:selectedKeys="selectedKeys"
        mode="inline"
        style="height: 100%"
    >
      <a-menu-item key="/welcome">
        <router-link to="/welcome">
          <coffee-outlined /> &nbsp;欢迎
        </router-link>
      </a-menu-item>

      <a-menu-item key="/station">
        <router-link to="/station">
          <user-outlined /> &nbsp;车站管理
        </router-link>
      </a-menu-item>

      <a-menu-item key="/train">
        <router-link to="/train">
          <user-outlined /> &nbsp;火车管理
        </router-link>
      </a-menu-item>
    </a-menu>
  </a-layout-sider>
</template>

<script>
import {defineComponent, ref, watch} from 'vue';
import router from "@/router";


export default defineComponent({
  name: "train-the-sider-view",  // 此处加入可以解决Vue页面报错的问题
  setup() {
    const selectedKeys = ref([]);

    watch(() => router.currentRoute.value.path, (newValue) => {
      console.log('watch', newValue);
      selectedKeys.value = [];
      selectedKeys.value.push(newValue);
    }, {immediate: true});

    return {
      selectedKeys
    };
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
