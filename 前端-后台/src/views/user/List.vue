<template>
  <div class="card-top">
    <div class="">
      <span>用户名称：</span><a-input placeholder="请输入内容" v-model:value="queryParam.username"
        style="width: 200px; margin-right: 10px"></a-input>
      <a-button type="primary" @click="fetchData">搜索</a-button>
    </div>
  </div>
  <div class="card-container">
    <div class="container-top">
      <p>用户列表</p>
      <a-button type="primary" @click="showModal">新增用户</a-button>
    </div>
    <!-- 用户数据 -->
    <div class="container-main">
      <a-table :columns="columns" :data-source="tableDatas" :rowKey="(tableDatas) => tableDatas.id" :loading="loading"
        :pagination="pagination" @change="handleTableChange">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'avatar'">
            <a-image :width="200" :src="record.avatar" />
          </template>
          <template v-if="column.key === 'type'">
            <span v-if="record.type == 0"><a-tag color="#cd201f">管理员</a-tag></span>
            <span v-else><a-tag color="#55acee">普通用户</a-tag></span>
          </template>
          <template v-if="column.key === 'action'">
            <span>
              <a @click="getRowData(record)">编辑</a>
              <a-divider type="vertical" />
              <!-- <a>删除</a> -->
              <a-popconfirm title="确定要删除该用户吗?" ok-text="确定" cancel-text="取消" @confirm="confirm(record.id)">
                <a href="#">删除</a>
              </a-popconfirm>
            </span>
          </template>
        </template>
      </a-table>
    </div>
  </div>
  <!-- 对话框-新增按钮 -->
  <Moadl :open="dialog.visible" :title="dialog.title" @onClose="onClose" @onConfirm="onConfirm">
    <template #content>
      <a-form :model="formUser" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-form-item label="用户ID" v-if="!dialog.disabled">
          <a-input v-model:value="formUser.id" :disabled="true" />
        </a-form-item>
        <a-form-item label="用户名">
          <a-input v-model:value="formUser.username" />
        </a-form-item>
        <a-form-item label="昵称">
          <a-input v-model:value="formUser.nickname" />
        </a-form-item>
        <a-form-item label="头像" style="align-items: center">
          <a-input style="display: none" v-model:value="formUser.avatar" />
          <!-- 图片上传 -->
          <div class="clearfix">
            <a-upload v-model:file-list="fileList" action="/api/file/upload" list-type="picture-card"
              @preview="handlePreview" @remove="remove" @change="handleChange">
              <div v-if="fileList.length < 1">
                <plus-outlined />
                <div style="margin-top: 8px">上传图片</div>
              </div>
            </a-upload>
            <!-- 图片预览 -->
            <a-modal :open="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
              <img alt="example" style="width: 100%" :src="previewImage" />
            </a-modal>
          </div>
        </a-form-item>
        <a-form-item label="用户类型">
          <!-- <a-input v-model:value="formUser.type" /> -->
          <a-select v-model:value="usertype" label-in-value style="width: 120px" :options="options"
            @change="typeChange"></a-select>
        </a-form-item>
      </a-form>
    </template>
  </Moadl>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import { UserAdd, UserDel, UserEdit, UserList } from '../../api/modules/user';
import { message } from 'ant-design-vue';
//对话框
import Moadl from '../../components/Moadl.vue';
//分页参数
const pagination = reactive({
  total: 0,
  pageSize: 10, // 每页中显示 5 条数据
  current: 1, // 当前页数
  showSizeChanger: true, // 是否可以改变每页的数据
  pageSizeOptions: ['1', '5', '10', '20'], // 每页中显示的数据选项
  showTotal: (total) => `共有 ${total} 条数据`, // 分页中显示总的数据
});
//加载状态
let loading = ref(false);
//查询参数
const queryParam = reactive({
  pageSize: 10,
  pageNum: 1,
  username: undefined,
});
// 表格行数据
const columns = [
  {
    title: '用户名',
    dataIndex: 'username',
    key: 'username',
    align:'center'
  },
  {
    title: '用户昵称',
    dataIndex: 'nickname',
    key: 'nickname',
    align:'center'
  },
  {
    title: '用户头像',
    key: 'avatar',
    dataIndex: 'avatar',
    align:'center'
  },
  {
    title: '用户类型',
    key: 'type',
    dataIndex: 'type',
    align:'center'
  },
  {
    title: '操作',
    key: 'action',
  },
];
// 表格数据
let tableDatas = ref([
]);
//渲染数据
const fetchData = async () => {
  loading.value = true;
  UserList(queryParam).then((res) => {
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
//用户表单
const formUser = reactive(
  {
    id: '',
    username: '',
    nickname: '',
    avatar: '',
    type: '',
  },
);
//获取到当前行的数据
const getRowData = (row) => {
  formUser.id = row.id;
  formUser.username = row.username;
  formUser.nickname = row.nickname;
  formUser.avatar = row.avatar;
  formUser.type = row.type;
  fileList.value = [
    {
      url: row.avatar,
    },
  ];
  if (row.type == 0) {
    usertype.value = {
      value: '0',
      label: '管理员',
    };
  } else {
    usertype.value = {
      value: '1',
      label: '普通用户',
    };
  }
  console.log(formUser.avatar);
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
  if (item === 'edit') {
    dialog.title = '编辑用户'; //编辑
    dialog.disabled = false;
    //编辑还是新增
    typeEdit = 'edit';
    //编辑
  } else {
    dialog.title = '新增用户';
    dialog.disabled = true;
    typeEdit = 'add';
    //新增
    formUser.id = '';
    formUser.username = '';
    formUser.nickname = '';
    formUser.avatar = '';
    formUser.type = '';
    fileList.value = [];
    usertype.value = {}
  }
};

//关闭对话框
const onClose = () => {
  dialog.visible = false;
};
//确认对话框
const onConfirm = () => {
  dialog.visible = false;
  console.log(formUser.username, "确认");
  if (typeEdit == 'edit') {
    UserEdit(formUser).then((res) => {
      try {
        message.success('编辑成功');
        fetchData();
      } catch {
        message.error('编辑失败');
      }
    });
  } else if (typeEdit == 'add') {
    UserAdd(formUser).then((res) => {
      try {
        message.success('新增成功');
        fetchData();
      } catch {
        message.error('新增失败');
      }
    });
  }
};
const labelCol = {
  style: {
    width: '70px',
  },
};
const wrapperCol = {
  span: 20,
};
//用户类型选择框
const options = ref([
  {
    value: '0',
    label: '管理员',
  },
  {
    value: '1',
    label: '普通用户',
  },
]);
const typeChange = value => {
  formUser.type = value.key; //赋值给表单用户类型
  console.log(value.key); // { key: "lucy", label: "Lucy (101)" }
};
const usertype = ref();
//对话框结束

//图片上传
function getBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = (error) => reject(error);
  });
}
//图片预览
const previewVisible = ref(false);
const previewTitle = ref('');

const previewImage = ref('');
const fileList = ref([]);
//图片预览关闭
const handleCancel = () => {
  previewVisible.value = false;
  previewTitle.value = '';
  console.log(456);
};
const handlePreview = async (file) => {
  if (!file.url && !file.preview) {
    file.preview = await getBase64(file.originFileObj);
  }
  previewImage.value = file.url || file.preview;
  previewVisible.value = true;
  previewTitle.value =
    file.name || file.url.substring(file.url.lastIndexOf('/') + 1);
  console.log(file, 8686);
};

//删除图片
const remove = (file) => {
  console.log(fileList.value, 8080);
  console.log(file, 999);
};

//图片上传--上传成功--失败都会触发
const handleChange = async ({ file, fileList }) => {
  if (file.status === 'done') {
    if (file.response.code === 200) {
      message.success('上传成功');
      //获取上传成功后的url
      formUser.avatar = file.response.data;
      console.log(file.response.data, 991319);
    } else {
      message.error('上传失败');
    }
  }
};

//删除
const confirm = e => {
  loading.value = true;
  UserDel({
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
