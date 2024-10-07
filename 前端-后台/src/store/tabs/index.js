import { defineStore } from "pinia";

export const tabsStore = defineStore('tabsStore',{
    // 定义store
    state: () => {
        return {
            tabsList:[]
        }
    },

    //定义getters
    getters:{
        // 获取tabs
        getTabs:(state) =>{
            return state.tabsList
        }
    },

    //定义actions
    actions:{
        // 添加tabs
        addTab(tab){
            if(tab.path =='/login') return
            if(this.tabsList.some(item => item.path === tab.path)){
                return
            }
            this.tabsList.push(tab)
        }
    }
})