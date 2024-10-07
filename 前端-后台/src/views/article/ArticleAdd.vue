<template>
  <div class="card-top">
    <a-form ref="formRef" name="advanced_search" class="ant-advanced-search-form" :model="formData" @finish="onFinish">
      <a-row :gutter="10">
        <a-col :span="3">
          <a-form-item label="标题">
            <a-input v-model:value="formData.title" placeholder="请输入标题"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="2">
          <a-form-item label="作者">
            <a-input v-model:value="formData.author" placeholder="请输入作者"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="3">
          <a-form-item label="分类">
            <a-select v-model:value="formData.categoryid" placeholder="请选择分类"
              :options="categoryList.map(item => ({ value: item.id, label: item.name }))">
              <template #dropdownRender="{ menuNode: menu }">
                <v-nodes :vnodes="menu" />
                <a-divider style="margin: 4px 0" />
                <a-space style="padding: 4px 8px">
                  <a-input ref="inputRef" v-model:value="categoryName" placeholder="添加分类" />
                  <a-button type="text" @click="addItem">
                    <template #icon>
                      <plus-outlined />
                    </template>
                    添加分类
                  </a-button>
                </a-space>
              </template>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="2">
          <a-form-item label="是否顶置">
            <a-select v-model:value="formData.stop" style="width: 13em">
              <a-select-option :value="1">
                是
              </a-select-option>
              <a-select-option :value="0">
                否
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="2" style="margin-left: 5rem;">
          <a-form-item style="align-items: center">
            <a-input style="display: none" v-model:value="formData.coverimg" />
            <!-- 图片上传 -->
            <div class="clearfix">
              <a-upload v-model:file-list="fileList" action="/api/file/upload" list-type="picture-card"
                @preview="handlePreview" @remove="remove" @change="handleChange">
                <div v-if="fileList.length < 1">
                  <div>
                    <FileImageOutlined />上传封面
                  </div>
                </div>
              </a-upload>
              <!-- 图片预览 -->
              <a-modal :open="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
                <img alt="example" style="width: 100%" :src="previewImage" />
              </a-modal>
            </div>
          </a-form-item>
        </a-col>
        <a-col :span="2" style="text-align: left">
          <a-button type="primary" html-type="submit" @click="publish">发布</a-button>
          <!-- <a-button >Clear</a-button> -->
        </a-col>
      </a-row>
    </a-form>
    <!-- 富文本 -->
    <div style="height: 80vh;width: 100%">
      <MdEditor style="height: 100%;" v-model="formData.content" />
    </div>

  </div>
</template>
<script setup>
import { defineComponent, onMounted, reactive, ref } from 'vue';
import { CategoryAdd, CategoryList } from '../../api/modules/category';

import { ArticleDetail, ArticleAdd, ArticleEdit } from '../../api/modules/Article';
import { MdEditor } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';

import {  useRoute } from 'vue-router';
import {  useRouter } from 'vue-router';
import { message } from 'ant-design-vue';
const route = useRoute();
const router = useRouter();
//查询参数
const queryParam = reactive({
  pageSize: 20,
  pageNum: 1,
});

//根据文章列表传递过来的ID渲染文章详情
const getDetail = () => {
  if (route.query.id) {
    ArticleDetail({
      id: route.query.id
    }).then((res) => {
      formData.value = res.data
      fileList.value = [
        {
          url: res.data.coverimg,
        },
      ];
      console.log('formData', formData);
    })
  }
};
const formRef = ref({});
const formData = ref({
  title: undefined, //文章标题
  content: undefined, //文章内容
  author: undefined, //作者
  coverimg: undefined, //封面图像
  stop: 0, //是否推荐 0否 1是
  categoryid: undefined, //分类ID
});
//表单提交
const onFinish = values => {
  console.log('Received values of form: ', values);
  console.log('formData: ', formData);
};

//选择分类

const VNodes = defineComponent({
  props: {
    vnodes: {
      type: Object,
      required: true,
    },
  },
  render() {
    return this.vnodes;
  },
});
let index = 0;
//分类类表
const categoryList = ref([])

//渲染分类
const renderCategory = async () => {
  const res = await CategoryList(queryParam);
  categoryList.value = res.data.rows;
};
const value = ref();
const inputRef = ref();
const categoryName = ref()
//分类名称
const category = ref();
//选择分类时下拉框添加分类
const addItem = e => {
  e.preventDefault();
  console.log('addItem');
  const data = { name: categoryName.value };
  categoryName.value = '';
  CategoryAdd(data).then(res => {
    if (res.code === 200) {
      categoryList.value.push(data);
      renderCategory();
      message.success('添加成功');
    }
  });
  setTimeout(() => {
    inputRef.value?.focus();
  }, 0);
};
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
      formData.value.coverimg = file.response.data;
      console.log(formData.va.coverimg, 991319);
    } else {
      message.error('上传失败');
    }
  }
};
//文章发布
const publish = () =>{
  //根据传递的id进行判断新增或编辑
  if(!route.query.id){
    ArticleAdd(formData.value).then((res) =>{
     if(res.code == 200){
      message.success("发布成功")
      //发布成功后遍历清空表单
      for (const key in formData.value) {
        formData.value[key] = undefined
      }
      formData.value.coverimg = undefined
      router.push('/article/list')
     }else{
       message.error(res.msg+"发布失败")

     }
    })
  }else{
    ArticleEdit(formData.value).then((res) =>{
      if(res.code==200){
        message.success("编辑成功")
        for (const key in formData.value) {
        formData.value[key] = undefined
      }
      //编辑成功后跳转到文章列表
      router.push('/article/list')
      }else{
        message.error(res.msg+"编辑失败")
      }
      
    })
  }
  // if(!route.query.id){
  //   for(let key in formData.value){
  //     formData.value[key] = undefined
  //   }
  //   fileList.value = [{}]
  //     console.log(formData.value, "xinzeng");
      
  // }else{
  //   for(let key in formData.value){
  //     formData.value[key] = undefined
  //   }
  //   fileList.value = [{}]
  //     console.log(formData.value, "bianji");
  //     setImmediate(() => {
  //       router.push('/article/list')
  //       message.success("编辑成功")
  //     },1000);
  // }
  
}
onMounted(() => {
  renderCategory();
  getDetail();
});
</script>
<style lang="scss" scoped>
:deep(.ant-upload-wrapper.ant-upload-picture-card-wrapper .ant-upload.ant-upload-select) {
  width: 152px;
  height: 40px;

}

.card-top {
  width: 100%;
  background-color: #fff;
  border-radius: 5px;
  padding: 20px;

}
</style>