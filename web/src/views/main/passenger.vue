<template>
  <p>
    <a-button type="primary" @click="showModal">新增</a-button>
  </p>
  <a-table :data-source="passengers" :columns="colums" :pagination="pagination"/>
  <a-modal v-model:visible="visible" title="乘车人" @ok="handleOk"
      ok-text="确认" cancel-text="取消">
    <a-form :model="passenger" :label-col="{span: 4}" :wrapper-col="{ span: 20 }">
      <a-form-item label="姓名">
        <a-input v-model:value="passenger.name" />
      </a-form-item>
      <a-form-item label="身份证">
        <a-input v-model:value="passenger.idCard" />
      </a-form-item>
      <a-form-item label="旅客类型">
        <a-select v-model:value="passenger.type">
          <a-select-option value="1">成人</a-select-option>
          <a-select-option value="2">儿童</a-select-option>
          <a-select-option value="3">学生</a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>

import {defineComponent, onMounted, reactive, ref} from 'vue';
import axios from "axios";
import {notification} from "ant-design-vue";

export default defineComponent({
  name: "train-passenger-view",
  setup() {
    const visible = ref(false);
    const passenger = reactive({
      id: undefined,
      memberID: undefined,
      name: undefined,
      idCard: undefined,
      type: undefined,
      createTime: undefined,
      updateTime: undefined,
    });

    const passengers = ref([]);

    // 分页的三个属性名是固定的
    const pagination = reactive({
      total: 0,
      current: 1,
      pageSize: 2,
    });

    const colums = [ {
      title: '姓名',
      dataIndex: 'name',
      key: 'name',
    },
      {
        title: '年龄',
        dataIndex: 'idCard',
        key: 'idCard',
      },
      {
        title: '类型',
        dataIndex: 'type',
        key: 'type',
      },];

    const showModal = () => {
      visible.value = true;
    };

    const handleOk = () => {
      axios.post("/member/passenger/save", passenger).then((response) => {
        let data = response.data;
        if (data.success) {
          notification.success({description: "保存成功！"});
          visible.value = false;
        } else {
          notification.error({description: data.message});
        }
      });
    };

    const handleQuery = (param) => {
      axios.get("/member/passenger/query-list", {
        params: {
          page: param.page,
          size: param.size
        }
      }).then((response) => {
        let data = response.data;
        if (data.success) {
          passengers.value = data.content.list;
          pagination.total = data.content.total;
        } else {
          notification.error({description: data.message});
        }
      });
    };

    onMounted(() => {
      handleQuery({
        page: 1,
        size: 2
      });
    });

    return {
      passenger,
      visible,
      showModal,
      handleOk,
      passengers,
      colums,
      pagination,
    };
  },
});
</script>
