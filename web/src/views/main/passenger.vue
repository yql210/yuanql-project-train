<template>
  <a-button type="primary" @click="showModal">新增</a-button>
  <a-modal v-model:visible="visible" title="乘车人" @ok="handleOk">
    <p>Some contents...</p>
    <p>Some contents...</p>
    <p>Some contents...</p>
  </a-modal>
</template>

<script>

import {defineComponent, ref} from 'vue';

export default defineComponent({
  name: "train-passenger-view",
  setup() {
    const visible = ref(false);

    const showModal = e => {
      console.log(e);
      visible.value = true;
    };

    const handleOk = e => {
      console.log(e);
      visible.value = false;
    };

    return {
      visible,
      showModal,
      handleOk
    };
  },
});
</script>
