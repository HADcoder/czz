const app = getApp();
let util = require('../../../utils/util.js');
Page({
  data: {
    inputShowed: false,
    inputVal: "",
    foodList: []
  },
  onShow: function () {
    if (app.globalData.foodTypeList === null || app.globalData.foodTypeList.length === 0) {
      app.initCategories();
    }
    this.setData({
      foodTypeList: app.globalData.foodTypeList
    });
  },
  showInput: function () {
    this.setData({
      inputShowed: true
    });
  },
  hideInput: function () {
    this.setData({
      inputVal: "",
      inputShowed: false
    });
  },
  clearInput: function () {
    this.setData({
      inputVal: ""
    });
  },
  inputTyping: function (e) {
    this.setData({
      inputVal: e.detail.value
    });
    wx.request({
      url: app.globalData.apiBase + "/food/search",
      method: "GET",
      data: {
        alias: e.detail.value
      },
      complete: res => {
        wx.hideLoading();
      },
      success: res => {
        this.setData({
          foodList: res.data.foodList
        })
      },
      fail: res => {
        util.showModel('请求失败,请检查网络', res.errMsg);
      }
    });
    console.log(this.data.inputVal);
  },
  onFoodTypeTap: function (e) {
    wx.navigateTo({
      url: "../foodList/foodList?typeCode=" + e.currentTarget.dataset.foodCode
    })
  }
});