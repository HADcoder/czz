function getUrlParam(name) {
    //构造一个含有目标参数的正则表达式对象
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    //匹配目标参数
    var res = window.location.search.substr(1).match(reg);
    if (res != null) {
        return decodeURIComponent(res[2]);
    } else {
        return '';
    }
}

function setCookie(name, value) {//两个参数，一个是cookie的名子，一个是值
    var Days = 30; //此 cookie 将被保存 30 天
    var exp = new Date();    //new Date("December 31, 9998");
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}

function getCookie(name) {//取cookies函数
    var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
    if (arr != null) return unescape(arr[2]);
    return null;

}

function delCookie(name) {//删除cookie
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = GetCookie(name);
    if (cval != null) document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

function showSysError() {
    showTip("系统错误，给您带来的不便非常抱歉");
}

function showTimeOutError() {
    showTip("请求超时，请重试");
}

function showTip(cont) {
    layer.alert(cont);
}

function showTipWithCb(cont, cb) {
    layer.open({
        content: cont,
        yes: function (index, layero) {
            cb();
            layer.close(index); //如果设定了yes回调，需进行手工关闭
        }
    });
}

function showConfirm(cont, ok, cancel) {
    layer.confirm(cont, {
        btn: ['确定', '取消'] //按钮
    }, function (index) {
        if (ok) {
            ok();
            layer.close(index);
        }
    }, function (index) {
        if (cancel) {
            cancel();
            layer.close(index);
        }
    });
}

function showPrompt(title, succCb) {
    layer.prompt({title: title, formType: 0}, function (text, index) {
        layer.close(index);
        if (succCb) {
            succCb(text);
        }
    });
}

function showModal(domId, title, width, height, maxmin, cancelCb) {
    layer.open({
        type: 1,
        title: title,
        maxmin: maxmin || false,
        area: [width, height], //宽高
        content: $('#' + domId),
        cancel: function () {
            $('#' + domId).hide();
            if(cancelCb){
                cancelCb();
            }
        }
    });
}

/**
 * 弹出loading框
 * @returns {*}
 */
function showLoading() {
    var loading = layer.load(0, {shade: [0.2, '#393D49']});
    return loading;
}

/**
 * 关闭loading框
 * @param loading
 */
function closeLoading(loading) {
    layer.close(loading);
}

/**
 * 关闭所有弹出层
 * @param loading
 */
function closeAll(loading) {
    layer.closeAll();
}

/**
 * 关闭所有modal层
 * @param loading
 */
function closeModal(contentDomId) {
    $('#' + contentDomId).hide();
    layer.closeAll('page');
}

function clsFormat(input) {
    switch (parseInt(input)) {
        case 1:
            return '推荐';
        case 2:
            return '食用';
        case 3:
            return '不食用';
        default:
            return '';
    }
}

Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));

    }
    return fmt;
}

//+--------------------------------------------------
//| 求两个时间的天数差 日期格式为 YYYY-MM-dd
//+--------------------------------------------------
function daysBetween(dayOne, dayTwo) {
    if (dayOne && dayTwo) {
        var days = ((Date.parse(dayOne) - Date.parse(dayTwo)) / (24 * 60 * 60 * 1000));
        days = Math.abs(days);
        //console.log(dayOne + ' 到 ' + dayTwo + ' 相差：' +days);
        return days;
    }
    return 0;

}

/**
 * 时间格式  2016-04-07
 * @param dayOne
 * @param dayTwo
 * @returns {number}
 */
function monthsBetween(dayOne, dayTwo) {
    if (dayOne && dayTwo) {
        if (dayOne && dayTwo) {
            var days = ((Date.parse(dayOne) - Date.parse(dayTwo)) / (24 * 60 * 60 * 1000));
            var months = Math.floor(Math.abs(days) / 30);
            //console.log(dayOne + ' 到 ' + dayTwo + ' 相差：' + months);
            return months;
        }

    }
    return 0;

}

function isOver6Month(dayOne, dayTwo) {
    if (monthsBetween(dayOne, dayTwo) > 6) {
        showTip('起止时间跨度不能超过6个月');
        return false;
    }
    return true;
}

function isOver180(dayOne, dayTwo) {
    if (daysBetween(dayOne, dayTwo) > 180) {
        showTip('起止时间跨度不能超过180天');
        return false;
    }
    return true;
}

/**
 * 月份加减
 * @param date 日期对象
 * @param num 加减的月份数
 * @returns {Date}
 */
function addMonth(date, num) {
    num = parseInt(num);
    var sDate = date;

    var sYear = sDate.getFullYear();
    var sMonth = sDate.getMonth() + 1;
    var sDay = sDate.getDate();

    var eYear = sYear;
    var eMonth = sMonth + num;
    var eDay = sDay;
    while (eMonth > 12) {
        eYear++;
        eMonth -= 12;
    }

    var eDate;
    var tempDay;
    if (num >= 0) {
        if (sDay == "1") {
            //获得当月的最后一天
            eDay = getLastDay(eYear, eMonth - 1);
            eDate = new Date(eYear, eMonth - 2, eDay);
        } else if (sDay == getLastDay(eYear, sMonth)) {
            //如果是该月份的最后一天,如果+num的那个月如果有这一天，就截止到这一天，如果没有就截止到+num的月份最后一天
            //eDay=getLastDay(eYear,eMonth);
            var thisMonthLastDay = getLastDay(eYear, sMonth);
            //获得+num月的最后一天
            var numMonthLastDay = getLastDay(eYear, eMonth);
            if (thisMonthLastDay <= numMonthLastDay) {
                eDay = thisMonthLastDay - 1;
            } else {
                eDay = numMonthLastDay;
            }
            ;
            eDate = new Date(eYear, eMonth - 1, eDay);
        } else {
            eDate = new Date(eYear, eMonth - 1, eDay - 1);
            /*while (eDate.getMonth() != eMonth - 1) {
                   eDay--;
                   eDate = new Date(eYear, eMonth - 1, eDay);
               }*/
            //
        }
    } else {
        if (sDay == "1") {
            var thisMonthLastDay = getLastDay(eYear, sMonth);
            var numMonthLastDay = getLastDay(eYear, eMonth);
            if (numMonthLastDay < thisMonthLastDay) {
                eDay = 1;
            } else {
                eDay = numMonthLastDay - thisMonthLastDay + 1;
            }
            eDate = new Date(eYear, eMonth - 1, eDay);
        } else if (sDay == getLastDay(eYear, sMonth)) {
            eDate = new Date(eYear, eMonth, 1);
        } else {
            eDate = new Date(eYear, eMonth - 1, eDay + 1);
        }
    }

    return eDate;
}

//获得一个月份的最后一天
function getLastDay(year, month) {
    var new_year = year;
    var new_month = month + 1;
    if (new_month > 12) {
        new_month = new_month - 12;
        new_year++;
    }
    ;
    //获取下一个月份的第一天
    var new_date = new Date(new_year, month, 1);
    return (new Date(new_date.getTime() - 1000 * 60 * 60 * 24)).getDate();
}

