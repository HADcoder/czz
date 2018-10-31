// pages/question/treatmentMethod/treatmentMethod.js
let util = require('../../../utils/util.js');
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    question: '目前治疗方式是：',
    nextQuestionBtnText: '下一步 （8/9）',
    updateValueBtnText: '确认更新并返回',
    parameter: util.parameterMap.treatmentMethod,
    value: [0],
    postUpdate: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.postUpdate != 'false') {
      this.setData({
        postUpdate: true,
      });
      let preValue = app.globalData.userBodyInfo.treatmentMethod;
      let parameterList = this.data.parameter;
      parameterList.filter(item => preValue.includes(item.key)).forEach(item => {
        item.checked = true
      });
      this.setData({
        parameter: parameterList
      });
    } else {
      app.globalData.userBodyInfo.treatmentMethod = [];
    }

    if (app.globalData.logoUrl) {
      this.setData({
        logoUrl: app.globalData.logoUrl
      })
    }
  },

  checkboxChange: function (e) {
    console.log(e.detail.value);
    app.globalData.userBodyInfo.treatmentMethod = e.detail.value;
  },

  goToNextQuestion: function (e) {
    console.log(app.globalData.userBodyInfo);
    wx.navigateTo({
      url: '../../question/otherDisease/otherDisease?postUpdate=false'
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