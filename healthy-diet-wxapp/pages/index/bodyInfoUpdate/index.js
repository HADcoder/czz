// pages/index/bodyInfoUpdate/index.js

let util = require('../../../utils/util.js');
let sdk = require('../../../vendor/wafer2-client-sdk/index');
const app = getApp();

Page({
  /**
   * 页面的初始数据
   */
  data: {
    title: {
      headerText: '身体资料更新',
      subHeader: '及时更新，获得最精确的膳食推荐',
    },
    updateBodyInfoBtnText: '更新资料，获取身体报告',
    userBodyInfo: null,
    hiddenLoading: true,
    itemIconUrl: app.globalData.imageBasePath + 'item_icon.png',
    arrowIconUrl: app.globalData.imageBasePath + 'greyArrow.png',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    try {
      app.globalData.userBodyInfo=wx.getStorageSync('userBodyInfo');
    } catch (e) {
      console.log('Exception happen when try to get userBodyInfo from storage!')
    }
    console.log(app.globalData.userBodyInfo);
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
    const that = this;
    try {
      let cachedBodyInfo = wx.getStorageSync('userBodyInfo');
      console.log(cachedBodyInfo);
      this.setData({
        userBodyInfo: {
          gender: util.getAliasSingleOption("gender", cachedBodyInfo.gender),
          birth: cachedBodyInfo.birth,
          height: cachedBodyInfo.height,
          weight: cachedBodyInfo.weight,
          sportRate: util.cutMessage(util.getAliasSingleOption("sportRate", cachedBodyInfo.sportRate), 6),
          treatmentMethod: util.getAliasMultiOption("treatmentMethod", cachedBodyInfo.treatmentMethod),
          otherDisease: util.getAliasMultiOption("otherDisease", cachedBodyInfo.otherDisease),
          nephroticPeriod: cachedBodyInfo.nephroticPeriod,
          irritability: util.getAliasMultiOption("irritability", cachedBodyInfo.irritability),
        },
      });
      console.log("Successfully get userBodyInfo.");
      console.log(this.data.userBodyInfo);

    } catch (e) {
      console.log('Exception happen when try to get userBodyInfo from storage!' + e)
    }
  },

  updateBodyInfo: function () {
    var that=this;
    this.setData({
      hiddenLoading: false,
    });
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