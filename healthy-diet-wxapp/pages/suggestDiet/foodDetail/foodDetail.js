// pages/foodDetail.js
const app = getApp();
let util = require('../../../utils/util.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    foodCode: null,
    foodInfo: {
      imageUrl: 'https://kidneyhealty.com.cn/images/item_icon.png',
    },
    food_composition: null,
    showModalStatus: true,
    animationData: null,
    preferenceMap: [
      {key: "1", value: "不喜欢吃"},
      {key: "2", value: "马马虎虎"},
      {key: "3", value: "超爱吃的"},
    ],
    hasUserBodyInfo: false,
    motto: '只需九步，了解自己更多',
    slogon2: '记录身体信息\n获得营养师更有针对性的推荐',
    recipeList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (app.globalData.userBodyInfo !== null) {
      this.setData({
        hasUserBodyInfo: true
      })
    }
    this.setData({
      foodCode: options.foodCode
    });
    console.log(options);
    wx.showLoading({
      title: "正在加载食材信息...",
      mask: true
    });
    if (this.data.foodCode) {
      let that = this;
      wx.request({
        url: app.globalData.apiBase + "/food/detail/" + this.data.foodCode,
        data: {
          "openId": app.globalData.authInfo.openid
        },
        method: "GET",
        header: {
          "Content-Type": "application/json"
        },
        dataType: "json",
        complete: res => {
          wx.hideLoading();
        },
        success: res => {
          if (res.data.frequency !== undefined) {
            this.data.preferenceMap.forEach(item => {
              item.default_checked = (item.key === res.data.frequency);
            });
          }
          that.setData({
            foodInfo: res.data,
            preferenceMap: this.data.preferenceMap,
            recipeList: res.data.recipeList,
          });
          that.setData({
            food_composition: Object.entries(this.data.foodInfo.composition).map(item => {
              return {name: item[0], value: item[1]}
            }),
          });

        },
        fail: res => {
          util.showModel('请求失败,请检查网络', res.errMsg);
        }
      });
    }
  },

  //事件处理函数
  goToQuestions: function () {
    wx.switchTab({
      url: '../../index/index'
    })
  },

  jumpRecipe: function (e) {
    let recipeId = e.currentTarget.dataset.recipeCode;
    wx.navigateTo({
      url: '../recipeDetail/recipeDetail?recipeCode=' + recipeId
    });
  },

  updatePreference: function (e) {
    let preference = e.detail.value;
    console.log(preference);
    let foodCode = this.data.foodCode;
    wx.showLoading({
      title: "正在更新信息...",
      mask: true
    });
    wx.request({
      url: app.globalData.apiBase + "/food/preference",
      data: {
        "userId": app.globalData.authInfo.openid,
        "foodId": foodCode,
        "preference": preference,
      },
      method: "POST",
      header: {
        "Accept": "application/json",
        "Content-Type": "application/x-www-form-urlencoded"
      },
      dataType: "json",
      complete: res => {
        wx.hideLoading();
      },
      success: res => {
        if (res.data.status === 500) {
          wx.showModal({
            title: res.data.message,
            status: "fail"
          })
        } else {
          console.log("Successfully post preference to backend")
        }
      },
      fail: res => {
        util.showModel('请求失败,请检查网络', res.errMsg);
      }
    });
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

});