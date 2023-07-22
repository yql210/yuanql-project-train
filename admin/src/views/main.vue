<template>
  <a-layout id="components-layout-demo-top-side">
    <train-the-header-view></train-the-header-view>
    <a-layout-content style="padding: 0 50px">
      <a-breadcrumb style="margin: 16px 0">
        <a-breadcrumb-item>Home</a-breadcrumb-item>
        <a-breadcrumb-item>List</a-breadcrumb-item>
        <a-breadcrumb-item>App</a-breadcrumb-item>
      </a-breadcrumb>
      <a-layout style="padding: 24px 0; background: #fff">
        <train-the-sider-view></train-the-sider-view>
        <a-layout-content
            :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
        >
          <router-view></router-view>
        </a-layout-content>
      </a-layout>
    </a-layout-content>
    <train-the-footer-view></train-the-footer-view>
  </a-layout>
</template>

<script>
import {defineComponent, ref} from "vue";
import TrainTheHeaderView from "@/components/the-header.vue";
import TrainTheSiderView from "@/components/the-sider.vue";
import TrainTheFooterView from "@/components/the-footer.vue";

export default defineComponent({
  name: "train-main-view",
  components: {TrainTheFooterView, TrainTheSiderView, TrainTheHeaderView},
  setup() {
    const count = ref(0);
    return {
      count
    };
  }
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>

</style>