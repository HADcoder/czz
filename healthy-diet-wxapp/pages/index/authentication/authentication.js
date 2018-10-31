// pages/index/authentication/authentication.js
const app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    title:{
      headerText: '授权设置',
      subHeader: '更多信息将有助于了解你的身体状况',
    },
    hasUserInfo: false,
    settingIconUrl: app.globalData.imageBasePath + 'index/setting_icon.png',
    greentickIconUrl: app.globalData.imageBasePath + 'auth/green_tick.png',
    greytickIconUrl: app.globalData.imageBasePath + 'auth/grey_tick.png',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  getUserInfo: function (e) {
    console.log(e)
    if (e.detail.userInfo) {
      app.globalData.userInfo = e.detail.userInfo;
      this.setData({
        hasUserInfo: true
      })
    } else {
      console.log('User reject the authentication');
      this.setData({
        hasUserInfo: false
      })
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    if (app.globalData.userInfo) {
      this.setData({
        hasUserInfo: true
      })
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },


})