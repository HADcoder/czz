var time = 0;
var touchDot = 0;//触摸时的原点
var interval = "";
var flag_hd = true;
let util = require('../../utils/util.js');
const app = getApp();
const currentYear = new Date().getFullYear();
const currentMonth = util.formatNumber(new Date().getMonth() + 1);
const currentDate = util.formatNumber(new Date().getDate());

Page({

  /**
   * 页面的初始数据
   */
  data: {
    if_show_report: false,
    mealtime: [],
    mealFoodMap: null,
    nutritionRatio: null,
    datePickerIsShow: false,
    datePickerValue: [currentYear, currentMonth, currentDate],
    year: currentYear,
    month: currentMonth,
    currentDate: currentDate,
    day: new Date().getDate(),
    ballTop: 0,
    ballLeft: 0,
    screenHeight: 0,
    screenWidth: 0,
    text: "没有滑动",
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

    headerText: '饮食记录',

    hasMealRecord: false,
    proteinPieText: '蛋白质摄入\n',
    energyPieText: '热量摄入\n',
    expectedProtein: 0,
    expectedEnergy: 0,
    proteinEaten: 0,
    energyEaten: 0,
    protein_color: '#ffffff',
    energy_color: '#ffffff',
  },
  onShow: function () {
    this.resetColor();
    flag_hd = true; //重新进入页面之后，可以再次执行滑动切换页面代码
    clearInterval(interval); // 清除setInterval
    time = 0;
    this.loadFood();
  },
  resetColor: function () {
    if (this.data.proteinEaten < 100) {
      this.setData({protein_color: '#ffffff'})
    } else {
      this.setData({protein_color: '#e35d42'})
    }
    if (this.data.energyEaten < 100) {
      this.setData({energy_color: '#ffffff'})
    } else {
      this.setData({energy_color: '#e35d42'})
    }
  },
  resetDateOptions: function () {
    let pages = getCurrentPages();
    let currentPage = pages[pages.length - 1];
    let options = currentPage.options;
    options.year = this.data.year;
    options.month = this.data.month;
    options.currentDate = this.data.currentDate;
  },
  loadFood: function () {
    if (this.data.year && this.data.month && this.data.currentDate) {
      this.resetDateOptions();
      let logDate = this.data.year + "-" + util.formatNumber(this.data.month) + "-" + util.formatNumber(this.data.currentDate);
      wx.showLoading({
        title: "加载用餐记录中",
        mask: true
      });
      wx.request({
        url: app.globalData.apiBase + "/foodLog/single",
        method: "GET",
        data: {
          openId: app.globalData.authInfo.openid,
          date: logDate,
        },
        complete: res => {
          wx.hideLoading();
        },
        success: res => {
          let currentRecord = res.data.dietRecordList;
          console.log(currentRecord);
          if (currentRecord.length > 0) {
            this.setData({
              hasMealRecord: true,
            })
          } else {
            this.setData({
              hasMealRecord: false,
            })
          }
          let param = {};
          currentRecord.forEach(item => {
            param[item.mealtime] = item.foodLogItems.map((item2, index) => {
              return {
                description: item2.foodAlias + ": " + item2.unit + "g",
                object: item2,
                index: index,
              }
            })
          });
          this.setData({
            mealFoodMap: param,
          });

          if (res.data.monthFoodLog && res.data.monthFoodLog.totalProtein) {
            let temp = res.data.monthFoodLog;
            let nutritionRatio = Object.entries(temp)
              .filter(item => item[0] in this.data.headerMapping)
              .map(item => {
                return {name: this.data.headerMapping[item[0]], value: item[1]}
              });
            let proteinRatio = parseFloat(temp.totalProtein) / parseFloat(temp.expectProtein);
            let proteinEaten = proteinRatio * 100;

            let energyRatio = parseFloat(temp.totalEnergy) / parseFloat(temp.expectEnergy);
            let energyEaten = energyRatio * 100;

            this.drawDiagram(proteinRatio < 0 ? 1 : proteinRatio, energyRatio < 0 ? 1 : energyRatio);
            this.setData({
              nutritionRatio: nutritionRatio,
              expectedProtein: temp.expectProtein,
              expectedEnergy: temp.expectEnergy,
              proteinEaten: Math.abs(proteinEaten.toFixed(1)),
              energyEaten: Math.abs(energyEaten.toFixed(1)),
            })
          } else {
            if (res.data.monthFoodLog) {
              let temp = res.data.monthFoodLog;
              this.drawDiagram(0, 0);
              this.setData({
                expectedProtein: temp.expectProtein,
                expectedEnergy: temp.expectEnergy,
                proteinEaten: 0,
                energyEaten: 0,
              });
            }
            console.log('res.data.monthFoodLog is null');
            var temp = [];
            for (var key in this.data.headerMapping) {
              if (this.data.headerMapping.hasOwnProperty(key)) {
                temp.push({
                  name: this.data.headerMapping[key],
                  value: 0
                });
              }
            }

            this.setData({
              nutritionRatio: temp,
            });
          }
          this.resetColor();
        },
        fail: res => {
          util.showModel('请求失败,请检查网络', res.errMsg);
        }
      });
    }
  },
  switchReport: function (e) {
    this.setData({
      if_show_report: !this.data.if_show_report,
    })
  },

  threeDayDietReport: function (e) {
    let logDate = this.data.year + "-" + util.formatNumber(this.data.month) + "-" + util.formatNumber(this.data.currentDate);
    wx.navigateTo({
      url: "../threeDayDietReport/threeDayDietReport?logDate=" + logDate,
    })
  },
  checkboxChange: function (e) {
    var checked = e.detail.value;
    let logDate = this.data.year + "-" + util.formatNumber(this.data.month) + "-" + util.formatNumber(this.data.currentDate);
    if (checked.length > 0) {
      wx.request({
        url: app.globalData.apiBase + "/foodLog/isCompletedLog",
        method: "POST",
        data: {
          openId: app.globalData.authInfo.openid,
          date: logDate,
          checked: true
        },
        complete: res => {
          wx.hideLoading();
        },
        success: res => {
          console.log(res.data);
        }
      })
    } else {
      wx.request({
        url: app.globalData.apiBase + "/foodLog/isCompletedLog",
        method: "POST",
        data: {
          openId: app.globalData.authInfo.openid,
          date: logDate,
          checked: false
        },
        complete: res => {
          wx.hideLoading();
        },
        success: res => {
          console.log(res.data);
        }
      })
    }
  },

  showMealDetail: function (e) {
    console.log(e);
    let logDate = this.data.year + "-" + util.formatNumber(this.data.month) + "-" + util.formatNumber(this.data.currentDate);
    wx.navigateTo({
      url: "../logFoodDetail/logFoodDetail?logDate=" + logDate + "&mealtime=" + e.currentTarget.dataset.mealtime + "&openId=" + this.data.openId,
    })
  },
  showDatePicker: function (e) {
    this.setData({
      datePickerIsShow: true,
      datePickerValue: [this.data.year, this.data.month, this.data.currentDate]
    });
  },
  hideDatePicker: function (e) {
    this.setData({
      datePickerIsShow: false,
    });
  },
  confirmDate: function (e) {
    console.log(e);
    let dateArr = e.detail.value;
    this.setData({
      year: dateArr[0],
      month: dateArr[1],
      currentDate: dateArr[2],
      datePickerIsShow: false
    });
    this.loadFood();
  },
  foodRecomment: function () {
    wx.navigateTo({
      url: '../suggestDiet/dailyTips/dailyTips',
    })
  },
  addFoodLog: function () {
    let logDate = this.data.year + "-" + util.formatNumber(this.data.month) + "-" + util.formatNumber(this.data.currentDate);
    wx.navigateTo({
      url: "../logFoodDetail1/logFoodDetail?logDate=" + logDate + "&openId=" + this.data.openId,
    })
  },
  deleteMealDetail: function (e) {
    console.log(e);
    let index = e.currentTarget.dataset.index;
    let mealtime = e.currentTarget.dataset.mealtime;
    let logDate = this.data.year + "-" + util.formatNumber(this.data.month) + "-" + util.formatNumber(this.data.currentDate);

    wx.showLoading({
      title: "正在删除选中用餐信息...",
      mask: true
    });
    let foodList = this.data.mealFoodMap[mealtime];
    foodList.splice(index, 1);
    wx.request({
      url: app.globalData.apiBase + "/foodLog",
      method: "POST",
      data: {
        openId: this.data.openId,
        logDate: logDate,
        mealTime: mealtime,
        foodLogItems: foodList.map(item => item.object),
      },
      complete: res => {
        wx.hideLoading();
      },
      success: res => {
        console.log(res);
        this.setData({
          mealFoodMap: this.data.mealFoodMap,
        });
        this.loadFood();
      },
      fail: res => {
        util.showModel('请求失败,请检查网络', res.errMsg);
      }
    });

  },
  drawDiagram: function (proteinCompletionRatio, energyCompletionRatio) {
    this.drawProgressbg('canvasProgressbg1');
    this.drawCircle('canvasProgress1', 2 * proteinCompletionRatio);
    this.drawProgressbg('canvasProgressbg2');
    this.drawCircle('canvasProgress2', 2 * energyCompletionRatio);
  },
  drawProgressbg: function (backgroundId) {
    let radius = this.data.screenWidth / 5;
    // 使用 wx.createContext 获取绘图上下文 context
    let ctx = wx.createCanvasContext(backgroundId);
    ctx.setLineWidth(3);// 设置圆环的宽度
    ctx.setStrokeStyle('#93c8a5'); // 设置圆环的颜色
    ctx.setLineCap('round');// 设置圆环端点的形状
    ctx.beginPath();//开始一个新的路径
    ctx.arc(radius, radius, radius * 0.9, 0, 2 * Math.PI, false);
    //设置一个原点(100,100)，半径为90的圆的路径到当前路径
    ctx.stroke();//对当前路径进行描边
    ctx.draw();
  },
  drawCircle: function (progressId, step) {
    let radius = this.data.screenWidth / 5;
    let context = wx.createCanvasContext(progressId);
    context.setLineWidth(5);
    context.setStrokeStyle('#ffffff');
    context.setLineCap('round');
    context.beginPath();
    // 参数step 为绘制的圆环周长，从0到2为一周 。 -Math.PI / 2 将起始角设在12点钟位置 ，结束角 通过改变 step 的值确定
    context.arc(radius, radius, radius * 0.9, -Math.PI / 2, step * Math.PI - Math.PI / 2, false);
    context.stroke();
    context.draw()
  },
  onLoad: function (options, year, month, day, state) {
    let _this = this;
    wx.getSystemInfo({
      success: function (res) {
        _this.setData({
          screenHeight: res.windowHeight,
          screenWidth: res.windowWidth,
          font: ""
        });
      }
    });
    this.setData({
      mealtime: app.globalData.mealtime,
      openId: app.globalData.authInfo.openid
    });
    if (options.year) {
      this.setData({
        year: options.year,
        month: options.month,
        currentDate: options.currentDate,
      });
    }
    // wx.showShareMenu({
    //   withShareTicket: true
    // });
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
        // var shareTickets = res.shareTickets;
        // if (shareTickets.length == 0) {
        //   return false;
        // }
        // //可以获取群组信息
        // wx.getShareInfo({
        //   shareTicket: shareTickets[0],
        //   success: function (res) {
        //     console.log(res)
        //   }
        // })
      },
      fail: function (res) {
        // 转发失败
        console.log("转发失败:" + JSON.stringify(res));
      }
    }
  },
});
