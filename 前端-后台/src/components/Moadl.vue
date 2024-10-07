<template>
    <div>
        <a-modal v-model:open="props.open" :maskClosable="false" :title="props.title" :closable="false"
            :width="props.width" :height="props.height" @ok="onClose">
            <template #footer>
                <a-button @click="onClose">取消</a-button>
                <a-button type="primary" @click="onConfirm">确定</a-button>
            </template>
            <div :style="{ height: props.height + 'px' }" style="overflow-y: auto;overflow-x: inherit;">
                <slot name="content"></slot>
            </div>
        </a-modal>
    </div>
</template>
<script setup>
import { ref } from 'vue';
const props = defineProps({
    open: {
        required: false,
        type: Boolean,
        default: false
    },
    title: {
        required: false,
        type: String,
        default: "标题"
    },
    width: {
        required: false,
        type: Number,
        default: 450
    },
    height: {
        required: false,
        type: Number,
        default: 400
    },
})

//注册事件
const emit = defineEmits(['onClose', 'onConfirm'])

//关闭对话框
const onClose = () => {
    emit('onClose')
}

//确认对话框
const onConfirm = () => {
    emit('onConfirm')
}
</script>
<style lang="scss">
:deep(.ant-modal-header) {
    padding: 14px 24px;
    border-top-left-radius: 7px;
    border-top-right-radius: 7px;
    background-color: #1890ff;

    .ant-modal-title {
        color: #fff;
    }
}

.custom-modal .ant-modal-content {
    border-top-left-radius: 7px;
    border-top-right-radius: 7px;

    .ant-modal-header {
        padding: 14px 24px;
        border-top-left-radius: 7px;
        border-top-right-radius: 7px;
        background-color: #1890ff;
    }

    .ant-modal-body {
        padding: 10px 10px;
    }

    .ant-modal-close {
        .ant-modal-colse-icon {
            color: #fff;
        }
    }
}
</style>