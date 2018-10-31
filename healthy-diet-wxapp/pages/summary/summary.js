// pages/summary/summary.js
const wxCharts = require('../../utils/wxcharts-min.js');
const app = getApp();
var columnChart = null;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userBodyInfo: {},
    goBackMain: '返回主程序',
    goShare: '好东西齐分享',
    reportHeader: '您的身体报告',

    basicInfoTitle: '基础身体信息',
    basicInfoSubTitle: '根据您的身体基本信息，我们为您规划了近期的理想目标体重，并按照目标体重设定了每日的总热量和总蛋白摄入量',
    basicInfoSummary: [],

    barChartTitle: 'CKD饮食结构推荐',
    barChartSubTitle: '根据您的理想体重，总能量和总蛋白质摄入需求，营养师推荐以下的每日饮食结构',
    suggestedNutrition: [],
    advice: '',
    slogan: '具体一日三餐食谱，可参考主页面的“膳食建议“功能获得我们的智能推荐',
    footerSlogan: '返回主程序\n以获得更多贴身智能膳食推荐',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    try {
      this.setData({
        userBodyInfo: wx.getStorageSync('userBodyInfo'),
        basicInfoSummary: wx.getStorageSync('basicInfoSummary'),
        suggestedNutrition: wx.getStorageSync('suggestedNutrition'),
        advice: wx.getStorageSync('advice'),
        slogan: wx.getStorageSync('slogan'),
      });
      console.log(this.data.basicInfoSummary);
      console.log(this.data.suggestedNutrition);
      if (this.data.userBodyInfo) {
        console.log(this.data.userBodyInfo);
      }
    } catch (e) {
      console.log('Exception happen when try to get userBodyInfo from storage!')
    }
    // wx.showShareMenu({
    //   withShareTicket: false
    // });
  },

  goBackMain: function (e) {
    wx.switchTab({
      url: '../index/index'
    })
  },

  
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    let windowWidth = 320;
    try {
      let res = wx.getSystemInfoSync();
      windowWidth = res.windowWidth;
    } catch (e) {
      console.log('fail to get system width.');
    }
    let suggestedNutrition = wx.getStorageSync('suggestedNutrition');
    let categories = suggestedNutrition.map(item => item.name);
    let data = suggestedNutrition.map(item => {
      var index = item.value.indexOf("克");
      return {
        name: item.name,
        data: parseInt(item.value.substring(0, index)),
      }
    })
    console.log(data);
    columnChart = new wxCharts({
      canvasId: 'columnCanvas',
      type: 'pie',
      animation: true,
      categories: categories,
      series: data,
      width: 300,
      height: 290,
      dataLabel: true,
    });
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

  onShareAppMessage: function (ops) {
    if (ops.from === 'button') {
      // 来自页面内转发按钮
      console.log(ops.target)
    }
    return {
      title: '肾脏健康营养师',
      path: '/pages/index/guide/guide',
      imageUrl: 'https://kidneyhealty.com.cn/images/guide2.jpg',
      success: function (res) {
        // 转发成功
        console.log("转发成功:" + JSON.stringify(res));
      },
      fail: function (res) {
        // 转发失败
        console.log("转发失败:" + JSON.stringify(res));
      }
    }
  },
})