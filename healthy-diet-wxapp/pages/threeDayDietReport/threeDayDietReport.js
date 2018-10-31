// pages/threeDayDietReport/threeDayDietReport.js
const app = getApp();
let util = require('../../utils/util.js');
const currentYear = new Date().getFullYear();
const currentMonth = util.formatNumber(new Date().getMonth() + 1);
const currentDate = util.formatNumber(new Date().getDate());
const MONTHS = ['Jan.', 'Feb.', 'Mar.', 'Apr.', 'May.', 'June.', 'July.', 'Aug.', 'Sept.', 'Oct.', 'Nov.', 'Dec.'];
Page({

  /**
   * 页面的初始数据
   */
  data: {
    title: {
      headerText: '三日膳食分析',
      subHeader: '您，吃对了吗？',
    },
    standardLog: null,
    elementEvgs: null,
    nutritionRatio: null,
    dieticianAdvice: null,
    headerMapping: {
      totalEnergy: "总能量",
      totalProtein: "总蛋白质",
      peRatio: "优质蛋白比例（建议0.5-0.7)",
      fat: "脂肪",
      feRatio: "脂肪供能比(建议0.25-0.35)",
      cho: "碳水化合物",
      ceRatio: "碳水化合物供能比(建议0.55-0.65）",
      na: "钠 (建议<2000mg/d)",
      k: "钾",
      p: "磷 (建议<800mg/d)",
      ca: "钙 (建议<2000mg/d)"
    },
    hasResult: false,
    basicInfoSummary: [],
    year: currentYear,
    month: currentMonth,
    currentDate: currentDate,
    day: new Date().getDate(),
    selectedDaysStyle: [],
    hasReportsDates: [],
    str: MONTHS[new Date().getMonth()],  // 月份字符串
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.loadCalendar();
    let logDate = options.logDate;
    console.log(logDate);
    this.refreshCalendarDates();
    this.loadCalendar();
    this.loadReport(logDate);
  },

  switchMonth: function (e) {
    console.log(e.detail);
    let {prevMonth, prevYear, currentMonth, currentYear} = e.detail;
    let logMonth = currentYear + "-" + util.formatNumber(currentMonth);
    this.setData({
      year: currentYear,
      month: util.formatNumber(currentMonth),
    });
    this.loadCalendar();
    this.loadReport();
  },

  switchDay: function (e) {
    console.log(e.detail);
    let {background, color, day, lunarDay, lunarMonth, month, year} = e.detail;
    let logMonth = year + "-" + util.formatNumber(month);
    console.log(+this.data.month !== month);
    if (+this.data.month !== month) {
      this.setData({
        month: util.formatNumber(month),
        currentDate: day,
      });
      this.loadCalendar();
    } else {
      this.setData({
        currentDate: day,
      });
      this.refreshCalendarDates();
    }
    this.loadReport();
  },

  loadCalendar: function () {
    let logMonth = this.data.year + "-" + util.formatNumber(this.data.month);
    wx.showLoading({
      title: "加载报告列表中",
      mask: true
    });
    wx.request({
      url: app.globalData.apiBase + "/foodLog/reports",
      method: "POST",
      data: {
        openId: app.globalData.authInfo.openid,
        month: logMonth,
      },
      complete: res => {
        wx.hideLoading();
      },
      success: res => {
        let hasReportsDates = res.data.logDateList;
        this.setData({
          hasReportsDates
        });
        this.refreshCalendarDates();
      },
      fail: res => {
        util.showModel('请求失败,请检查网络', res.errMsg);
      }
    });
  },

  refreshCalendarDates: function () {
    let selectedDaysStyle = [];
    const days_count = new Date(this.data.year, this.data.month, 0).getDate();
    for (let i = 1; i <= days_count; i++) {
      if (i === +this.data.currentDate) {
        selectedDaysStyle.push({
          month: 'current', day: i, color: 'white', background: '#4cb16f'
        });
      } else if (this.data.hasReportsDates.includes(i)) {
        selectedDaysStyle.push({
          month: 'current', day: i, color: 'white', background: '#84e7d0'
        });
      } else {
        selectedDaysStyle.push({
          month: 'current', day: i, color: 'black'
        });
      }
    }
    this.setData({
      selectedDaysStyle
    });
  },
  loadReport: function (logDate) {
    if (!logDate) {
      logDate = this.data.year + "-" + util.formatNumber(this.data.month) + "-" + util.formatNumber(this.data.currentDate);
    }
    wx.showLoading({
      title: "加载报告中",
      mask: true
    });
    wx.request({
      url: app.globalData.apiBase + "/foodLog/analysis",
      method: "GET",
      data: {
        openId: app.globalData.authInfo.openid,
        date: logDate,
      },
      complete: res => {
        wx.hideLoading();
      },
      success: res => {
        console.log(res.data);
        let result = res.data;
        if (result.standardLog) {
          this.setData({
            standardLog: result.standardLog,
            hasResult: true,
          })
        } else {
          this.setData({
            standardLog: false,
            hasResult: false,
          })
        }
        if (result.elementEvgs) {
          this.setData({
            elementEvgs: result.elementEvgs,
          });
          let nutritionRatio = Object.entries(this.data.elementEvgs)
            .map(item => {
              return {name: this.data.headerMapping[item[0]], value: item[1]}
            });
          this.setData({
            nutritionRatio: nutritionRatio,
          })
        } else {
          this.setData({
            elementEvgs: {},
            nutritionRatio: [],
          })
        }
        if (result.dieticianAdvice) {
          this.setData({
            dieticianAdvice: result.dieticianAdvice,
          });
          try {
            if (wx.getStorageSync("basicInfoSummary")) {
              this.setData({
                basicInfoSummary: wx.getStorageSync("basicInfoSummary")
              });
              console.log("Session contained basicInfoSummary.");
              console.log(this.data.basicInfoSummary);
            } else {
              console.log("Session does not contained basicInfoSummary.");
            }
          } catch (e) {
            console.log('Exception happen when try to get basicInfoSummary from storage!');
            console.log(e);
          }
        } else {
          this.setData({
            dieticianAdvice: [],
          });
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

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
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
  }
});