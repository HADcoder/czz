angular.module('admin', [
    'admin.index.controllers',
    'admin.services',
    'admin.filters',
    'ui.router'])

    .run(function ($rootScope, $state, $stateParams, $transitions, cache) {
        // 把$state和$stateParams放入$rootScope，方便在任何地方使用。
        // It's very handy to add references to $state and $stateParams to the $rootScope
        // so that you can access them from any scope within your applications.For example,
        // <li ng-class="{ active: $state.includes('contacts.list') }"> will set the <li>
        // to active whenever 'contacts.list' or one of its decendents is active.
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;

        $transitions.onStart({}, function (transition) {
            var token = cache.get("token");
            if (!token) {
                window.location.href = 'login.html';
            }
        });

        $transitions.onSuccess({}, function (transition) {
            $rootScope.navTitles = transition.$to().navTitles;
            $('.layui-nav-item a[ui-sref="' + transition.$to() + '"]').parent().addClass('layui-this');
        });
    })

    .config(function ($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider) {
        $httpProvider.interceptors.push('authInterceptor');

        $locationProvider.html5Mode(false).hashPrefix('');
        $urlRouterProvider.otherwise('/foodinfo');
        $stateProvider
            .state('foodinfo', {
                url: '/foodinfo',
                templateUrl: "html/foodinfo.html",
                controller: 'foodinfo-ctrl',
                navTitles: ["食材信息"]
            })
            .state('food', {
                url: '/food',
                templateUrl: "html/food.html",
                controller: 'food-ctrl',
                navTitles: ["食材信息"]
            })
            .state('recipeinfo', {
                url: '/recipeinfo',
                templateUrl: "html/recipeinfo.html",
                controller: 'recipeinfo-ctrl',
                navTitles: ["食谱信息"]
            })
        ;

    })
;
