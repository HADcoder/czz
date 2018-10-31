// pages/question/sportRate/sportRate.js
let util = require('../../../utils/util.js');
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    question: '日常体力活动程度：',
    nextQuestionBtnText: '下一步 （6/9）',
    updateValueBtnText: '确认更新并返回',
    parameter: util.parameterMap.sportRate,
    value: [1],
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
      var preValue = app.globalData.userBodyInfo.sportRate;     
      let parameterList = this.data.parameter;
      for (let i = 0; i < parameterList.length; i++) {
        if (parameterList[i].key === preValue) {
          this.setData({
            value:[i],
          })
        }
      }
    }else{
      app.globalData.userBodyInfo.sportRate = this.data.parameter[1].key;
    }

    
  },

  bindChange: function (e) {
    const val = e.detail.value;
    app.globalData.userBodyInfo.sportRate = this.data.parameter[val[0]].key;
    console.log(app.globalData.userBodyInfo.sportRate);
  },

  goToNextQuestion: function (e) {
    console.log(app.globalData.userBodyInfo);
    wx.navigateTo({
      url: '../nephroticPeriod/nephroticPeriod?postUpdate=false'
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