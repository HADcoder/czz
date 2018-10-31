angular.module('admin.filters', [])
    .filter('FormatString', function () {
        return function (input, num) {
            var len = num||8;
            if (input && input.length > len) {
                return input.substr(0, len) + "…"
            }
            return input;
        };
    })
    .filter('FormatNumber', function () {
        return function (input) {
            return Math.round(input * 100) / 100;
        };
    })
;