// pages/suggestDiet/dailyTips/dailyTips.js
let util = require('../../../utils/util.js');
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    headerText: '',
    currentDate: null,
    subHeader: '换一批推荐看看',
    mealList: null,
    aliasMap: {
      "additionMeal": "加餐",
      "breakfast": "早餐",
      "lunch": "午餐",
      "dinner": "晚餐",
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.freshDay();

    let dailyRecommendation = wx.getStorageSync('dailyRecommendation');
    if (!dailyRecommendation
      || (dailyRecommendation.length > 0 && this.data.currentDate !== dailyRecommendation[0].effectDate)) {
      this.refreshRecommendation();
    } else {
      this.setData({
        mealList: dailyRecommendation,
      });
    }
  },

  freshDay: function () {
    const date = new Date();
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const currentDate = year + "-" + util.formatNumber(month) + "-" + util.formatNumber(day);
    this.setData({
      headerText: currentDate,
      currentDate: currentDate,
    });
  },

  /**
   * 换一批推荐菜谱
   */
  refreshRecommendation: function (e) {
    console.log("Get new batch of recommendation recipe.");
    wx.showLoading({
      title: "加载中",
      mask: true
    });
    let _this = this;
    wx.request({
      url: app.globalData.apiBase + "/meals/recommendation",
      method: "GET",
      data: {
        openId: app.globalData.authInfo.openid
      },
      dataType: "json",
      complete: res => {
        wx.hideLoading();
      },
      success: (result) => {
        console.log(result.data);
        let mealListResult = Object.entries(result.data)
          .filter(item => item[0] in this.data.aliasMap)
          .map(item => {
            return {
              effectDate: this.data.currentDate,
              meatime: this.data.aliasMap[item[0]],
              recipeList: item[1].map(item2 => {
                return {
                  recipeId: item2.recipeId,
                  recipeName: item2.recipeName,
                  shortIntroduction: util.cutMessage("建议食用" + Object.entries(item2.materials)
                      .map(item3 => item3[0] + item3[1] + "克")
                      .join(","), 25),
                  protein: "含" + item2.protein + "克蛋白"
                };
              })
            }
          });

        wx.setStorageSync('dailyRecommendation', mealListResult);
        _this.setData({
          mealList: mealListResult,
        });
        console.log(mealListResult);
      },

      fail: (result) => {
        wx.showModel('后台错误', result.errMsg)
      },
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
    this.freshDay();
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

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    if (ops.from === 'button') {
      // 来自页面内转发按钮
      console.log(ops.target)
    }
    return {
      title: '肾脏健康营养师三餐推荐',
      // path: '/pages/index/guide/guide',
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
  }
});