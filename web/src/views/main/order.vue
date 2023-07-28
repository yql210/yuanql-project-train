<template>
  <div> {{dailyTrainTicket}}</div>
</template>

<script>

import {defineComponent} from 'vue';


export default defineComponent({
  name: "train-order-view",
  setup() {

    const dailyTrainTicket = SessionStorage.get(SESSION_ORDER) || {};
    console.log("下单的车次信息", dailyTrainTicket);

    return {
      dailyTrainTicket,
    };
  },
});
</script>
