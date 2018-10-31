angular.module('admin.services', [])
    .factory('adService', function ($http, cache) {
        var ad = {};

        var timeout = 5000;

        ad.login = function () {
            window.location.href = "login.html";
        };

        ad.success = function () {
            showTip('操作成功');
        };

        ad.getWithCB = function (url, data, success, error) {
            $http({
                url: url,
                method: 'GET',
                timeout: timeout
            }).then(function successCallBack(response) {
                var res = response.data;
                if (res.code === 0) {
                    if (success) {
                        success(res);
                    } else {
                        ad.success();
                    }
                } else {
                    if (res && typeof(res) === 'string') {
                        res = JSON.parse(res);
                    }
                    if (res.code === -7) {
                        var cb = function () {
                            window.location = 'login.html';
                        };
                        showTipWithCb(res.desc, cb);
                        return;
                    }
                    if (error) {
                        error(res);
                    } else {
                        showTip(res.desc);
                    }
                }
            }, function errorCallBack(response) {
                if (response.status === -1) {
                    showTimeOutError();
                } else {
                    // 请求失败执行代码
                    if (error) {
                        error();
                    } else {
                        showSysError();
                    }
                }
            })
        };

        ad.getWithCBLoading = function (url, params, success, error) {
            var loading = showLoading();
            $http({
                url: url,
                params: params,
                method: 'GET',
                timeout: timeout
            }).then(function successCallBack(response) {
                closeLoading(loading);
                var res = response.data;
                if (res.code === 0) {
                    if (success) {
                        success(res);
                    } else {
                        ad.success();
                    }
                } else {
                    if (res && typeof(res) === 'string') {
                        res = JSON.parse(res);
                    }
                    if (res.code === -7) {
                        let cb = function () {
                            window.location = 'login.html';
                        };
                        showTipWithCb(res.desc, cb);
                        return;
                    }
                    if (error) {
                        error(res);
                    } else {
                        showTip(res.desc);
                    }
                }
            }, function errorCallBack(response) {
                closeLoading(loading);
                if (response.status === -1) {
                    showTimeOutError();
                } else {
                    // 请求失败执行代码
                    if (error) {
                        error();
                    } else {
                        showSysError();
                    }
                }
            })
        };

        ad.postWithCBLoading = function (url, data, success, error) {
            var loading = showLoading();
            $http({
                method: 'POST',
                url: url,
                data: data,
                headers: {
                    'Content-Type': 'application/json'
                },
                timeout: timeout
            }).then(function successCallBack(response) {
                closeLoading(loading);
                var res = response.data;
                if (res.code === 0) {
                    if (success) {
                        success(res);
                    } else {
                        ad.success();
                    }
                } else {
                    if (res && typeof(res) === 'string') {
                        res = JSON.parse(res);
                    }
                    if (res.code === -7) {
                        let cb = function () {
                            window.location = 'login.html';
                        };
                        showTipWithCb(res.desc, cb);
                        return;
                    }
                    if (error) {
                        error(res);
                    } else {
                        showTip(res.desc);
                    }
                }
            }, function errorCallBack(response) {
                closeLoading(loading);
                if (response.status === -1) {
                    showTimeOutError();
                } else {
                    // 请求失败执行代码
                    if (error) {
                        error();
                    } else {
                        showSysError();
                    }
                }
            })
        };

        ad.postWithCB = function (url, data, success, error) {
            $http({
                method: 'POST',
                url: url,
                data: data,
                headers: {
                    'Content-Type': 'application/json'
                },
                timeout: timeout
            }).then(function successCallBack(response) {
                var res = response.data;
                if (res.code === 0) {
                    if (success) {
                        success(res);
                    } else {
                        ad.success();
                    }
                } else {
                    if (res && typeof(res) === 'string') {
                        res = JSON.parse(res);
                    }
                    if (res.code === -7) {
                        let cb = function () {
                            window.location = 'login.html';
                        };
                        showTipWithCb(res.desc, cb);
                        return;
                    }
                    if (error) {
                        error(res);
                    } else {
                        showTip(res.desc);
                    }
                }
            }, function errorCallBack(response) {
                if (response.status === -1) {
                    showTimeOutError();
                } else {
                    // 请求失败执行代码
                    if (error) {
                        error();
                    } else {
                        showSysError();
                    }
                }
            })
        };
        return ad;
    })
    .factory('cache', function () {
        return {        //存储单个属性
            set: function (key, value) {
                // localStorage[key] = value;
                sessionStorage.setItem(key, value);
            },        //读取单个属性
            get: function (key, defaultValue) {
                //return localStorage[key] || defaultValue;
                return sessionStorage.getItem(key) || defaultValue;
            },        //存储对象，以JSON格式存储
            setObject: function (key, value) {
                sessionStorage.setItem(key, JSON.stringify(value));
                //localStorage[key] = JSON.stringify(value);//将对象以字符串保存
            },        //读取对象
            getObject: function (key) {
                return JSON.parse(sessionStorage.getItem(key) || '{}')
                //return JSON.parse(localStorage[key] || '{}');//获取字符串并解析成对象
            },
            remove: function (key) {
                sessionStorage.removeItem(key);
            },
            clear: function () {
                sessionStorage.clear();
            }
        }
    })
    .factory('authInterceptor', function (cache) {
        return {
            request: function (config) {
                config.headers = config.headers || {};
                if (cache.get('token')) {
                    config.headers.Authorization = 'Bearer ' + cache.get('token');
                }
                return config;
            },
            responseError: function (response) {
                // ...
            }
        };
    })
;