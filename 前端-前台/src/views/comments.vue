<template>
    <div class="box">
        <HeaderList></HeaderList>
        <div class="box-container">
            <!-- 昵称和留言内容输入框 -->
            <div class="message">
                <p><span>*</span>留言内容</p>
                <textarea id="content" cols="30" rows="10" placeholder="请输入留言内容" v-model="newMessageContent"></textarea>
            </div>
            <div class="publish" @click="publishNewMsg()">
                <span>发布</span>
            </div>

            <!-- 留言内容展示 -->
            <div class="msg-content">
                <div class="msg-body" v-for="item in commentsList" :key="item.id">
                    <div class="msg-top">
                        <div>
                            <img class="msg-avatar" :src="item.avatar">
                            <span>{{item.username}}</span>
                        </div>
                        <span><img  >{{item.updatetime}}</span>
                    </div>
                    <p>{{ item.content }}</p>

                    <!-- 回复按钮 -->
                    <div class="msg-replies">
                        <a @click="toggleReply(item.id)"><FormOutlined />回复</a>
                    </div>

                    <!-- 回复框，判断当前回复框是否为点击的 item -->
                    <div v-if="activeReplyId === item.id" class="reply">
                        <a-textarea :placeholder="`回复 @${item.username}`" v-model:value="replyMessageContent"></a-textarea>
                        <div class="reply-btn"><a-button type="primary" size="small" @click="publishReply(item.id)">回复留言</a-button></div>
                    </div>

                    <!-- 子回复 -->
                    <div class="be-msg" v-for="child in item.children" :key="child.id">
                        <div class="msg-top">
                            <div>
                                <img class="msg-avatar" :src="child.avatar">
                                <span>{{child.username}}</span>
                            </div>
                            <span><img class="msg-icon" src="./assets/image/message/time.png">{{child.updatetime}}</span>
                        </div>
                        <p>{{ child.content }}</p>
                        <div class="msg-replies">
                            <a @click="toggleReply(child.id)"><FormOutlined />回复</a>
                        </div>

                        <!-- 子回复框，同样通过 activeReplyId 控制 -->
                        <div v-if="activeReplyId === child.id" class="reply">
                            <a-textarea :placeholder="`回复 @${child.username}`"  v-model:value="replyMessageContent"></a-textarea>
                            <div class="reply-btn"><a-button type="primary" size="small" @click="publishReply(child.id)">回复留言</a-button></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script setup>
import { getUserInfo } from '../utils/user';
import { CommentList,AddComment } from '../api/modules/comments';
import { FormOutlined } from '@ant-design/icons-vue';
import HeaderList from '../components/HeaderList.vue';
import { onMounted, reactive, ref } from 'vue';
import { message } from 'ant-design-vue';

const query = reactive({
    pageNum: 1,
    pageSize: 10,
});
// 留言和回复内容
const newMessageContent = ref();  // 新留言内容
const replyMessageContent = ref(); // 回复内容
const formMessage = ref({
    username: undefined, // 用户名
    avatar: undefined, // 头像
    pid: undefined, // 父级留言 ID
    content: undefined,//留言内容
});
// 回复框显示的 ID，默认不显示
const activeReplyId = ref(null);

// 留言列表
const commentsList = ref([]);

// 获取用户信息
const userInfo = getUserInfo();

// 发布新留言
const publishNewMsg = () => {
    if (!userInfo) {
        message.error('请先登录');
        return;
    }
    
    if (!newMessageContent.value) {
        message.error('请输入留言内容');
        return;
    }

    // 确保新发布不会误识别为回复
    activeReplyId.value = null;
    formMessage.value.content = newMessageContent.value;//留言内容
    formMessage.value.avatar = userInfo.avatar; // 头像
    formMessage.value.username = userInfo.nickname; // 用户名
    AddComment(formMessage.value).then(res => {
        if (res.code === 200) {
            message.success('新留言发布成功');
            newMessageContent.value = ""; // 清空输入框
            getComments();
        } else {
            message.error(res.msg);
        }
    });

};
// 发布回复
const publishReply = (replyToId) => {
    if (!userInfo) {
        message.error('请先登录');
        return;
    }

    if (!replyMessageContent.value) {
        message.error('请输入回复内容');
        return;
    }

    console.log('回复评论，ID:', replyToId, '内容:', replyMessageContent.value);
    // 调用发布回复的逻辑
    message.success(`回复成功，回复评论 ID: ${replyToId}`);
    AddComment({
        username: userInfo.nickname,
        avatar: userInfo.avatar,
        pid: replyToId,
        content: replyMessageContent.value,
    }).then(res => {
        if (res.code === 200) {
            message.success('回复成功');
                // 处理完回复后，清空 activeReplyId 和回复内容
        activeReplyId.value = null;
        replyMessageContent.value = ""; // 清空回复框
            getComments();
        } else {
            message.error(res.msg);
        }
    });

};
// 切换回复框
const toggleReply = (id) => {
    if (!userInfo) {
        message.error('请先登录');
        return;
    }
    // 如果当前已经显示的回复框就是点击的 ID，那么关闭回复框；否则显示新的回复框
    activeReplyId.value = activeReplyId.value === id ? null : id;
};

// 获取留言列表
const getComments = async () => {
    const res = await CommentList(query);
    commentsList.value = res.data.rows;
};

onMounted(() => {
    getComments();
});
</script>
<style lang="scss" scoped>
@import url('../assets/css/msg.css');

:deep(.anticon) {
    width: 1px;
}
.box {
    background: #f1f2f5;
    height: 100vh;
}
.reply {
    textarea {
        transition: height 0.5s;
        border: 1px solid #ebebeb;
        border-radius: 0.5rem;
        padding: 0.5rem;
        resize: none;
        outline: none;
        font-size: 0.8rem;
        margin-top: 1rem;
        background-color: #f1f2f5;
    }
    .reply-btn {
        display: flex;
        justify-content: flex-end;
        margin-top: 1rem;
        button {
            background-color: #17A1FF;
        }
    }
}
</style>
