angular.module('admin.station.controllers', [])
    .controller('top-ctrl', function ($scope, adService, $rootScope, $state, $stateParams, cache) {
        // alert("top")
    })
    .controller('navigate-ctrl', function ($scope, adService, $rootScope, $state, $stateParams, cache) {
        // alert("nav")
        $scope.goToPage = function (page) {
            $state.go(page);
        }
    })
    .controller('station-ctrl', function ($scope, adService, $rootScope, $state, $stateParams, cache, layuiObj) {
        $scope.isShowForm = false;

        $scope.stationModel = {};
        $scope.saveStation = function () {

        };

        $scope.addStation = function () {
            showModal('editForm', '添加', '1000px', '520px');
            initRender();

        };

        function initRender(){
            layform.on('select(station)', function (data) {
                console.log(data);
                $scope.stationModel.station = data.value;
                // form.render();
            });
            layform.on('select(area)', function (data) {
                console.log(data);
                $scope.stationModel.area = data.value;
                // form.render();
            });
            layform.on('radio(pd)', function (data) {
                console.log(data.elem); //得到radio原始DOM对象
                // $scope.pd = data.value;
                console.log(data.value); //被点击的radio的value值
            });
            layform.on('submit(*)', function (data) {
                console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
                console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
                console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });
            layform.render();
        }
    })
;

