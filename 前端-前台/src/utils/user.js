//存储用户id用户判断是否登录因为没有做token功能
export function setUserId(id){
    return localStorage.setItem('userId',id)
}
//获取用户id
export function getUserId(){
  return localStorage.getItem('userId')
}
//退出时清除用户id
export function removeUserId(){
  return localStorage.removeItem('userId')
}
//存储用户信息
export function setUserInfo(userInfo){
    return localStorage.setItem('userInfo',JSON.stringify(userInfo))
}
//获取用户信息
export function getUserInfo(){
    return JSON.parse(localStorage.getItem('userInfo'));
}
//清除用户信息
export function removeUserInfo(){
    return localStorage.removeItem('userInfo')
}
//清空本地存储
export function clearLocalStorage(){
    return localStorage.clear()
}