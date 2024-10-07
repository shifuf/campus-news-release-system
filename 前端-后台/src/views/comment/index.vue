<template>
  <a-table :columns="columns" :data-source="msgdataList" :rowKey="(msgdataList) => msgdataList.id"  >

    <template #bodyCell="{ column, record }">
          <!-- <template v-if="column.key == 'name'">
            <a-tag color="#2db7f5">{{ record.name }}</a-tag>
          </template>
          <template v-if="column.key == 'articlecount'">
            <a-tag color="blue">{{ record.articlecount }}</a-tag>
          </template> -->
          <template v-if="column.key === 'avatar'">
            <a-image :width="200"  :src="record.avatar" />
          </template>
          <template v-if="column.key === 'action'">
            <span>
              <a @click="editComment(record)">编辑</a>
              <a-divider type="vertical" />
              <!-- <a>删除</a> -->
              <a-popconfirm title="确定要删除该留言?" ok-text="确定" cancel-text="取消" @confirm="confirm(record.id)">
                <a href="#">删除</a>
              </a-popconfirm>
            </span>
          </template>
        </template>
  </a-table>
  <Moadl :open="dialog.visible" :title="dialog.title" @onClose="onClose" @onConfirm="onConfirm" :height="60">
    <template #content>
      <a-form :model="formComment" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-form-item label="分类名称">
          <a-input v-model:value="formComment.content" />
        </a-form-item>
      </a-form>
    </template>
  </Moadl>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import { CommentList,EditComment,DelComment } from '../../api/modules/comments';
import { getUserInfo } from '../../utils/user';
import { message } from 'ant-design-vue';
import Moadl from '../../components/Moadl.vue';
const queryParam = reactive({
  pageSize: 10,
  pageNum: 1,
});
const userInfo = getUserInfo();
//编辑
const formComment = reactive({
  id: undefined,
  pid: undefined,
  username: undefined,
  content: undefined,
  avatar: undefined,
})
//分页参数
const pagination = reactive({
  total: 0,
  pageSize: 5, // 每页中显示 5 条数据
  current: 1, // 当前页数
  showSizeChanger: true, // 是否可以改变每页的数据
  pageSizeOptions: ['1', '5', '10', '20'], // 每页中显示的数据选项
  showTotal: (total) => `共有 ${total} 条数据`, // 分页中显示总的数据
});
const columns = [
  {
    title: '昵称',
    dataIndex: 'username',
    key: 'username',
    width: '12%',
    align:'center'
  },
  {
    title: '留言内容',
    dataIndex: 'content',
    width: '30%',
    key: 'content',
    align:'center'
  },
  {
    title: '头像',
    dataIndex: 'avatar',
    key: 'avatar',
    align:'center'
  },
  {
    title: '发布时间',
    dataIndex: 'createtime',
    key: 'createtime',
    width: '12%',
    align:'center'
  },
  {
    title: '操作',
    key: 'action',
    align: 'center'
  },
];
//留言列表数据
const msgdataList = ref([]);
//渲染数据
const renderData = () => {
  CommentList(queryParam).then((res) => {
    msgdataList.value = res.data.rows;
    pagination.total = res.data.total;

  });
};
//编辑留言
const editComment = (rows) => {
  dialog.visible = true;
  formComment.id = rows.id;
  formComment.pid = rows.pid;
  formComment.username = rows.username;
  formComment.content = rows.content;
  formComment.avatar = rows.avatar;
};

//删除留言
const confirm = (id) => {
  DelComment({
    id:id
  }).then((res) => {
    if (res.code == 200) {
      message.success('删除成功');
      renderData();
    }
  });
};
//对话框开始
const dialog = reactive({
  visible: false, //打开对话框
  title: '编辑留言', //标题
  disabled: true, //是否禁用
});
//关闭对话框
const onClose = () => {
  dialog.visible = false;
  //清空表单
  formComment.id = undefined;
  formComment.pid = undefined;
};
//确认对话框
const onConfirm = () => {
  dialog.visible = false;
  EditComment(formComment).then((res) => {
    if (res.code == 200) {
      message.success('编辑成功');
      renderData();
    }
  });
};
const labelCol = {
  style: {
    width: '80px',
  },
};
const wrapperCol = {
  span: 50,
};
onMounted(() => {
  renderData();
}); 
</script>
<style scoped></style>
