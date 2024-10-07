<template>
  <div class="card-top">
    <div >
      <span>文章名称：</span><a-input placeholder="请输入内容" v-model:value="queryParam.title"
        style="width: 200px; margin-right: 10px"></a-input>
      <a-button type="primary" @click="fetchData">搜索</a-button>
    </div>
  </div>
  <div class="card-container">
    <div class="container-top">
      <p>文章列表</p>
      <a-button type="primary"  @click="$router.push({ path: '/article/add' })">新增文章</a-button>
    </div>
    <div class="container-main">
      <a-table :columns="columns" :data-source="tableDatas" :rowKey="(tableDatas) => tableDatas.id" :loading="loading"
        :pagination="pagination" @change="handleTableChange">
        <template #bodyCell="{ column, record }">
          <!-- 文章封面图 -->
          <template v-if="column.key === 'coverimg'">
            <a-image :width="200" :src="record.coverimg" />
          </template>
          <!-- 是否推荐该文章 -->
          <template v-if="column.key === 'stop'">
            <span v-if="record.stop == 0"><a-tag color="#6c3ae4">不推荐</a-tag></span>
            <span v-else><a-tag color="#55acee">推荐</a-tag></span>
          </template>
          <!-- 对应的分类名称 -->
          <template v-if="column.key === 'categoryname'">
            <a-tag color="blue">{{record.categoryname}}</a-tag>
          </template>
          <!-- 操作按钮 -->
          <template v-if="column.key === 'action'">
            <span>
              <a  @click="$router.push({ path: '/article/add', query: { id: record.id } })">编辑</a>
              <a-divider type="vertical" />
              <!-- <a>删除</a> -->
              <a-popconfirm title="确定要删除该文章吗?" ok-text="确定" cancel-text="取消" @confirm="confirm(record.id)">
                <a href="#">删除</a>
              </a-popconfirm>
            </span>
          </template>
        </template>
      </a-table>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'ant-design-vue';
import { ArticleList,ArticleAdd,ArticleEdit,ArticleDel } from '../../api/modules/Article';
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
  title: undefined,
});
// 表格行名称
const columns = [
  {
    title: '文章标题',
    dataIndex: 'title',
    key: 'title',
    width: '10%',
    align:'center'
  },
  {
    title: '作者',
    dataIndex: 'author',
    key: 'author',
    align:'center'
  },
  {
    title: '封面图像',
    dataIndex: 'coverimg',
    key: 'coverimg',
    align:'center'
  },
  {
    title: '是否推荐',
    dataIndex: 'stop',
    key: 'stop',
    align:'center'
  },
  {
    title: '分类',
    dataIndex: 'categoryname',
    key: 'categoryname',
    align:'center'
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
    align:'center'
  },
];
// 表格数据
let tableDatas = ref([]);
//渲染数据
const fetchData = async () => {
  loading.value = true;
  ArticleList(queryParam).then((res) => {
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
//文章表单
const formArticle = reactive(
  {
    id: '',
    name: '',
    description: '',
  },
);
//获取到当前行的数据
const getRowData = (row) => {
  formArticle.id = row.id;

  showModal('edit');
};

//对话框开始
const dialog = reactive({
  visible: false, //打开对话框
  title: '', //标题
  disabled: true, //是否禁用
});

//打开对话框
const showModal = (item) => {
  dialog.visible = true;
  //判断是新增还是编辑
  if (item == 'edit') {
    dialog.title = '编辑文章'; //编辑

  } else {
    dialog.title = '新增文章';

  }
};
//对话框结束

//删除
const confirm = e => {
  loading.value = true;
  ArticleDel({
    id: e,
  }).then((res) => {
    if (res.code === 200) {
      message.success('删除成功');
      loading.value = false;
      fetchData();
    } else {
      message.error('删除失败');
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
