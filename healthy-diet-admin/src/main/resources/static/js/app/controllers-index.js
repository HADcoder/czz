angular.module('admin.index.controllers', [])
    .controller('navigate-ctrl', function ($scope, adService, $state, $stateParams, cache) {
        $scope.tokenJson = cache.getObject("tokenJson");
        $scope.logout = function () {
            showConfirm("确定退出？", function () {
                adService.postWithCBLoading("auth/logout", null, function () {
                    cache.clear();
                    window.location.href = 'login.html';
                });

            });
        };
    })
    .controller('foodinfo-ctrl', function ($scope, adService, $timeout, cache) {
        $scope.q = {
            foodName: '',
            type: '',
            subType: ''
        };
        $scope.currPage = 1;
        $scope.pageSize = 10;
        $scope.foodTypes = [];
        $scope.foodSubTypes = [];
        $scope.foodCkds = [];

        $scope.queryFoodAttrs = function () {
            adService.postWithCB("admin/foodInfo/queryFoodAttrs", null, function (res) {
                $scope.foodTypes = res.data.types;
                $scope.foodCkds = res.data.ckds;
                $timeout(function () {
                    initRender();
                }, 100)
            });
        };

        $scope.save = function (model) {
            adService.postWithCBLoading("admin/foodInfo/save", JSON.stringify(model), function (res) {
                if (res.code == 0) {
                    closeModal('editForm');
                    showTipWithCb(res.desc, function () {
                        $scope.query();
                        $scope.foodSubTypes = [];
                    });
                } else {
                    showTip(res.desc);
                }
            });
        };

        function initRender() {

            layform.on('select(q_type)', function (data) {
                $scope.$apply(function () {
                    $scope.q.type = data.value;
                    var subTypeArr = !data.value ? [] : JSON.parse($(data.elem).children('[value="' + data.value + '"]').attr('_subVal'));
                    $scope.foodSubTypes = subTypeArr;
                    $timeout(function () {
                        layform.render();
                    }, 100)
                });
            });
            layform.on('select(q_subType)', function (data) {
                $scope.$apply(function () {
                    $scope.q.subType = data.value;
                });
            });

            layform.on('select(type)', function (data) {
                $scope.$apply(function () {
                    var subTypeArr = !data.value ? [] : JSON.parse($(data.elem).children('[value="' + data.value + '"]').attr('_subVal'));
                    $scope.foodSubTypes = subTypeArr;
                    $timeout(function () {
                        layform.render();
                    }, 100)
                });
            });

            layform.on('submit(*)', function (data) {
                // console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
                // console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
                // console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
                $scope.save(data.field);
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });
            layform.render();
        }

        $scope.add = function () {
            $scope.foodSubTypes = [];
            var model = {
                "id": null,
                "type": "",
                "typeName": "",
                "subType": "",
                "subTypeName": "",
                "foodAlias": "",
                "foodCode": "",
                "foodName": "",
                "edible": "",
                "water": "",
                "energyKcal": "",
                "energyKj": "",
                "protein": "",
                "proteinCls": "",
                "fat": "",
                "fatCls": "",
                "cho": "",
                "choCls": "",
                "gi": "",
                "cholesterol": "",
                "cholesterolCls": "",
                "ash": "",
                "purine": "",
                "purineCls": "",
                "fiber": "",
                "soluble": "",
                "dietaryFiber": "",
                "retinol": "",
                "thiamin": "",
                "riboflavin": "",
                "nicotinic": "",
                "folate": "",
                "niacin": "",
                "carotene": "",
                "vitA": "",
                "vitC": "",
                "vitE": "",
                "vitE1": "",
                "vitE2": "",
                "vitE3": "",
                "vitB6": "",
                "vitB12": "",
                "aceEleCa": "",
                "aceEleP": "",
                "aceElePCls": "",
                "proteinDivP": "",
                "aceEleK": "",
                "aceEleKCls": "",
                "aceEleNa": "",
                "aceEleNaCls": "",
                "aceEleMg": "",
                "aceEleFe": "",
                "aceEleZn": "",
                "aceEleSe": "",
                "aceEleCu": "",
                "aceEleMn": "",
                "aceEleI": "",
                "version": "",
                "remark": "",
                "intake": "",
                "ckd": ""
            };
            showModal('editForm', '添加食材', '1250px', '600px', true, function () {
                $scope.$apply(function () {
                    $scope.foodSubTypes = [];
                })
            });
            initRender();
            layform.val('editForm', model);
        };

        $scope.edit = function (model) {
            $scope.$apply(function () {
                for (i = 0, iLen = $scope.foodTypes.length; i < iLen; i++) {
                    if ($scope.foodTypes[i].type == model.type) {
                        $scope.foodSubTypes = $scope.foodTypes[i].subTypes;
                    }
                }
            });
            showModal('editForm', '编辑食材', '1250px', '600px', true, function () {
                $scope.$apply(function () {
                    $scope.foodSubTypes = [];
                })
            });
            initRender();
            layform.val('editForm', model);
        };

        $scope.comfirm = function (model) {
            var params = {
                id: model.id
            };
            showConfirm("确认当前信息已完整？", function () {
                adService.postWithCBLoading("admin/foodInfo/comfirmState", JSON.stringify(params), function (res) {
                    if (res.code == 0) {
                        showTipWithCb(res.desc, function () {
                            $scope.query();
                        });
                    } else {
                        showTip(res.desc);
                    }
                });
            });
        };

        $scope.query = function () {
            var params = $scope.q;
            params.currPage = $scope.currPage;
            params.pageSize = $scope.pageSize;

            table.reload('table', {
                where: params
            });
        };

        initUploader('', 'admin/foodInfo/saveByImport', {}, 'xlsx|xls', 10240, function (res) {
            if (res.code == 0) {
                showTipWithCb(res.desc, function () {
                    $scope.query();
                })
            } else {
                showTip(res.desc);
            }
        });

        var table = layui.table;
        table.render({
            elem: '#table'
            , url: 'admin/foodInfo/query'
            , method: 'post'
            , contentType: 'application/json'
            , headers: {
                'Authorization': 'Bearer ' + cache.get('token')
            }
            , request: {
                pageName: 'currPage' //页码的参数名称，默认：page
                , limitName: 'pageSize' //每页数据量的参数名，默认：limit
            }
            // , toolbar: true
            // , defaultToolbar: ['filter'] //'filter', 'print', 'exports'
            , title: '食材列表'
            , cols: [[
                {
                    field: 'idx', title: '序号', align: 'center', fixed: 'left', templet: function (d) {
                        return d.LAY_INDEX
                    }
                }
                , {
                    field: 'foodName',
                    title: '食材名称',
                    width: 150,
                    fixed: 'left',
                    event: 'edit',
                    templet: function (d) {
                        if (d.state == 0) {
                            return '<div><a style="cursor:pointer;color:#01AAED">' + d.foodName + '</a></div>'
                        } else {
                            return '<div><a>' + d.foodName + '</a></div>'
                        }
                    }
                }
                , {field: 'foodAlias', title: '食材别名', width: 120, fixed: 'left'}
                , {field: 'foodCode', title: '编码', width: 90, fixed: 'left', sort: true}
                , {field: 'typeName', title: '食材分类', width: 100}
                , {field: 'subTypeName', title: '食材亚类', width: 100}
                , {field: 'edible', title: '食部(%)', width: 90}
                , {field: 'water', title: '水(g)', width: 80, sort: true}
                , {
                    field: 'energyKcal', title: '能量(kcal)', width: 100, sort: true, templet: function (d) {
                        return Math.round(d.energyKcal * 100) / 100
                    }
                }
                , {
                    field: 'protein', title: '蛋白质(g)', width: 100, sort: true, templet: function (d) {
                        return Math.round(d.protein * 100) / 100
                    }
                }
                , {
                    field: 'cho', title: '碳水化合物(g)', width: 130, sort: true, templet: function (d) {
                        return Math.round(d.cho * 100) / 100
                    }
                }
                , {field: 'ckd', title: 'CKD', width: 70}
                , {field: 'gi', title: '血糖指数(gi)', width: 120, sort: true}
                , {field: 'cholesterol', title: '胆固醇(mg)', width: 110, sort: true}
                , {field: 'purine', title: '嘌呤(mg)', width: 100, sort: true}
                , {field: 'fat', title: '脂肪(g)', width: 100, sort: true}
                , {
                    field: 'proteinCls', title: '蛋白质赋值', width: 100, templet: function (d) {
                        return clsFormat(d.proteinCls)
                    }
                }
                , {
                    field: 'fatCls', title: '脂肪赋值', width: 90, templet: function (d) {
                        return clsFormat(d.fatCls)
                    }
                }
                , {
                    field: 'cholesterolCls', title: '胆固醇赋值', width: 100, templet: function (d) {
                        return clsFormat(d.cholesterolCls)
                    }
                }
                , {
                    field: 'purineCls', title: '嘌呤赋值', width: 90, templet: function (d) {
                        return clsFormat(d.purineCls)
                    }
                }
                , {field: 'fiber', title: '总纤维(g)', width: 100}
                , {field: 'soluble', title: '可溶性纤维(g)', width: 110}
                , {field: 'dietaryFiber', title: '不溶性纤维(g)', width: 110}
                , {field: 'retinol', title: '视黄醇(μg)', width: 100}
                , {field: 'thiamin', title: '硫胺素(mg)', width: 110}
                , {field: 'riboflavin', title: '核黄素(mg)', width: 110}
                , {field: 'nicotinic', title: '尼克酸(mg)', width: 110}
                , {field: 'folate', title: '叶酸(μg)', width: 100}
                , {field: 'niacin', title: '烟酸(mg)', width: 100}
                , {field: 'carotene', title: '胡萝卜素(μg)', width: 120}
                , {field: 'vitA', title: '维生素A(μg)', width: 110}
                , {field: 'vitC', title: '维生素C(mg)', width: 120}
                , {field: 'vitE', title: '维生素E(mg)', width: 120}
                , {field: 'vitB6', title: '维生素B6(mg)', width: 120}
                , {field: 'vitB12', title: '维生素B12(μg)', width: 120}
                , {field: 'aceEleCa', title: '钙(mg)', width: 80}
                , {field: 'aceEleP', title: '磷(mg)', width: 80}
                , {
                    field: 'aceElePCls', title: '磷赋值', width: 100, templet: function (d) {
                        return clsFormat(d.aceElePCls)
                    }
                }
                , {field: 'proteinDivP', title: '蛋白质/麟', width: 100}
                , {field: 'aceEleK', title: '钾(mg)', width: 80}
                , {
                    field: 'aceEleKCls', title: '钾赋值', width: 80, templet: function (d) {
                        return clsFormat(d.aceEleKCls)
                    }
                }
                , {field: 'aceEleNa', title: '钠(mg)', width: 80}
                , {
                    field: 'aceEleNaCls', title: '钠赋值', width: 100, templet: function (d) {
                        return clsFormat(d.aceEleNaCls)
                    }
                }
                , {field: 'aceEleMg', title: '镁(mg)', width: 80}
                , {field: 'aceEleFe', title: '铁(mg)', width: 80}
                , {field: 'aceEleZn', title: '锌(mg)', width: 80}
                , {field: 'aceEleSe', title: '硒(mg)', width: 80}
                , {field: 'aceEleCu', title: '铜(mg)', width: 80}
                , {field: 'aceEleMn', title: '锰(mg)', width: 80}
                , {field: 'aceEleI', title: '碘(μg)', width: 80}
                , {field: 'version', title: '版本', width: 80}
                , {field: 'intake', title: '摄入份量', width: 100}
                , {
                    title: '操作', fixed: 'right', event: 'comfirm', templet: function (d) {
                        if (d.state == 0) {
                            return '<div><a class="layui-btn layui-btn-normal layui-btn-xs">确认</a></div>'
                        } else {
                            return '<div><a class="layui-btn layui-btn-primary layui-disabled layui-btn-xs">确认</a></div>'
                        }
                    }
                    , width: 70
                }
            ]]
            , page: {
                groups: 5 //只显示 1 个连续页码
            }
            , response: {
                statusCode: 0 //重新规定成功的状态码为 200，table 组件默认为 0
            }
            , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                if (res.code != 0) {
                    showTipWithCb(res.desc, function () {
                        adService.login();
                    });
                }
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.desc, //解析提示文本
                    "count": res.data.totalElements, //解析数据长度
                    "data": res.data.content //解析数据列表
                };
            }
        });

        table.on('sort(table)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            //尽管我们的 table 自带排序功能，但并没有请求服务端。
            //有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
            table.reload('table', {
                initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
                , where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
                    field: obj.field //排序字段
                    , order: obj.type //排序方式
                }
            });
        });

        //监听单元格事件
        table.on('tool(table)', function (obj) {
            if (obj.event === 'edit') {
                if (obj.data.state == 0) {
                    $scope.edit(obj.data);
                }
            }
            if (obj.event === 'comfirm') {
                if (obj.data.state == 0) {
                    $scope.comfirm(obj.data);
                }
            }
        });
        $scope.queryFoodAttrs();


    })
    .controller('recipeinfo-ctrl', function ($scope, adService, $timeout, cache) {
        $scope.q = {
            name: '',
            foodId: ''
        };
        $scope.currPage = 1;
        $scope.pageSize = 10;
        $scope.mainIngredients = [];
        $scope.suppleMentarys = [];
        var slider = layui.slider;

        $scope.save = function (model) {
            if ($scope.mainIngredients.length == 0) {
                showTip("请输入主料");
                return false;
            }
            model.mainIngredient = JSON.stringify($scope.mainIngredients);
            model.suppleMentary = JSON.stringify($scope.suppleMentarys);
            adService.postWithCBLoading("admin/recipeInfo/save", JSON.stringify(model), function (res) {
                if (res.code == 0) {
                    closeModal('editForm');
                    showTipWithCb(res.desc, function () {
                        $scope.query();
                    });
                } else {
                    showTip(res.desc);
                }
            });
        };

        function initRender() {
            layform.on('checkbox(mealTime)', function (data) {
                var arr = '';
                $('input[name="mealTimes"]:checked').each(function () {
                    arr += $(this).val() + "|";
                });
                $('input[name="mealTime"]').val("|" + arr);
            });
            layform.on('submit(*)', function (data) {
                // console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
                // console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
                // console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
                $scope.save(data.field);
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });
            layform.render();
        }

        $scope.add = function () {
            var model = {
                "id": null,
                "name": "",
                "method": "1",
                "taste": "1",
                "cuisine": "1",
                "ageGroup": "1",
                "difficulty": "1",
                "prepareTime": "",
                "cookingTime": "1",
                "mealTime": "1",
                "category": "1",
                "material": "",
                "mainIngredient": "",
                "suppleMentary": "",
                "energy": "",
                "protein": "",
                "proteinIndicator": "",
                "fatIndicator": "",
                "cholIndicator": "",
                "carboIndicator": "",
                "purineIndicator": "",
                "kaliumIndicator": "",
                "natriumIndicator": "",
                "phosphorIndicator": "",
                "ckd": "",
                "cookingNote": "",
                "picture": "",
                "createTime": "",
                "updateTime": ""
            };
            $scope.mainIngredients = [];
            showModal('editForm', '添加食谱', '950px', '600px', false);
            initRender();
            layform.val('editForm', model);
            initSlider('prepareTime', 1);
            initSlider('cookingTime', 1);
        };

        $scope.edit = function (model) {
            console.log(model);
            $scope.mainIngredients = JSON.parse(model.mainIngredient);
            $scope.suppleMentarys = JSON.parse(model.suppleMentary);
            $timeout(function () {
                showModal('editForm', '编辑食谱', '950px', '600px', true);
            }, 100)

            initRender();
            layform.val('editForm', model);

            var arr = model.mealTime.split("|");
            for (i = 0, iLen = arr.length; i < iLen; i++) {
                $('input[name="mealTimes"][value="' + arr[i] + '"]').prop("checked", "checked");
                $('input[name="mealTimes"][value="' + arr[i] + '"]').next().addClass("layui-form-checked");
            }
            initSlider('prepareTime', model.prepareTime);
            initSlider('cookingTime', model.cookingTime);
        };

        $scope.query = function () {
            var params = $scope.q;
            params.currPage = $scope.currPage;
            params.pageSize = $scope.pageSize;

            table.reload('table', {
                where: params
            });
        };

        $scope.delete = function () {
            var datas = table.checkStatus('table').data;
            if (datas.length <= 0) {
                showTip("请勾选记录");
                return;
            }
            var idArr = [];
            for (i = 0, iLen = datas.length; i < iLen; i++) {
                idArr.push(datas[i].id);
            }
            showConfirm("确认删除？", function () {
                adService.postWithCBLoading("admin/recipeInfo/delete", JSON.stringify({ids: idArr.join(",")}), function (res) {
                    showTipWithCb(res.desc, function () {
                        $scope.query();
                    });
                });
            })

        };

        initUploader('', 'admin/recipeInfo/saveByImport', {}, 'xlsx|xls', 10240, function (res) {
            if (res.code == 0) {
                showTipWithCb(res.desc, function () {
                    $scope.query();
                })
            } else {
                showTip(res.desc);
            }
        });

        $scope.delMainIngredient = function (id) {
            for (var i = 0, iLen = $scope.mainIngredients.length; i < iLen; i++) {
                if (id == $scope.mainIngredients[i].id) {
                    $scope.mainIngredients.splice(i, 1);
                }
            }
        };

        $scope.delSuppleMentary = function (id) {
            for (var i = 0, iLen = $scope.suppleMentarys.length; i < iLen; i++) {
                if (id == $scope.suppleMentarys[i].id) {
                    $scope.suppleMentarys.splice(i, 1);
                }
            }
        };

        $scope.showMain = function (model) {
            console.log(model);
            $scope.mainIngredients = JSON.parse(model.mainIngredient);
            $timeout(function () {
                showModal('mainModal', '查看主料', '650px', '300px');
            }, 100);
        };

        $scope.clearFoodId = function () {
            if (!$('#q_foodId').val()) {
                $scope.q.foodId = '';
            }
        };

        layui.use(['autocomplete'], function () {
            var autocomplete = layui.autocomplete;
            autocomplete.render({
                elem: $('#mainIngredient')[0],
                url: 'admin/foodInfo/queryAll',
                method: 'post',
                search_key: 'foodAlias',
                template_val: '',
                template_txt: '{{d.foodAlias}}',
                headers: {
                    'Authorization': 'Bearer ' + cache.get('token')
                },
                onselect: function (resp) {
                    var exists = false;
                    for (var i = 0, iLen = $scope.mainIngredients.length; i < iLen; i++) {
                        if (resp.id == $scope.mainIngredients[i].id) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        showPrompt('请输入' + resp.foodAlias + '的重量', function (text) {
                            var tmpModel = {
                                id: resp.id,
                                foodAlias: resp.foodAlias,
                                weight: text
                            };
                            $scope.$apply(function () {
                                $scope.mainIngredients.push(tmpModel);
                            });
                        })
                    }
                }
            });

            autocomplete.render({
                elem: $('#suppleMentary')[0],
                url: 'admin/foodInfo/queryAll',
                method: 'post',
                search_key: 'foodAlias',
                template_val: '',
                template_txt: '{{d.foodAlias}}',
                headers: {
                    'Authorization': 'Bearer ' + cache.get('token')
                },
                onselect: function (resp) {
                    var exists = false;
                    for (var i = 0, iLen = $scope.suppleMentarys.length; i < iLen; i++) {
                        if (resp.id == $scope.suppleMentarys[i].id) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        showPrompt('请输入' + resp.foodAlias + '的重量', function (text) {
                            var tmpModel = {
                                id: resp.id,
                                foodAlias: resp.foodAlias,
                                weight: text
                            };
                            $scope.$apply(function () {
                                $scope.suppleMentarys.push(tmpModel);
                            });
                        })
                    }
                }
            });

            autocomplete.render({
                elem: $('#q_foodId')[0],
                url: 'admin/foodInfo/queryAll',
                method: 'post',
                search_key: 'foodAlias',
                template_val: '{{d.foodAlias}}',
                template_txt: '{{d.foodAlias}}',
                headers: {
                    'Authorization': 'Bearer ' + cache.get('token')
                },
                onselect: function (resp) {
                    $scope.q.foodId = resp.id;
                }
            });
        });

        function initSlider(domId, value) {
            slider.render({
                elem: '#' + domId  //绑定元素
                , min: 1
                , max: 180
                , input: true
                , theme: '#1E9FFF'
                , value: value || 1
                , setTips: function (value) { //自定义提示文本
                    return value;
                }
                , change: function (value) {
                    $('input[name="' + domId + '"]').val(value);
                }
            });
        }

        var table = layui.table;
        table.render({
            elem: '#table'
            , url: 'admin/recipeInfo/query'
            , method: 'post'
            , contentType: 'application/json'
            , headers: {
                'Authorization': 'Bearer ' + cache.get('token')
            }
            , request: {
                pageName: 'currPage' //页码的参数名称，默认：page
                , limitName: 'pageSize' //每页数据量的参数名，默认：limit
            }
            , title: '食谱列表'
            , cols: [[
                {type: 'checkbox', fixed: 'left'}
                , {
                    field: 'idx', title: '序号', align: 'center', fixed: 'left', templet: function (d) {
                        return d.LAY_INDEX
                    }
                }
                , {
                    field: 'name',
                    title: '名称',
                    width: 120,
                    fixed: 'left',
                    event: 'edit',
                    templet: function (d) {
                        return '<div><a style="cursor:pointer;color:#01AAED">' + d.name + '</a></div>'
                    }
                }
                , {
                    field: 'method', title: '烹饪方法', width: 90,
                    templet: function (d) {
                        switch (parseInt(d.method)) {
                            case 1:
                                return "炒";
                            case 2:
                                return "煮";
                            case 3:
                                return "蒸";
                            case 4:
                                return "炖";
                            case 5:
                                return "发酵";
                            case 6:
                                return "其它";
                            default:
                                return d.method;
                        }
                    }
                }
                , {
                    field: 'taste', title: '风味', width: 80,
                    templet: function (d) {
                        switch (parseInt(d.taste)) {
                            case 1:
                                return "原味";
                            case 2:
                                return "清淡";
                            case 3:
                                return "咸鲜";
                            case 4:
                                return "油炸";
                            case 5:
                                return "其它";
                            default:
                                return d.taste;
                        }
                    }
                }
                , {
                    field: 'cuisine', title: '菜系', width: 80,
                    templet: function (d) {
                        switch (parseInt(d.cuisine)) {
                            case 1:
                                return "粤菜";
                            case 2:
                                return "川菜";
                            case 3:
                                return "湘菜";
                            case 0:
                                return "其它";
                            default:
                                return d.cuisine;
                        }
                    }
                }
                , {
                    field: 'difficulty', title: '难度', width: 80,
                    templet: function (d) {
                        switch (parseInt(d.difficulty)) {
                            case 1:
                                return "简单";
                            case 2:
                                return "容易";
                            case 3:
                                return "一般";
                            case 4:
                                return "中等";
                            case 5:
                                return "困难";
                            default:
                                return d.difficulty;
                        }
                    }
                }
                , {
                    field: 'mealTime', title: '餐时', width: 150, sort: true,
                    templet: function (d) {
                        var result = d.mealTime;
                        result = result.replace('0', '早餐');
                        result = result.replace('1', '午餐');
                        result = result.replace('2', '晚餐');
                        result = result.replace('3', '加班');
                        return result;
                    }
                }
                , {
                    field: 'category', title: '荤素类别', width: 100, sort: true,
                    templet: function (d) {
                        switch (parseInt(d.category)) {
                            case 0:
                                return "素食";
                            case 1:
                                return "荤菜";
                            default:
                                return d.category;
                        }
                    }
                }
                , {
                    field: 'energy', title: '总能量', width: 100, sort: true, templet: function (d) {
                        return Math.round(d.energy * 100) / 100
                    }
                }
                , {
                    field: 'protein', title: '总蛋白', width: 100, sort: true, templet: function (d) {
                        return Math.round(d.protein * 100) / 100
                    }
                }
                , {
                    field: 'proteinIndicator', title: '蛋白质赋值', width: 110, templet: function (d) {
                        return clsFormat(d.proteinIndicator)
                    }
                }
                , {
                    field: 'fatIndicator', title: '脂肪赋值', width: 100, templet: function (d) {
                        return clsFormat(d.fatIndicator)
                    }
                }
                , {
                    field: 'cholIndicator', title: '碳水化合物赋值', width: 90, templet: function (d) {
                        return clsFormat(d.cholIndicator)
                    }
                }
                , {
                    field: 'carboIndicator', title: '胆固醇赋值', width: 100, templet: function (d) {
                        return clsFormat(d.carboIndicator)
                    }
                }
                , {
                    field: 'purineIndicator', title: '嘌呤赋值', width: 90, templet: function (d) {
                        return clsFormat(d.purineIndicator)
                    }
                }
                , {
                    field: 'kaliumIndicator', title: '钾赋值', width: 80, templet: function (d) {
                        return clsFormat(d.kaliumIndicator)
                    }
                }
                , {
                    field: 'natriumIndicator', title: '钠赋值', width: 80, templet: function (d) {
                        return clsFormat(d.natriumIndicator)
                    }
                }
                , {
                    field: 'phosphorIndicator', title: '磷赋值', width: 80, templet: function (d) {
                        return clsFormat(d.phosphorIndicator)
                    }
                }
                , {field: 'ckd', title: 'CKD', width: 70}
                , {
                    title: '查看', fixed: 'right', event: 'showMain', templet: function (d) {
                        return '<div><a class="layui-btn layui-btn-normal layui-btn-xs">主料</a></div>'
                    }
                    , width: 70
                }
            ]]
            , page: {
                groups: 5 //只显示 1 个连续页码
            }
            , response: {
                statusCode: 0 //重新规定成功的状态码为 200，table 组件默认为 0
            }
            , parseData: function (res) { //将原始数据解析成 table 组件所规定的数据
                if (res.code != 0) {
                    showTipWithCb(res.desc, function () {
                        adService.login();
                    });
                }
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.desc, //解析提示文本
                    "count": res.data.totalElements, //解析数据长度
                    "data": res.data.content //解析数据列表
                };
            }
        });

        table.on('sort(table)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            //尽管我们的 table 自带排序功能，但并没有请求服务端。
            //有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，从而实现服务端排序，如：
            table.reload('table', {
                initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
                , where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
                    field: obj.field //排序字段
                    , order: obj.type //排序方式
                }
            });
        });

        //监听单元格事件
        table.on('tool(table)', function (obj) {
            if (obj.event == 'edit') {
                $scope.edit(obj.data);
            }
            else if (obj.event == 'showMain') {
                $scope.showMain(obj.data);
            }
        });
    })
;