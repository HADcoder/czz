// pages/question/irritability/irritability.js
let util = require('../../../utils/util.js');
let sdk = require('../../../vendor/wafer2-client-sdk/index');
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    question: '您是否有以下过敏症？',
    fetchReport: '查看report',
    updateValueBtnText: '确认更新并返回',
    parameter: util.parameterMap.irritability,//奶/蛋/贝壳/虾蟹鱼/面粉/坚果/黄豆/玉米
    postUpdate: false,
    hiddenLoading: true,
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
    if (options.postUpdate != 'false') {
      this.setData({
        postUpdate: true,
      });

      let preValue = app.globalData.userBodyInfo.irritability;
      let parameterList = this.data.parameter;
      parameterList.filter(item => preValue.includes(item.key)).forEach(item => {
        item.checked = true
      });
      this.setData({
        parameter: parameterList
      })
    } else {
      app.globalData.userBodyInfo.irritability = [];
    }
  },

  checkboxChange: function (e) {
    console.log(e.detail.value);
    app.globalData.userBodyInfo.irritability = e.detail.value;

  },

  sendDataAndSeeReport: function (e) {
    var that = this;
    that.setData({
      hiddenLoading: false,
    });
    wx.setStorageSync('userBodyInfo', app.globalData.userBodyInfo);
    console.log(app.globalData.userBodyInfo);
    console.log(app.globalData.userInfo);
    console.log('userBodyInfo is stored.');
    try {
      sdk.request({
        url: app.globalData.apiBase + `/home/report`,
        method: 'POST',
        header: {"Content-Type": "application/json"},
        data: {
          userInfo: {
            info: JSON.stringify(app.globalData.userInfo),
            openId: wx.getStorageSync('openid')
          },
          userDataInfo: wx.getStorageSync('userBodyInfo')
        },
        login: false,
        success(result) {
          //util.showSuccess('请求成功完成');
          console.log("请求成功");
          console.log(result.data);

          app.globalData.basicInfoSummary = [
            {name: "体型", value: result.data.bmi},
            {name: "目标体重", value: result.data.standardWeight},
            {name: "总热量摄入", value: result.data.calorie},
            {name: "总蛋白摄入", value: result.data.protein}
          ];
          app.globalData.advice = result.data.advice;
          app.globalData.slogan = result.data.slogan;
          app.globalData.suggestedNutrition = result.data.suggestNutrition;

          wx.setStorageSync('basicInfoSummary', app.globalData.basicInfoSummary);
          wx.setStorageSync('suggestedNutrition', app.globalData.suggestedNutrition);
          wx.setStorageSync('advice', app.globalData.advice);
          wx.setStorageSync('slogan', app.globalData.slogan);

          that.setData({
            hiddenLoading: true,
          });
          wx.redirectTo({
            url: '../../summary/summary'
          });
        },
        fail(error) {
          that.setData({
            hiddenLoading: true,
          });
          util.showModel('请求失败,请检查网络', error);
          console.log('request fail', error);
        }
      });
    } catch (e) {
      this.setData({
        hiddenLoading: true,
      });
      console.log('Exception happen when store userBodyInfo!')
      console.log(e)
    }
  },

  updateValue: function (e) {
    console.log(app.globalData.userBodyInfo);
    try {
      wx.setStorageSync('userBodyInfo', app.globalData.userBodyInfo);
      console.log('userBodyInfo is updated.');
    } catch (e) {
      console.log('Exception happen when try to get userBodyInfo from storage!')
    }
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