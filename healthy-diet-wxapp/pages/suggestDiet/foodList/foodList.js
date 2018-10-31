const app = getApp();
let util = require('../../../utils/util.js');
Page({
  data: {
    inputShowed: false,
    inputVal: "",
    typeCode: null,
    foodList: [],//for all data return from server
    foodListView: [],//for page filtering only
  },
  onLoad: function (options) {
    let that = this;
    this.setData({
      typeCode: options.typeCode
    });
    if(this.data.typeCode) {
      wx.showLoading({
        title: "正在加载食材信息...",
        mask: true
      });
      wx.request({
        url: app.globalData.apiBase + "/food/",
        method: "GET",
        data: {
          "typeId": options.typeCode
        },
        header: {
          "Content-Type": "application/json"
        },
        dataType: "json",
        complete: res => {
          wx.hideLoading();
        },
        success: res => {
          console.log(res);
          that.setData({
            foodList: res.data.foodList,
            foodListView: res.data.foodList
          });
        },
        fail: res => {
          console.log(res);
          util.showModel('请求失败,请检查网络', res.errMsg);
        }
      })
    }
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
    console.log(this.data.inputVal);
  },
  onFoodTypeTap: function (e) {

  }
});