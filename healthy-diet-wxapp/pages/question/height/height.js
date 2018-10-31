// pages/question/height/height.js
let util = require('../../../utils/util.js');
const heights = util.range(100, 230);
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    question: '您的身高是？',
    nextQuestionBtnText: '下一步 （4/9）',
    updateValueBtnText: '确认更新并返回',
    heights: heights,
    value: [parseInt(heights.length / 2)],
    postUpdate: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (app.globalData.logoUrl) {
      this.setData({
        logoUrl: app.globalData.logoUrl
      })
    }
    if (options.postUpdate == 'true') {
      this.setData({
        postUpdate: true,
      });
      var preValue = app.globalData.userBodyInfo.height.substr(0,3);
      let parameterList = heights;
      for (let i = 0; i < parameterList.length; i++) {
        if (parameterList[i] === preValue) {
          this.setData({
            value: [i],
          })
        }
      }
    }else{
      app.globalData.userBodyInfo.height = "165cm";
    }
    
  },

  bindChange: function (e) {
    const val = e.detail.value;
    app.globalData.userBodyInfo.height = this.data.heights[val[0]] + "cm";
  },

  goToNextQuestion: function (e) {
    console.log(app.globalData.userBodyInfo);
    wx.navigateTo({
      url: '../weight/weight?postUpdate=false'
    })
  },

  updateValue: function (e) {
    console.log(app.globalData.userBodyInfo);
    try {
      wx.setStorageSync('userBodyInfo', app.globalData.userBodyInfo);
      console.log('userBodyInfo is updated.');
    } catch (e) {
      console.log('Exception happen when try to get userBodyInfo from storage!')
    };
    wx.navigateBack({
      delta: -1
    })
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