# 校园新闻发布系统

#### 介绍
仿的腾讯新闻网，学校要求实训要有一个实训项目国庆前花了一周写的
当时写完交上去的时候感觉写的还行，收假回来后这写的什么垃圾东西啊

写的不规范有几处已只BUG不想修了，拿去作为实训项目也够用了。

前端分为前台和后台，前台可查看已发布的新闻文章 可发布留言 后台可以添加删除用户、新增编辑文章

↓↓↓项目基于↓↓↓

前端 Vue3+Ant Design Vue+Vue Router+Pinia+Axios

后端-服务端 Spring Boot3+MyBatis-Plus+Lombok+MySQL

 **前台** 
![首页](https://github.com/shifuf/campus-news-release-system/blob/main/img/%E9%A6%96%E9%A1%B5.png?raw=true)
![列表页](https://github.com/shifuf/campus-news-release-system/blob/main/img/%E5%88%97%E8%A1%A8%E9%A1%B5.png?raw=true)
![输入图片说明](https://github.com/shifuf/campus-news-release-system/blob/main/img/%E5%88%97%E8%A1%A8%E9%A1%B5.png?raw=true)
![输入图片说明](https://github.com/shifuf/campus-news-release-system/blob/main/img/%E7%95%99%E8%A8%80%E9%A1%B5.png)
 **后台** 
![登陆页](https://github.com/shifuf/campus-news-release-system/blob/main/img/%E7%99%BB%E5%BD%95%E9%A1%B5.png)
![用户管理列表](https://github.com/shifuf/campus-news-release-system/blob/main/img/%E7%94%A8%E6%88%B7%E7%AE%A1%E7%90%86%E5%88%97%E8%A1%A8.png)
![新增用户](https://github.com/shifuf/campus-news-release-system/blob/main/img/%E6%96%B0%E5%A2%9E%E7%94%A8%E6%88%B7.png)
![编辑用户](https://github.com/shifuf/campus-news-release-system/blob/main/img/%E7%BC%96%E8%BE%91%E7%94%A8%E6%88%B7.png)
![分类类表](https://github.com/shifuf/campus-news-release-system/blob/main/img/%E5%88%86%E7%B1%BB%E5%88%97%E8%A1%A8.png)
![文章列表](https://github.com/shifuf/campus-news-release-system/blob/main/img/%E6%96%87%E7%AB%A0%E5%88%97%E8%A1%A8.png)
![文章发布-编辑](https://github.com/shifuf/campus-news-release-system/blob/main/img/%E6%96%87%E7%AB%A0%E5%8F%91%E5%B8%83-%E7%BC%96%E8%BE%91.png)
![输入图片说明](https://github.com/shifuf/campus-news-release-system/blob/main/img/%E7%95%99%E8%A8%80%E5%88%97%E8%A1%A8.png)

#### 安装教程

1. 确保已安装Node.js版本18及以上+Java版本17以上
2. 打开终端并克隆此仓库到本地：git clone https://github.com/shifuf/campus-news-release-system.git
3. 进入前端-前台+前端-后台项目目录
4. 安装项目依赖：npm install
5. 启动数据库，注：需要手动添加一个用户密码用的md5加密，忘记写注册页面，注册接口已经写了
