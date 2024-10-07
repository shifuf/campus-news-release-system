<template>
    <div class="container">
        <div class="loginbox">
            <div class="login-left">
                <div class="header">
                    <h1>欢迎登录</h1>
                    <span>校园新闻</span>
                </div>
            </div>
            <div class="login-right">
                <h3 class="title">用户登录</h3>
                <a-form :model="formState" name="basic" :label-col="{ span: 5 }" :wrapper-col="{ span: 16 }"
                    autocomplete="off" @finish="onFinish" @finishFailed="onFinishFailed">
                    <a-form-item label="账号" name="username"
                        :rules="[{ required: true, message: '请输入账号' }]">
                        <a-input  autoComplete="true" v-model:value="formState.username" />
                    </a-form-item>

                    <a-form-item label="密码" name="password"
                        :rules="[{ required: true, message: '请输入密码' }]">
                        <a-input-password autoComplete="true" v-model:value="formState.password" />
                    </a-form-item>

                    <a-form-item :wrapper-col="{ offset: 8, span: 16 }">
                        <a-button type="primary" html-type="submit">登录</a-button>
                    </a-form-item>
                </a-form>
            </div>
        </div>
        
    </div>
</template>

<script setup>
import { onMounted, reactive } from 'vue';
import { userLogin } from '../../api/modules/user'
import { message } from 'ant-design-vue';
import { setUserId } from '../../utils/user';
import router from '../../router';
import { setUserInfo } from '../../utils/user';
const formState = reactive({
    username: '',
    password: '',
});
const onFinish = values => {
    console.log();
    userLogin({
        username: formState.username,
        password: formState.password
    }).then(res => {
        console.log(res)
        if (res.code ==200) {
            message.success('登录成功');
            //登录成功写入用户id
            setUserInfo(res.data)
            //写入本地存储
            console.log(res.data,"login");
            
            //跳转到首页
            setTimeout(() => {
                router.push({ path: '/' });
            },100);
        }else{
            message.error(res.msg);
        }
    })
};
const onFinishFailed = errorInfo => {
    console.log('Failed:', errorInfo);
};
onMounted(() => {
    if (localStorage.getItem('userInfo')) {
        
        message.success('已登录');
        setTimeout(() => {
            router.push({ path: '/' });
        }, 200);
    }else{
        console.log('未登录');
    }
});
</script>
<style lang="scss" scoped>
.container {
    width: 100%;
    min-height: 100vh;
    background: #e2effc url(../../assets/images/loginbg.jpg) no-repeat top;
    background-size: 100%;
    vertical-align: middle;
    display: flex;

    .loginbox {
        width: 800px;
        height: 500px;
        margin: auto;
        background: #fff;
        -webkit-box-shadow: 0 5px 15px rgba(0, 0, 0, .15);
        box-shadow: 0 5px 15px rgba(0, 0, 0, .15);
        border-radius: 8px;

        .login-left {
            width: 50%;
            height: 100%;
            float: left;
            background: url(../../assets/images/login-left.png) no-repeat center;
            background-size: cover;
            border-top-left-radius: 8px;
            border-bottom-left-radius: 8px;
            color: #fff;
            
            .header {
                height: 400px;
                padding: 50px;
                margin-top: 100px;

                h1 {
                    font-size: 30px;
                    margin-bottom: 10px;
                }

            }
        }

        .login-right {
            height: 500px;
            margin: 0 auto;
            display: block;
            padding: 0 30px;
            position: relative;
            .title{
                padding: 60px 0 45px 0;
                padding-left: 400px;
            }
        }
    }
}
</style>
