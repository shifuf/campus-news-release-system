<template>
  <div class="card-top">
    <div class="">
      <span>分类名称：</span><a-input placeholder="请输入内容" v-model:value="queryParam.name"
        style="width: 200px; margin-right: 10px"></a-input>
      <a-button type="primary" @click="fetchData">搜索</a-button>
    </div>
  </div>
  <div class="card-container">
    <div class="container-top">
      <p>分类列表</p>
      <a-button type="primary" @click="showModal()">新增分类</a-button>
    </div>
    <div class="container-main">
      <a-table :columns="columns" :data-source="tableDatas" :rowKey="(tableDatas) => tableDatas.id" :loading="loading"
        :pagination="pagination" @change="handleTableChange">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key == 'name'">
            <a-tag color="#2db7f5">{{ record.name }}</a-tag>
          </template>
          <template v-if="column.key == 'articlecount'">
            <a-tag color="blue">{{ record.articlecount }}</a-tag>
          </template>
          <template v-if="column.key === 'action'">
            <span>
              <a @click="getRowData(record)">编辑</a>
              <a-divider type="vertical" />
              <!-- <a>删除</a> -->
              <a-popconfirm title="确定要删除该分类吗?" ok-text="确定" cancel-text="取消" @confirm="confirm(record.id)">
                <a href="#">删除</a>
              </a-popconfirm>
            </span>
          </template>
        </template>
      </a-table>
    </div>
  </div>
  <Moadl :open="dialog.visible" :title="dialog.title" @onClose="onClose" @onConfirm="onConfirm" :height="60">
    <template #content>
      <a-form :model="formCategory" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-form-item label="分类名称">
          <a-input v-model:value="formCategory.name" />
        </a-form-item>
      </a-form>
    </template>
  </Moadl>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'ant-design-vue';
import Moadl from '../../components/Moadl.vue';
import { CategoryList, CategoryAdd, CategoryEdit, CategoryDel } from '../../api/modules/category';
//对话框
//分页参数
const pagination = reactive({
  total: 0,
  pageSize: 5, // 每页中显示 5 条数据
  current: 1, // 当前页数
  showSizeChanger: true, // 是否可以改变每页的数据
  pageSizeOptions: ['1', '5', '10', '20'], // 每页中显示的数据选项
  showTotal: (total) => `共有 ${total} 条数据`, // 分页中显示总的数据
});
//加载状态
let loading = ref(false);
//查询参数
const queryParam = reactive({
  pageSize: 5,
  pageNum: 1,
  name: undefined,
});
// 表格行数据
const columns = [
  {
    title: '分类编号',
    dataIndex: 'id',
    key: 'id',
    align: 'center'
  },
  {
    title: '分类名称',
    dataIndex: 'name',
    key: 'name',
    align: 'center'
  },
  {
    title: '文章数量',
    dataIndex: 'articlecount',
    key: 'articlecount',
    align: 'center'
  },
  {
    title: '创建时间',
    dataIndex: 'createtime',
    key: 'createtime',
    align: 'center'
  },
  {
    title: '更新时间',
    dataIndex: 'updatetime',
    key: 'updatetime',
    align: 'center'
  },
  {
    title: '操作',
    key: 'action',
    align: 'center'
  },
];
// 表格数据
let tableDatas = ref([]);
//渲染数据
const fetchData = async () => {
  loading.value = true;
  CategoryList(queryParam).then((res) => {
    if (res.code === 200) {
      tableDatas.value = res.data.rows;
      pagination.total = res.data.total;
    }
    loading.value = false;
  });
};
//分页点击后重新请求数据
const handleTableChange = (items) => {
  pagination.current = items.current;
  pagination.pageSize = items.pageSize;
  queryParam.pageNum = items.current;
  queryParam.pageSize = items.pageSize;
  fetchData();
};
//分类表单
const formCategory = reactive(
  {
    id: '',
    name: '',
    description: '',
  },
);
//获取到当前行的数据
const getRowData = (row) => {
  formCategory.id = row.id;
  formCategory.name = row.name;
  formCategory.description = row.description;

  console.log(formCategory.id);
  showModal('edit');
};

//对话框开始
const dialog = reactive({
  visible: false, //打开对话框
  title: '', //标题
  disabled: true, //是否禁用
});
//编辑还是新增
let typeEdit = '';
//打开对话框
const showModal = (item) => {
  dialog.visible = true;
  //判断是新增还是编辑
  if (item == 'edit') {
    dialog.title = '编辑分类'; //编辑
    dialog.disabled = false;
    //编辑还是新增
    typeEdit = 'edit';
    //编辑
  } else {
    dialog.title = '新增分类';
    dialog.disabled = true;
    typeEdit = 'add';
    //新增
    formCategory.id = '';
    formCategory.name = '';
    formCategory.description = '';
  }
};

//关闭对话框
const onClose = () => {
  dialog.visible = false;
};
//确认对话框
const onConfirm = () => {
  dialog.visible = false;
  if (typeEdit == 'edit') {
    console.log("编辑");
    CategoryEdit(formCategory).then((res) => {
      if (res.code === 200) {
        message.success('编辑成功');
        fetchData();
      } else {
        message.error('编辑失败');
      }
    });

  } else if (typeEdit == 'add') {
    console.log("新增");
    CategoryAdd(formCategory).then((res) => {
      if (res.code === 200) {
        message.success('新增成功');
        fetchData();
      } else {
        message.error('新增失败');
      }
    });
  }
};
const labelCol = {
  style: {
    width: '80px',
  },
};
const wrapperCol = {
  span: 50,
};

//对话框结束

//删除
const confirm = e => {
  loading.value = true;
  CategoryDel({
    id: e,
  }).then((res) => {
    if (res.code === 200) {
      message.success('删除成功');
      loading.value = false;
      fetchData();
    } else {
      message.error(res.msg);
      loading.value = false;
    }
  });

};
onMounted(() => {
  fetchData();
});
</script>
<style lang="scss" scoped>
:deep(.ant-table-wrapper .ant-table-pagination.ant-pagination) {
  margin-right: 20px;
}

.card-top {
  width: 100%;
  height: 50px;
  background-color: #fff;
  border-radius: 5px;
  display: flex;
  line-height: 50px;
  padding-left: 15px;
}

.card-container {
  width: 100%;
  height: 100%;
  background-color: #fff;
  border-radius: 5px;
  margin-top: 10px;

  .container-top {
    width: 100%;
    height: 60px;
    padding: 1rem;
    line-height: 60px;
    display: flex;
    align-items: center;
    justify-content: space-between;

    p {
      font-weight: 700;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
}
</style>
