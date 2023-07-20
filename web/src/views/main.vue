<template>
  <a-layout id="components-layout-demo-top-side">
    <train-the-header-view></train-the-header-view>
    <a-layout-content style="padding: 0 50px">
      <a-layout style="padding: 24px 0; background: #fff">
        <train-the-sider-view></train-the-sider-view>
        <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
          <a-breadcrumb style="margin: 16px 0">
            <a-breadcrumb-item>Home</a-breadcrumb-item>
            <a-breadcrumb-item>List</a-breadcrumb-item>
            <a-breadcrumb-item>App</a-breadcrumb-item>
          </a-breadcrumb>
          所有会员总数：{{count}}
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
// import axios from "axios";
// import {notification} from "ant-design-vue";

export default defineComponent({
  name: "train-main-view",
  components: {TrainTheFooterView, TrainTheSiderView, TrainTheHeaderView},
  setup() {
    const count = ref(0);
    // axios.get("/member/member/count").then(response => {
    //   let data = response.data;
    //   if (data.success) {
    //     count.value = data.content;
    //   } else {
    //     notification.error({ description: data.message });
    //   }
    // });
    return {
      count
    };
  }
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
#components-layout-demo-top-side .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: #fff;
}
</style>